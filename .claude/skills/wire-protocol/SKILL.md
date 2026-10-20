---
name: wire-protocol
description: Reference for the OpenDisplay wire protocol (framing, handshake, message types) that this Android client must speak to interoperate with the existing Mac app, ported from Shared/Protocol.swift, iOS/PhoneReceiver.swift and Mac/MacSender.swift.
---

# OpenDisplay protocol (reference for the Android client)

Source: the upstream repo `peetzweg/opendisplay` (Swift), files `Shared/Protocol.swift`,
`iOS/PhoneReceiver.swift`, `Mac/MacSender.swift`, `Mac/InputInjector.swift`. **The Mac does not
change** — this skill exists so any work on the Android network/handshake layer can check against
the peer's real behavior instead of reinventing it from memory.

## Rule: a change never breaks the protocol (security included)

The source of truth is upstream's `PROTOCOL.md` (`peetzweg/opendisplay`), not just this skill.
The Mac doesn't change; any divergence becomes a silent incompatibility. Before changing
networking, handshake, framing or the session lifecycle:

1. Read the matching `PROTOCOL.md` section and check its MUST/MUST NOT rules.
2. Hardening (rejecting a connection, truncating/validating a field, capping a size, dropping a
   message) must not drop a legitimate session or change what goes on the wire. When in doubt,
   degrade instead of rejecting.
3. Normative rules that most often collide with security changes: an unknown type/field is
   ignored (§6); an unparseable control payload is ignored, never fatal; a new connection
   replaces the active one (§1); silence > 5 s is death and ping every 2 s (§8.2); `hello`
   always comes first; the JSON/video demux (§4) stays intact.
4. If the divergence is deliberate, document it in `RATIONALE.md`, test against the real Mac and
   tell the owner before merging. Example: #165 diverges from §1 (issue #168).

## Roles

