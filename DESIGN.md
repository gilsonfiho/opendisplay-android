---
name: OpenDisplay Android
description: Quiet navy desk hardware that turns an Android device into a Mac monitor, then gets out of the way.
colors:
  navy-ground: "#0E1526"
  navy-deep: "#0A101E"
  navy-panel: "#141C30"
  navy-container: "#172036"
  navy-raised: "#1B2440"
  navy-raised-high: "#222C47"
  navy-selected: "#26314F"
  navy-hairline: "#2A3452"
  slate-outline: "#5A6580"
  slate-muted: "#9AA4B8"
  mist-secondary: "#C3CBDA"
  mist-ink: "#E8ECF4"
  signal-blue: "#4C8DFF"
  fault-red: "#FF6B5E"
  fault-container: "#5C1F1A"
  fault-on-container: "#FFDAD5"
  paper: "#F2F5FA"
  paper-ground: "#E3E8F2"
  paper-container: "#EBEFF6"
  paper-high: "#D8DFEC"
  paper-hairline: "#C9D1E0"
  paper-outline: "#7D879B"
  paper-muted: "#4F5970"
  paper-secondary: "#3F4A63"
  signal-blue-light: "#1E5BD8"
  fault-red-light: "#B3261E"
  fault-container-light: "#F9DEDC"
  overlay-strip: "#0E1526E0"
  overlay-danger: "#E5484D"
typography:
  display:
    fontFamily: "Hanken Grotesk, sans-serif"
    fontSize: "88sp"
    fontWeight: 300
    lineHeight: "92sp"
    letterSpacing: "-1.5sp"
  display-medium:
    fontFamily: "Hanken Grotesk, sans-serif"
    fontSize: "56sp"
    fontWeight: 300
    lineHeight: "60sp"
    letterSpacing: "-0.8sp"
  display-small:
    fontFamily: "Hanken Grotesk, sans-serif"
    fontSize: "36sp"
    fontWeight: 300
    lineHeight: "44sp"
  headline:
    fontFamily: "Hanken Grotesk, sans-serif"
    fontSize: "28sp"
    fontWeight: 400
    lineHeight: "36sp"
  title:
    fontFamily: "Hanken Grotesk, sans-serif"
    fontSize: "22sp"
    fontWeight: 500
    lineHeight: "28sp"
  title-medium:
    fontFamily: "Hanken Grotesk, sans-serif"
    fontSize: "16sp"
    fontWeight: 600
    lineHeight: "24sp"
    letterSpacing: "0.15sp"
  title-small:
    fontFamily: "Hanken Grotesk, sans-serif"
    fontSize: "14sp"
    fontWeight: 600
    lineHeight: "20sp"
    letterSpacing: "0.1sp"
  body:
    fontFamily: "Roboto, sans-serif"
    fontSize: "16sp"
    fontWeight: 400
    lineHeight: "24sp"
    letterSpacing: "0.5sp"
  body-medium:
    fontFamily: "Roboto, sans-serif"
    fontSize: "14sp"
    fontWeight: 400
    lineHeight: "20sp"
    letterSpacing: "0.25sp"
  label:
    fontFamily: "Roboto, sans-serif"
    fontSize: "14sp"
    fontWeight: 500
    lineHeight: "20sp"
    letterSpacing: "0.1sp"
  label-medium:
    fontFamily: "Roboto, sans-serif"
    fontSize: "12sp"
    fontWeight: 500
    lineHeight: "16sp"
    letterSpacing: "0.5sp"
  label-small:
    fontFamily: "Roboto, sans-serif"
    fontSize: "11sp"
    fontWeight: 500
    lineHeight: "16sp"
    letterSpacing: "0.5sp"
rounded:
  xs: "6dp"
  sm: "10dp"
  md: "16dp"
  lg: "20dp"
  xl: "28dp"
  full: "9999dp"
spacing:
  hair: "4dp"
  xs: "8dp"
  sm: "12dp"
  md: "16dp"
  lg: "20dp"
  xl: "24dp"
  2xl: "32dp"
  3xl: "40dp"
  panel: "56dp"
