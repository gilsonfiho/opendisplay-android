---
version: 1
slug: "web-src-app-tsx"
primary_target: "web/src/App.tsx"
related_targets: ["web/src/components/sections/Hero.tsx","web/src/index.css"]
---

# Surface brief: landing page (web/)

Mode: Persuade. Audience: Mac owners with a spare Android tablet or phone. Action: install the two apps (OpenDisplay for Mac, then the Android APK). Proof: real screenshots, the comparison table, open source. Constraints: seven locales with full rewrite allowed (no invented claims, numbers or testimonials), legal pages share the theme, light and dark, build `web/` → `docs/` via pages.yml.

## Direction contract

THESIS: One desktop across two screens — the page itself shows the Mac's desktop continuing onto the tablet. Refuses the category default of a centered headline over a product banner and a row of equal cards.

OWN-WORLD: DESIGN.md's navy world. Dark: ground #0E1526, panel #141C30, raised #1B2440, hairline #2A3452, ink #E8ECF4, muted #9AA4B8, signal blue #4C8DFF used for the seam and live state only. Light: paper ground #E3E8F2/#F2F5FA, ink #0E1526, blue #1E5BD8. Hanken Grotesk light for display, system/Inter for body, tabular figures on numbers, dot grille as material, pill buttons, no shadows except the floating window.

STORY: The visitor reads "One desktop. Two screens.", sees a window sitting across the seam between a Mac desktop and a tablet, and installs both apps from the two-step bar right under it; the sections below prove it (how it works, real screenshots, features, comparison, FAQ) and close on contributing and support.

FIRST VIEWPORT: Nav on top (mark, links, GitHub, theme, language). Display headline at ~96px top-left. A full-bleed strip: Mac desktop on the left two-thirds, the tablet's bezel on the right, a glowing blue seam between them, a light window straddling the seam. Below it a pill install bar: 1 On your Mac → Download for Mac, 2 On your Android → Download APK (current version), then "Free · GPL-3.0 · Android 8.0+".

FORM: Extended strip, candidate 4 of 7 (dealt). Signature interaction: the window slides across the seam once on load (reduced-motion: static). seed:91782c2da26e407f

FINISH: unreviewed and undocumented is unfinished; this build ends with the finish review, the verdict, DESIGN.md, and every shipping raster carrying its provenance
