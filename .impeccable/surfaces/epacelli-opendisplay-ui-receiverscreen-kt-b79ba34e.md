---
version: 1
slug: "epacelli-opendisplay-ui-receiverscreen-kt-b79ba34e"
primary_target: "app/src/main/java/io/github/josepacelli/opendisplay/ui/ReceiverScreen.kt"
related_targets: ["app/src/main/java/io/github/josepacelli/opendisplay/ui/SettingsDialog.kt","app/src/main/java/io/github/josepacelli/opendisplay/ui/ConnectedMenu.kt","app/src/main/java/io/github/josepacelli/opendisplay/ui/CursorOverlay.kt"]
---

# Surface brief: app UI (idle, connected menu, settings, cursor/HUD)

Mode: Operate. Audience: Mac owner with a spare Android tablet/phone. Task: get the Mac to pick this device, then get out of the way. Constraints: Material 3 components and navigation, 7 locales, light + dark, phones and tablets, video/cursor path untouched.

## Direction contract

THESIS: The device as quiet desk hardware — one indicator lamp tells the whole connection story. Refuses the category default of a centered logo card with a status line and outlined buttons.

OWN-WORLD: Braun/Rams. Dark: graphite ground #1C1D1F, panel #232427, raised #2B2C2F, warm off-white ink #ECEAE5, muted #A8A6A0. Light: warm panel #F2F0EB on #E6E3DC, graphite ink. Lamp colours do jobs only: amber #F5A524 listening, green #3DDC84 streaming, red for errors. Perforated dot grille as the panel's one material. Roboto Flex light at display scale for the device name; tabular figures for every number. Pill buttons, off-white filled primary.

STORY: User opens the app, sees the lamp and its device name as the Mac will list it, follows two numbered steps, watches the stage strip advance, then the app disappears behind the desktop. Connected, a slim graphite control strip with vector icons gives zoom, fullscreen, settings, disconnect.

FIRST VIEWPORT: Tablet landscape: rounded graphite panel split in two. Left: amber lamp with halo rings, state label (uppercase, amber), device name at ~96sp light, one line "this is the name on the Mac", stage strip pinned bottom (Listening / Mac found / Streaming). Right over the grille: two numbered step cards, a tabular meta card (network ip:port, version), Settings (filled) + About (tonal) bottom-right. Phone/portrait: stacked, same order.

FORM: Braun Indicator, candidate 3 of 7. Raises: tabular figures (Ikeda), screen-map position pickers (catalog), one 8dp grid (Crouwel), fixed stage strip (folio), idle delay drawn as length (Labanotation). seed:672ed1068523608d

FINISH: unreviewed and undocumented is unfinished; this build ends with the finish review, the verdict, DESIGN.md, and every shipping raster carrying its provenance