components:
  idle-panel:
    backgroundColor: "{colors.navy-panel}"
    rounded: "{rounded.xl}"
    padding: "56dp"
  button-primary:
    backgroundColor: "{colors.mist-ink}"
    textColor: "{colors.navy-ground}"
    rounded: "{rounded.full}"
    height: "52dp"
  button-tonal:
    backgroundColor: "{colors.navy-raised-high}"
    textColor: "{colors.mist-ink}"
    rounded: "{rounded.full}"
    height: "52dp"
  step-card:
    backgroundColor: "{colors.navy-raised}"
    textColor: "{colors.mist-ink}"
    rounded: "{rounded.lg}"
    padding: "20dp"
  step-number:
    backgroundColor: "{colors.mist-ink}"
    textColor: "{colors.navy-ground}"
    rounded: "{rounded.full}"
    size: "36dp"
  settings-group:
    backgroundColor: "{colors.navy-panel}"
    rounded: "{rounded.lg}"
    padding: "4dp 0"
  length-chip:
    backgroundColor: "transparent"
    textColor: "{colors.mist-ink}"
    rounded: "{rounded.sm}"
    padding: "10dp 12dp"
  length-chip-selected:
    backgroundColor: "{colors.navy-selected}"
    textColor: "{colors.mist-ink}"
    rounded: "{rounded.sm}"
  version-tag:
    backgroundColor: "{colors.navy-selected}"
    textColor: "{colors.mist-ink}"
    rounded: "{rounded.xs}"
    padding: "4dp 10dp"
  overlay-strip:
    backgroundColor: "{colors.overlay-strip}"
    textColor: "{colors.mist-ink}"
    rounded: "{rounded.xl}"
    padding: "8dp"
  overlay-button:
    backgroundColor: "{colors.navy-raised}"
    textColor: "{colors.mist-ink}"
    rounded: "{rounded.full}"
    size: "52dp"
  overlay-button-on:
    backgroundColor: "{colors.mist-ink}"
    textColor: "{colors.navy-ground}"
    rounded: "{rounded.full}"
    size: "52dp"
  overlay-button-danger:
    backgroundColor: "{colors.overlay-danger}"
    textColor: "{colors.mist-ink}"
    rounded: "{rounded.full}"
    size: "52dp"
  perf-hud:
    backgroundColor: "{colors.overlay-strip}"
    textColor: "{colors.mist-ink}"
    typography: "{typography.label-medium}"
    rounded: "{rounded.sm}"
    padding: "6dp 10dp"
  unstable-pill:
    backgroundColor: "{colors.overlay-strip}"
    textColor: "{colors.mist-ink}"
    typography: "{typography.body-medium}"
    rounded: "{rounded.full}"
    padding: "12dp 18dp"
  problem-banner:
    backgroundColor: "{colors.fault-container}"
    textColor: "{colors.fault-on-container}"
    typography: "{typography.body-medium}"
    rounded: "{rounded.lg}"
    padding: "16dp"
---

# Design System: OpenDisplay Android

## Overview

**Creative North Star: "The Indicator Lamp"**

The idle screen is built like a piece of desk hardware: one rounded navy panel, a single indicator lamp that tells the connection story, and the device's network name set large and light, because that name is the one thing the user has to find on the Mac. Everything else is quiet: two numbered steps, the network address and version in small tabular figures, Settings and About as pill buttons. Once the Mac connects, the panel unmounts and the screen is the Mac's desktop. The only chrome left is a slim dark strip and an optional HUD.

The palette is navy and blue, taken from the blue app icon. Saturated colour is spent only on state: signal blue when listening, red for a problem, slate when off. The one material is a perforated dot grille behind the steps. Depth comes from tonal steps of navy, not shadows. Components are stock Material 3 (Button, FilledTonalButton, Switch, TopAppBar, tabs) restyled by the theme, so the app reads as native Android.

This build diverges from the original direction contract in five places, all on purpose. (1) Graphite and amber became navy and blue, because the owner asked for a dark blue that matches the app icon. (2) The display face is Hanken Grotesk, not Roboto Flex: Roboto is the platform family and can't be the app's own display voice. It ships as four static weight files because `FontVariation` on a resource font did not apply weight on the test device. (3) The planned three-stage strip (Listening / Mac found / Streaming) became three lamp colours. The app has no real "Mac found" state, and the idle screen unmounts on connect, so a "Streaming" stage would never be seen. (4) Network and version meta sit bottom-left of the idle panel, in the slot the stage strip held, instead of in a card on the grille side. (5) Overlay chrome stays fixed dark navy whatever the system theme, because it sits on the Mac's desktop.

