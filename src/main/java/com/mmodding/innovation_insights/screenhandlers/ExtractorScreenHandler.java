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

public class ExtractorScreenHandler extends ScreenHandler {

    private final Inventory inventory;

    public ExtractorScreenHandler(int syncId, PlayerInventory inv) {
        this(syncId, inv, new SimpleInventory((7)));
    }

    public ExtractorScreenHandler(int syncID, PlayerInventory playerInv, Inventory inv) {
        super(IIScreenHandlers.EXTRACTOR_HANDLER, syncID);
        checkSize(inv, 7);
        this.inventory = inv;

        inventory.onOpen(playerInv.player);

        this.addSlot(new Slot(inv, 0, 17,34));
        this.addSlot(new OutputSlot(inv, 1, 54, 34));
        this.addSlot(new OutputSlot(inv, 2, 72, 34));
        this.addSlot(new OutputSlot(inv, 3, 90, 34));
        this.addSlot(new OutputSlot(inv, 4, 108, 34));
        this.addSlot(new OutputSlot(inv, 5, 126, 34));
        this.addSlot(new OutputSlot(inv, 6, 144, 34));

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
