---
name: run
description: Build, install and run the OpenDisplay Android client on an emulator or device, and tail its logs.
---

# Running OpenDisplay Android

This project is a pure Android app (Kotlin + Gradle + Compose). There is no server and no
browser-driven mode — it's "install on the device and connect to a real Mac".

## Prerequisites

1. Android SDK installed, with `ANDROID_HOME` set or a `local.properties` at the root pointing
   `sdk.dir=/path/to/Android/sdk`. Without it Gradle fails at the `:app:compileDebugKotlin` step
   for lack of `android.jar`.
2. A physical device or a running emulator (`adb devices` must list at least one).
3. Optional, but recommended for real testing: the
   [opendisplay](https://github.com/peetzweg/opendisplay) Mac app running on the same WiFi
   network (see the repo's README).

## Build + install

```sh
cd <repo root>
./gradlew assembleDebug        # compile only
./gradlew installDebug         # compile + install on the connected device/emulator
./gradlew uninstallDebug       # remove from the device
```

If `./gradlew` doesn't exist yet (wrapper not generated), run this first:
```sh
gradle wrapper --gradle-version 9.4.1 --distribution-type bin
```

## Open and see it running

```sh
adb shell am start -n io.github.josepacelli.opendisplay/.MainActivity
```

## Logs

The whole network/protocol layer logs with the `OpenDisplay` tag (see `Log.kt` — same convention
as the original project's `Log.swift`, a thin wrapper over the native logger). To filter:

```sh
adb logcat -s OpenDisplay:*
```

Video decoding errors (`MediaCodec`) also show up under the `OpenDisplay` tag — don't use
`adb logcat *:E` as a first attempt, it hides the `Log.info` lines that usually explain the
connection state before the error.

## Testing the pipeline without the Mac

There is no "mock mode" in the app — it only makes sense connected to a real sender. To test the
handshake and the UI without setting up the whole Mac, you can write a throwaway Python/Node
script that opens a TCP socket to the device (via `adb reverse` or a direct IP), sends a fake
`hello` and a few Annex-B frames from a test video. Prefer that over modifying the app with
permanent debug shortcuts.

## Common errors

| Symptom | Likely cause |
|---|---|
| `SDK location not found` | missing `local.properties` / `ANDROID_HOME` |
| App opens but stays on "Listening on :9000" forever | the Mac isn't on the same network, or didn't discover the mDNS service (check in logcat that `NsdManager` registered without error) |
| Black screen, "Connected" but no video | the SPS/PPS keyframe hasn't arrived yet, or `MediaCodec` failed to configure — check logcat for `MediaCodec` |
| Touch does nothing on the Mac | Accessibility permission not granted on the Mac side — that's the Mac app's matter, not the Android's |