**Key Characteristics:**
- One panel, one lamp, one large name. The lamp colour is the status.
- Navy tonal layering, no shadows.
- Hanken Grotesk light for display, semibold for small titles. Body and labels use the system face.
- Tabular figures on every number: port, IP, version, step numbers, delays, HUD.
- Pill buttons. Off-white filled primary on dark, navy filled primary on light.
- Overlay chrome on video is always dark navy, translucent, and hides itself.

## Colors

A cool navy family in two schemes (dark and light), with one signal blue and one red, both used only to report state.

### Primary
- **Mist Ink** (`mist-ink`): primary in the dark scheme. Text on navy, the filled Settings button, step number discs, and the "on" state of overlay buttons.
- **Navy Ground** (`navy-ground`): primary in the light scheme, and the dark scheme's background and surface. Also the ink on Mist Ink fills.

### Secondary
- **Mist Secondary** (`mist-secondary`) / **Paper Secondary** (`paper-secondary`): secondary text roles in dark and light. Used sparingly.

### Tertiary
- **Signal Blue** (`signal-blue`, light scheme `signal-blue-light`): the listening lamp and the status line next to it. This is the only blue in the app, and it always means "waiting for the Mac, all good".
- **Fault Red** (`fault-red`, light scheme `fault-red-light`): the problem lamp and status line (no WiFi, VPN active). The problem banner uses its container pair (`fault-container` / `fault-on-container`, light `fault-container-light`).

### Neutral
- **Navy Panel** (`navy-panel`): the idle panel and settings groups (surfaceContainerLow).
- **Navy Container / Raised / Raised High** (`navy-container`, `navy-raised`, `navy-raised-high`): tonal steps for step cards (`navy-raised`) and tonal buttons (`navy-raised-high`).
- **Navy Deep** (`navy-deep`): lowest container.
- **Navy Selected** (`navy-selected`): primaryContainer. Selected length chips, version tags.
- **Navy Hairline** (`navy-hairline`): outlineVariant. Dividers, grille dots, chip borders, the overlay strip border.
- **Slate Outline** (`slate-outline`): outline, and the "off" lamp in the dark scheme.
- **Slate Muted** (`slate-muted`): secondary text (onSurfaceVariant), and the "off" lamp in the light scheme.
- **Paper family** (`paper`, `paper-ground`, `paper-container`, `paper-high`, `paper-hairline`, `paper-outline`, `paper-muted`): the light scheme's surface, background, containers, divider, outline and muted text, mapped role for role onto the navy family.
- **Overlay Strip** (`overlay-strip`): Navy Ground at about 88% alpha. Background of the connected menu, perf HUD and unstable pill, in both themes.
- **Overlay Danger** (`overlay-danger`): the Disconnect button and the unstable-connection dot on the overlay.

### Named Rules
**The Lamp Rule.** Saturated colour names a connection state and nothing else: blue is listening, red is a problem, slate is off. No accent on decoration, headings or icons.

**The Desktop Rule.** Anything drawn over the Mac's video uses the fixed overlay navy (`OverlayColors`), never the theme scheme. Whatever sits behind it is someone else's desktop.

## Typography

**Display Font:** Hanken Grotesk (static instances at 300, 400, 500, 600, bundled in `res/font/`)
**Body Font:** system sans (Roboto on stock Android)

**Character:** Hanken Grotesk is a calm, slightly technical grotesk, used light at display size so the device name reads like a model plate. Body and labels stay on the system face so longer text and controls feel like the rest of Android.

