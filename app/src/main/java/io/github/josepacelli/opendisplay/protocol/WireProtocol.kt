// Derived from Shared/Protocol.swift in peetzweg/opendisplay.
// Copyright (c) 2026 Philip Poloczek. Licensed under GPL-3.0.

package io.github.josepacelli.opendisplay.protocol

/**
 * Wire-protocol version contract, ported from the upstream Shared/Protocol.swift.
 * The Mac app is the fixed peer here — these values must match it exactly,
 * see .claude/skills/wire-protocol/SKILL.md.
 */
object WireProtocol {
    const val VERSION = 3
    const val MIN_SUPPORTED_PEER = 1
    const val ASSUMED_WHEN_ABSENT = 1

    /** The Mac must have negotiated at least this `pv` before `pencil` is sendable — below
     * it, an old Mac would just ignore the unknown type and the stylus would go dead, so
     * PROTOCOL.md §6.1 requires degrading to `touch` instead. See [io.github.josepacelli.opendisplay.net.PhoneReceiver.peerSupportsPencil]. */
    const val PENCIL_MIN_PEER = 3
}

/** Control-message `type` string constants (mirrors Swift `WireMessage`). */
object WireMessage {
    const val HELLO = "hello"
    const val PING = "ping"
    const val PONG = "pong"
    const val TOUCH = "touch"
    const val SCROLL = "scroll"
    const val PENCIL = "pencil"
    const val KEYFRAME_REQUEST = "kf"
    const val CURSOR = "cursor"
    const val CURSOR_IMAGE = "cursorImg"
    const val CURSOR_ACK = "cursorAck"
    const val STATS = "stats"
    const val WELCOME = "welcome"
    const val UPDATE_REQUIRED = "updateRequired"
    const val SLEEPING = "sleeping"
    const val CLOSING = "closing"
}

/** `hello.panel` (PROTOCOL.md §6.7): the facts the Mac sizes the extended desktop from, replacing
 * the deprecated top-level `pixelsWide`/`pixelsHigh`/`scale`. */
object HelloPanel {
    /** Builds the `panel` object for this device's current window.
     * `pointsWide`/`pointsHigh` are left out: they describe a "looks like" desktop setting that
     * Android doesn't have, and the spec takes both or neither.
     * @param pixelsWide physical pixels across, current orientation.
     * @param pixelsHigh physical pixels down, current orientation.
     * @param scale display density (pixels per dp), may be fractional.
     * @return the object's fields, or `null` when the values aren't ones the Mac would accept
     * (pixels below 2, or a non-finite or non-positive scale) — the panel size isn't known yet,
     * and the deprecated fields alone are the right thing to send. */
    fun fields(pixelsWide: Int, pixelsHigh: Int, scale: Double): Map<String, Any>? {
        if (pixelsWide < 2 || pixelsHigh < 2 || !scale.isFinite() || scale <= 0.0) return null
        return linkedMapOf("pixelsWide" to pixelsWide, "pixelsHigh" to pixelsHigh, "scale" to scale)
    }
}
