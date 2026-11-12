package com.example.customslidebutton;

import java.util.function.IntConsumer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

/** Local-only screen: input never sends inventory packets to a server. */
public final class SlideButtonScreen extends HandledScreen<SlideButtonScreenHandler> {
    private final IntConsumer valueListener;
    private SlideButton slider;
    private ButtonWidget done;
    private int value;

    public SlideButtonScreen(PlayerInventory inventory, int initialValue, IntConsumer valueListener) {
        super(new SlideButtonScreenHandler(0, inventory), inventory,
                Text.translatable("screen.custom_slide_button.title"));
        this.value = initialValue;
        this.valueListener = valueListener;
        backgroundWidth = 300;
        backgroundHeight = 166;
    }

    @Override
    protected void init() {
        super.init();
        slider = addDrawableChild(new SlideButton(x + 24, y + 64, backgroundWidth - 48, value, next -> {
            value = next;
            valueListener.accept(next);
        }));
        done = addDrawableChild(ButtonWidget.builder(Text.translatable("screen.custom_slide_button.done"),
                button -> close()).dimensions(x + 100, y + 130, 100, 20).build());
        setInitialFocus(slider);
    }

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        context.fill(x, y, x + backgroundWidth, y + backgroundHeight, 0xff15191f);
        context.drawBorder(x, y, backgroundWidth, backgroundHeight, 0xff4e5968);
    }

    @Override
    protected void drawForeground(DrawContext context, int mouseX, int mouseY) {
        context.drawCenteredTextWithShadow(textRenderer, title, backgroundWidth / 2, 18, 0xffffffff);
        context.drawCenteredTextWithShadow(textRenderer,
                Text.translatable("screen.custom_slide_button.value", value),
                backgroundWidth / 2, 43, 0xff55c2a5);
        context.drawCenteredTextWithShadow(textRenderer,
                Text.translatable("screen.custom_slide_button.hint"),
                backgroundWidth / 2, 106, 0xff9ba5b3);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        for (Element child : children()) {
            if (child.mouseClicked(mouseX, mouseY, button)) {
                setFocused(child);
                setDragging(button == GLFW.GLFW_MOUSE_BUTTON_LEFT);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
        return isDragging() && getFocused() != null
                && getFocused().mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        setDragging(false);
        return getFocused() != null && getFocused().mouseReleased(mouseX, mouseY, button);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == GLFW.GLFW_KEY_ESCAPE || client.options.inventoryKey.matchesKey(keyCode, scanCode)) {
            close();
            return true;
        }
        if (keyCode == GLFW.GLFW_KEY_TAB) {
            setFocused(getFocused() == slider ? done : slider);
            return true;
        }
        return getFocused() != null && getFocused().keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public void removed() {
        // Do not run container cleanup against the player's real inventory handler.
        setDragging(false);
    }

    @Override
    public void close() {
        if (client != null) {
            client.setScreen(null);
        }
    }

    @Override
    public boolean shouldPause() {
        return false;
    }
}