### Hierarchy
- **Display** (300, 88/92sp, -1.5sp tracking): the device name on the wide idle panel. Drops to Display Medium (56/60sp) in the single-column layout, and to Display Small (36/44sp) on short or narrow screens.
- **Headline** (400 at large/medium, 500 at small; Material sizes): available in the scale. The current screens barely use it.
- **Title** (500, 22/28sp): app bar titles.
- **Title Medium / Small** (600, 16sp and 14sp): the lamp's status line (with tabular figures), step numbers, settings category headers in muted ink.
- **Body** (system 400, 16/24sp): step text, the "this is the name on the Mac" line. Body Medium (14/20sp) for banners and settings secondary text.
- **Label** (system 500, 14/20sp): button text, meta lines, length-chip values. Label Medium (12/16sp) for the perf HUD. Label Small (11/16sp) for the captions under overlay buttons.

### Named Rules
**The Tabular Figures Rule.** Every number on screen sets `fontFeatureSettings = "tnum"`: port, IP:port, version, step numbers, delays, HUD readouts. Numbers must not jitter as they change.

**The Two Faces Rule.** Hanken Grotesk carries display, headline and title. Body and label stay on the system face. Don't put Hanken on running text, and don't put the system face at display size.

## Layout

The idle screen is a single panel inset 16dp from the safe drawing area, at the `xl` radius. When the window is landscape and at least 600dp wide, it splits into two equal columns. The left column holds the status block (lamp, status line, device name, explainer), vertically centred, with the network/version meta pinned to the bottom. The right column has the grille filling it, with the step card and the buttons centred on top and the buttons aligned to the end. Panel padding is 56dp, or 24dp when height is under 480dp (a phone on its side). Below that breakpoint, the panel becomes one scrolling column with 24dp padding: status block, steps, meta, buttons (end-aligned), then a 96dp strip of grille as the closing material.

Settings is a full-screen dialog with a top app bar and tabs. The content is capped at 720dp wide and centred, with 16dp side padding. Groups are separated by 24dp.

Spacing uses a 4dp base: 4, 8, 12, 16, 20, 24, 32, 40, 56. The contract asked for a strict 8dp grid; the build also uses 12 and 20 (and 6 and 10 inside small chips). Those values are now part of the system.

Connected, the video box is aspect-correct and centred on black. Overlay chrome is placed against it: the menu docks to a draggable screen edge, the HUD goes to the corner the user picked, banners sit top-centre at 32dp, and the unstable pill sits bottom-centre at 24dp.

## Elevation & Depth

Flat. No shadows or elevation anywhere in the UI code. Depth is tonal: background `navy-ground`, then the panel (`navy-panel`), then raised cards (`navy-raised`) and tonal buttons (`navy-raised-high`). The light scheme does the same with the paper family. On the video, overlays get their separation from translucent navy plus a 1dp `navy-hairline` border, not from a shadow.

### Named Rules
**The Tonal Step Rule.** To lift something, move it one step up the navy (or paper) container ramp. Don't add a shadow.

## Shapes

Rounded, soft-cornered hardware. Material shape scale: extraSmall 6dp (version tags), small 10dp (length chips, HUD), medium 16dp (screen-map picker frame), large 20dp (step card, settings groups, problem banner), extraLarge 28dp (idle panel, overlay strip). Every button, overlay action, step number, lamp and status dot is a full circle or pill. Borders are 1dp hairlines, except the screen-map picker frame, which uses a 2dp outline to read as a monitor bezel.

## Components

### Buttons
- **Shape:** full pill, minimum height 52dp, a 20dp leading icon with an 8dp gap.
- **Primary (filled):** Settings. `mist-ink` on navy in dark, `navy-ground` on paper in light.
- **Tonal:** About. `navy-raised-high` with ink text. It sits before the primary in a row with a 12dp gap.
- **Outlined:** used only for the About tab's link buttons.
- **States:** Material 3 defaults (ripple, state layers). No custom hover or press styling.

### Chips
- **Idle-delay length chips:** a row of radio-role chips at the `sm` radius with a 1dp border. Each one shows the delay in seconds (Label, tabular) over a 4dp bar whose length is proportional to the delay, up to 72dp. The delay is drawn as a length. Selected: `navy-selected` fill, primary-coloured border and bar. Unselected: transparent, `navy-hairline` border, outline-coloured bar. Minimum height 48dp.
- **Version tag:** `navy-selected` at the `xs` radius, 4/10dp padding, on changelog cards.

