# EnchantedSlideButton Minecraft Mod

EnchantedSlideButton is a client-side Fabric Minecraft mod that adds a custom slider screen and reusable slider widget. It demonstrates interactive Minecraft mod UI controls, including mouse dragging, keyboard input, value display, and narrator support.

## Install

1. Install JDK 21 and Gradle 8.8.
2. From the project directory, generate the Gradle wrapper with `gradle wrapper --gradle-version 8.8`.
3. Build the mod with `./gradlew build` (Windows: `gradlew.bat build`).
4. Install Fabric Loader 0.16.5 or newer for Minecraft 1.21.1, and Fabric API 0.107.0+1.21.1.
5. Put the built mod jar from `build/libs` and the Fabric API jar in your Minecraft client's `mods` folder. Use the mod jar, not the sources jar.
6. Launch the Fabric profile.

## Use

1. Enter a world and press `O` to open the slider screen. You can rebind the key in Minecraft's Controls settings.
2. Click or drag the slider to choose a value from 0% to 100%.
3. Focus the slider and use Left or Right for one-percent steps, or Home and End for the minimum and maximum.
4. Use Tab or Shift+Tab to switch focus between the slider and Done. Activate Done to close the screen.
5. Press Escape or the inventory key to close the screen.

The value starts at 50%, persists while Minecraft is running, and resets when Minecraft exits. It does not affect gameplay. The mod is client-side and requires no server installation or custom textures.

---

More Minecraft mod projects by EnderDevelopment: https://github.com/EnderDevelopment

Project page: https://dash.enderdevelopment.com/community/86fdab51-c5b5-4197-91cd-21242ea1d5c3?utm_source=github&utm_medium=readme&utm_campaign=auto_publish&utm_content=86fdab51-c5b5-4197-91cd-21242ea1d5c3
