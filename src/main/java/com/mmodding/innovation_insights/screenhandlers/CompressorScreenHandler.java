package com.mmodding.innovation_insights.screenhandlers;

import com.mmodding.innovation_insights.init.IIScreenHandlers;
import com.mmodding.innovation_insights.inventories.slots.OutputSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;

public class CompressorScreenHandler extends ScreenHandler {

    private final Inventory inventory;

    public CompressorScreenHandler(int syncId, PlayerInventory inv) {
        this(syncId, inv, new SimpleInventory((3)));
    }

    public CompressorScreenHandler(int syncID, PlayerInventory playerInv, Inventory inv) {
        super(IIScreenHandlers.COMPRESSOR_HANDLER, syncID);
        checkSize(inv, 3);
        this.inventory = inv;

        inventory.onOpen(playerInv.player);

        this.addSlot(new OutputSlot(inv, 0, 80,49));
        this.addSlot(new Slot(inv, 1, 10, 10));
        this.addSlot(new Slot(inv, 2, 150, 10));

        for (int m = 0; m < 3; ++m) {
            for (int l = 0; l < 9; ++l) {
                this.addSlot(new Slot(playerInv, l + m * 9 + 9, 8 + l * 18, 84 + m * 18));
            }
        }

        for (int hotBarSlot = 0; hotBarSlot < 9; hotBarSlot++) {
            this.addSlot(new Slot(playerInv, hotBarSlot, 8 + hotBarSlot * 18, 142));
        }
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return this.inventory.canPlayerUse(player);
    }

    @Override
    public ItemStack quickTransfer(PlayerEntity player, int index) {
        return null;
    }
}
