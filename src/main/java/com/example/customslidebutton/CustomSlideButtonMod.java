package com.example.customslidebutton;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;

public final class CustomSlideButtonMod implements ModInitializer {
    public static final String MOD_ID = "custom_slide_button";
    public static ScreenHandlerType<SlideButtonScreenHandler> SLIDE_BUTTON;

    @Override
    public void onInitialize() {
        SLIDE_BUTTON = ScreenHandlerRegistry.registerSimple(
                Identifier.of(MOD_ID, "slide_button"), SlideButtonScreenHandler::new);
    }
}