### Cards / Containers
- **Idle panel:** `navy-panel`, `xl` radius, 56dp padding (24dp when short).
- **Step card:** `navy-raised`, `lg` radius. Each row is 20dp padding, a 36dp `mist-ink` number disc with tabular Title Small, a 20dp gap, then Body text. Rows are split by an inset `navy-hairline` divider starting at 76dp.
- **Settings group:** `navy-panel`, `lg` radius, 4dp vertical padding, rows inside. A category header in Title Small, muted, sits above it with 16dp inset.
- **Changelog card:** container surface, 20dp padding, version tag plus bullet list.

### Navigation
Settings uses a stock Material 3 TopAppBar (close icon, title, a filled Save action) and tabs: Details / General / About / What's new. No custom navigation chrome.

### Indicator Lamp (signature)
A 64dp canvas: a 12dp-radius solid core in the state colour, with two static halos at 18% alpha (+8dp) and 10% alpha (+18dp). While listening, a 2dp stroke ring pulses outward 20dp and fades from 45% alpha over 2200ms (LinearOutSlowIn, repeating). Next to it, 16dp away, the status line is Title Medium tabular in the same state colour (muted ink when off).

### Grille (signature)
Perforated material: 2dp-radius dots on a 16dp square grid in `navy-hairline` (`paper-hairline` in light). It fills the right half of the wide idle panel, or a 96dp band at the bottom of the stacked layout. It is the panel's only texture.

### Connected Menu (overlay)
A pill strip docked to a screen edge: `overlay-strip` fill, 1dp `navy-hairline` border, `xl` radius, 8dp padding, 10dp gaps. A 48dp grip toggles it open. 52dp circular actions (zoom, fullscreen, settings, disconnect) carry Label Small captions underneath, up to 68dp wide. Off is `navy-raised`, on is `mist-ink` with navy icon, Disconnect is `overlay-danger`. A 1dp hairline separates Disconnect from the rest. Expand: fade 200ms plus expand 250ms FastOutSlowIn. Collapse: fade 120ms plus shrink 160ms. Background colour changes animate. It hides on touch or cursor activity and comes back after the user-set idle delay.

### Perf HUD and Unstable Pill (overlay)
HUD: tabular Label Medium on `overlay-strip` at the `sm` radius, 6/10dp padding, in the user-chosen corner. Unstable pill: `overlay-strip` full pill, 12/18dp padding, a 10dp `overlay-danger` dot, then Body Medium.

### Screen-Map Picker
The HUD position is picked on a 16:10 miniature screen (up to 280dp wide, 2dp outline frame, `md` radius). Each corner is a 72x48dp radio target. The selected corner shows a 40x14dp readout bar; the others show a 10dp `navy-hairline` dot.

### Cursor
The fallback remote cursor is a 5dp-radius white dot with a 1.5dp black stroke. It is plain black and white on purpose, not themed, because it has to show up on any desktop. Decoded sprites from the Mac are drawn as-is.

### Icons
Material Symbols Rounded as vector drawables (`ic_*.xml`), 24dp viewport, tinted by context: 18dp in meta lines, 20dp in buttons and banners.

## Do's and Don'ts

### Do:
- **Do** let the lamp carry state: `signal-blue` listening, `fault-red` problem, slate off, and colour the status line to match.
- **Do** set every number with tabular figures (`tnum`).
- **Do** lift surfaces by stepping up the navy/paper container ramp: panel, raised, raised-high.
- **Do** use `OverlayColors` (translucent navy, hairline border, pill shapes) for anything on top of the video, in both themes.
- **Do** keep Hanken Grotesk light (300) at display size for the device name, and semibold (600) for small titles.
- **Do** use stock Material 3 components restyled by the theme, with pill buttons at least 52dp tall and 48dp touch targets.

### Don't:
- **Don't** use blue or red as decoration. If a colour isn't reporting connection state, it should be navy, mist or slate.
- **Don't** add shadows or elevation to separate surfaces.
- **Don't** theme overlay chrome from the system scheme, or show the idle panel while connected.
- **Don't** use the system face (Roboto) at display size, or Hanken Grotesk for body text.
- **Don't** add a stage strip or a "Mac found" state. The app can't detect it, and the lamp already covers what it can.
