package com.example.customslidebutton;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public final class CustomSlideButtonClient implements ClientModInitializer {
    private int value = 50;

    @Override
    public void onInitializeClient() {
        KeyBinding openScreenKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.custom_slide_button.open", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_O,
                "category.custom_slide_button.controls"));
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (openScreenKey.wasPressed()) {
                if (client.player != null && client.currentScreen == null) {
                    client.setScreen(new SlideButtonScreen(client.player.getInventory(), value,
                            updatedValue -> value = updatedValue));
                }
            }
        });
    }
}