- The **Android (this project) listens** on a `ServerSocket` at port **9000**.
- The **Mac connects** — an active dial, both over WiFi (resolving the mDNS service) and over USB
  (usbmuxd, which doesn't exist on Android — see the USB section in the README).
- Once connected, the **same TCP socket carries video AND control**, in both directions.

## WiFi discovery

Announce via `NsdManager`:
- `serviceType = "_opensidecar._tcp"`
- `serviceName` = user-editable name (default something like "OpenDisplay"), analogous to iOS's
  `serviceName`.
- TXT record with `id` (stable install id, persisted UUID) and `pv` (protocol version, string).

## Framing (both directions, always)

```
[4 bytes length, big-endian, UInt32][N bytes of payload]
```

No exception — control and video use the same framing. Always read: 4 bytes of length, then
exactly that much payload, and decide what the payload is by its content (see the heuristic below).

## Heuristic: is the payload control JSON or Annex-B video?

Apply in this exact order (from `PhoneReceiver.swift`, function `handleAnnexB`):

```kotlin
val isControlJson = payload.size < 32_768 &&
    payload.isNotEmpty() && payload[0] == '{'.code.toByte() &&
    payload.none { it == 0.toByte() }
```

Why `none { it == 0 }`: video frames also start with a JSON telemetry prefix
(`{"cap":...,"snd":...}`) before the first NAL — but they always contain `0x00` bytes (start codes
`00 00 00 01`), which tells them apart from pure JSON (which never has NUL, even with base64 PNG).

## Video (Mac → Android)

- H.264 Annex-B, NALUs delimited by a 4-byte start code `00 00 00 01` (the sender only emits this
  format, never the 3-byte one).
- Each video payload may have a **telemetry prefix** before the first start code:
  `{"cap":<captureMs>,"snd":<sendMs>}` — use it to compute end-to-end latency (needs the clock
  offset via ping/pong, see below).
- NALU type 7 = SPS, type 8 = PPS, type 6 = SEI (ignore), anything else = video slice (VCL).
- Keyframes come with SPS+PPS embedded before the slice.
- When new SPS+PPS are assembled (or the first time), build the H.264 format/`MediaFormat` and
  (re)create the decoder — an in-band SPS/PPS change means the Mac changed resolution (e.g. the
  Android rotated, which resends `hello` with swapped dimensions and forces the Mac to rebuild the
  virtual display).
- All VCL NALUs of one payload (one "wire frame") form ONE decoded frame — don't decode slice by
  slice.

## Control — Android → Mac messages

All as JSON, one object per payload, `type` field always present:

| type | fields | when |
|---|---|---|
| `hello` | `pixelsWide`, `pixelsHigh`, `scale`, `device` (free string, e.g. `"Android"`), `id` (install id, persisted UUID), `pv` (protocol version) | on connect, and again whenever the orientation changes (dimensions swap) |
| `ping` | `t` (local timestamp in ms, `System.currentTimeMillis()`) | every 2s while connected |
| `touch` | `phase` (`"began"` \| `"moved"` \| `"ended"` \| `"cancelled"`), `x`, `y` (normalized 0–1, top-left origin, video space), `t` (optional: timestamp adjusted by the clock offset, to measure input latency) | touch events |
| `scroll` | `dx`, `dy` (video pixels, "natural scrolling" sign) | 2-finger gesture |
| `kf` | — | when the decoder loses sync (e.g. joined mid-GOP) and needs a new keyframe |
| `sleeping` | — | screen locked — the Mac tears down the virtual display and arms a reconnect for wake |
| `closing` | — | app being closed on purpose — the Mac ends the session without waiting for wake |

## Control — Mac → Android messages

| type | fields | what to do |
|---|---|---|
| `welcome` | `pv`, `min` | identifies the Mac's version; if `pv < WireProtocol.minSupportedPeer`, warn the user that the Mac is out of date |
| `updateRequired` | `target`, `store` (URL), `message` | the Mac refuses the session until the Android updates — show `message` and a link to `store` |
| `pong` | `t` (echo of `ping`), `mt` (Mac clock) | use to estimate the clock offset (see below) |
| `cursor` | `x`, `y` (normalized, or absent when `v=0`), `v` (1=visible, 0=hidden) | draw/hide a local cursor overlaid on the video — it isn't "burned into" the stream, which is faster |
| `cursorImg` | `png` (base64), `nw`, `nh` (size normalized against the display), `ax`, `ay` (normalized hotspot) | update the cursor sprite when its shape changes (arrow ↔ text ↔ resize) |
| `stats` | (diagnostics, sent by the Mac every ~5s, requires no action) | optional: log it |

## Version handshake

```kotlin
object WireProtocol {
    const val VERSION = 3
    const val MIN_SUPPORTED_PEER = 1
    const val ASSUMED_WHEN_ABSENT = 1   // a peer without a pv field = version 1
}
```

Send `pv` in every `hello`. A Mac that doesn't send `pv` in `welcome` (a very old install) must
be treated as protocol 1 — never block the connection in that case; only the explicit
`updateRequired`/`pv < minSupportedPeer` gate decides that.

## Clock sync (NTP-style, for the latency HUD)

From `PhoneReceiver.swift`: keep the last ~15 `(rtt, offset)` samples from each `pong` received,
`rtt = t2 - t1` (t1 = timestamp we sent in the `ping`, t2 = now), `offset = mt - (t1+t2)/2`.
The offset used is that of the **lowest-RTT** sample (the least asymmetric). A frame's end-to-end
latency = `(now + offset) - captureMs` (from the video frame's telemetry prefix).

This is only for the performance HUD — it doesn't block the functional MVP (video + touch work
without it), but it's cheap to implement and gives parity with the iOS app.

## Injection on the Mac side (to understand what touch/scroll do over there)

`Mac/InputInjector.swift`: `began` → mouse down, `moved` → drag (or move if no down), `ended`/
`cancelled` → mouse up. `scroll` becomes a scroll-wheel `CGEvent` in pixels. None of this needs
replicating on the Android — it only has to send the right messages.

**Mirror is view-only (upstream, not a client bug):** `MacSender` only creates the
`InputInjector` on the Extend path, always tied to the virtual display (`inputInjector =
InputInjector(displayID: vd.displayID)`); in Mirror mode it's never created, so `touch`/`scroll`
are still sent by the Android but hit a nil `inputInjector?...` and are silently ignored. Any
request for "touch in Mirror" is a feature request for `peetzweg/opendisplay`, not something
fixable here.
