# Product

<!-- impeccable:product-schema 1 -->

## Platform

android

## Users
Mac owners who have a spare Android tablet or phone and want it as a real second monitor for free — the open-source counterpart of Sidecar / Duet Display. They set it up once, then mostly look at the Mac desktop on it, not at this app.

## Product Purpose
OpenDisplay Android turns an Android device into an external display for a Mac. It receives low-latency H.264 video from the unchanged upstream Mac app (`peetzweg/opendisplay`) and sends touch/scroll/stylus back. Success: the user opens the app, picks the device on the Mac, and the app disappears behind the Mac's desktop.

## Positioning
The only Android client that speaks the upstream OpenDisplay protocol byte-for-byte: no account, no server, no telemetry, GPL-3.0, discovery by mDNS with zero config on WiFi.

## Operating Context
- Idle screen: app sits open waiting for the Mac (status: listening on :9000, instructions).
- Connected: fullscreen video; the only chrome is a self-hiding floating menu (zoom, immersive fullscreen, settings, disconnect) and an optional perf HUD.
- Settings: full screen, tabs Details / General / About / What's new.
- Used on tablets up to 15.6" and phones, landscape and portrait, often on a desk next to the Mac, any lighting.
- 7 locales (pt-BR default, en, es, ja, ko, pt-PT, zh-Hans).

## Capabilities and Constraints
- Jetpack Compose + Material 3, minSdk 26, targetSdk 36, edge-to-edge.
- The video surface and cursor overlay must stay pixel-accurate and latency-free; UI never sits on the video path.
- Protocol and Mac side never change because of UI work.
- Transports: WiFi/TCP, adb forward, USB accessory (AOA, experimental).

## Brand Commitments
- Name: OpenDisplay. Mark: a "D" drawn as a tablet with a window being dragged into it from
  the left — the icon has to say "second display" at a glance. Blue gradient with a soft glow
  on navy. No Apple or Android logos anywhere in brand art (trademark). Sources in `brand/`.
- The app UI and the landing page share the navy/blue world in DESIGN.md (site since #193).
- Voice: direct, human, no AI tone.

## Evidence on Hand
Screenshots in `web/public/screenshots/` (some outdated, v0.0.15). No testimonials or metrics to claim.

## Product Principles
1. Disappear once connected — the Mac desktop is the product.
2. Zero config: the idle screen tells you exactly the one thing to do on the Mac.
3. Honest status: always show what the connection is doing.
4. Native Android, not an iOS port.
