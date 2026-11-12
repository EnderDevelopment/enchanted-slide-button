package com.example.customslidebutton;

import java.util.Objects;
import java.util.function.IntConsumer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.screen.narration.NarrationPart;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

public final class SlideButton extends ClickableWidget {
    private static final int KNOB_SIZE = 18;
    private final IntConsumer valueListener;
    private int value;

    public SlideButton(int x, int y, int width, int value, IntConsumer valueListener) {
        super(x, y, width, 28, Text.translatable("screen.custom_slide_button.value", Math.clamp(value, 0, 100)));
        if (width <= KNOB_SIZE) {
            throw new IllegalArgumentException("Slider width must exceed 18 pixels");
        }
        this.value = Math.clamp(value, 0, 100);
        this.valueListener = Objects.requireNonNull(valueListener);
    }

    public int getValue() {
        return value;
    }

    @Override
    protected void renderWidget(DrawContext context, int mouseX, int mouseY, float delta) {
        int start = getX() + KNOB_SIZE / 2;
        int end = getX() + width - KNOB_SIZE / 2;
        int center = start + Math.round((end - start) * value / 100.0f);
        int top = getY() + 5;
        context.fill(start, getY() + 10, end, getY() + 18, 0xff20242b);
        context.fill(start, getY() + 10, center, getY() + 18, 0xff55c2a5);
        context.fill(center - 9, top, center + 9, top + KNOB_SIZE,
                !active ? 0xff777777 : isFocused() ? 0xffffd166 : isHovered() ? 0xffb9f5df : 0xfff5f7f6);
        context.drawBorder(center - 9, top, KNOB_SIZE, KNOB_SIZE, 0xff101318);
    }

    @Override
    protected void appendClickableNarrations(NarrationMessageBuilder builder) {
        builder.put(NarrationPart.TITLE, getMessage());
        builder.put(NarrationPart.USAGE, Text.translatable("screen.custom_slide_button.hint"));
    }

    @Override
    public void onClick(double mouseX, double mouseY) {
        updateFromMouse(mouseX);
    }

    @Override
    protected void onDrag(double mouseX, double mouseY, double deltaX, double deltaY) {
        updateFromMouse(mouseX);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (!active || !visible || !isFocused()) {
            return false;
        }
        switch (keyCode) {
            case GLFW.GLFW_KEY_LEFT -> setValue(value - 1);
            case GLFW.GLFW_KEY_RIGHT -> setValue(value + 1);
            case GLFW.GLFW_KEY_HOME -> setValue(0);
            case GLFW.GLFW_KEY_END -> setValue(100);
            default -> { return false; }
        }
        return true;
    }

    private void updateFromMouse(double mouseX) {
        double fraction = (mouseX - getX() - KNOB_SIZE / 2.0) / (width - KNOB_SIZE);
        setValue((int) Math.round(Math.clamp(fraction, 0.0, 1.0) * 100));
    }

    private void setValue(int nextValue) {
        int clamped = Math.clamp(nextValue, 0, 100);
        if (clamped != value) {
            value = clamped;
            setMessage(Text.translatable("screen.custom_slide_button.value", value));
            valueListener.accept(value);
        }
    }
}
