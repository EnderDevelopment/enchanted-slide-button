package com.example.customslidebutton;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;

/** Slot-free container model used exclusively by the local client screen. */
public final class SlideButtonScreenHandler extends ScreenHandler {
    public SlideButtonScreenHandler(int syncId, PlayerInventory inventory) {
        super(CustomSlideButtonMod.SLIDE_BUTTON, syncId);
    }

    @Override
    public ItemStack quickMove(PlayerEntity player, int slot) {
        // There are no slots or transferable stacks in this screen.
        return ItemStack.EMPTY;
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return true;
    }
}
