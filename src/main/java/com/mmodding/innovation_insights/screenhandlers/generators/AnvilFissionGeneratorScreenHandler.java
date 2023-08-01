package com.mmodding.innovation_insights.screenhandlers.generators;

import com.mmodding.innovation_insights.init.IIScreenHandlers;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;

public class AnvilFissionGeneratorScreenHandler extends ScreenHandler {

    private final Inventory inventory;

    public AnvilFissionGeneratorScreenHandler(int syncId, PlayerInventory inv) {
        this(syncId, inv, new SimpleInventory((1)));
    }

    public AnvilFissionGeneratorScreenHandler(int syncID, PlayerInventory playerInv, Inventory inv) {
        super(IIScreenHandlers.ANVIL_FISSION_GENERATOR_HANDLER, syncID);
        checkSize(inv, 1);
        this.inventory = inv;

        inventory.onOpen(playerInv.player);

        this.addSlot(new Slot(inv, 0, 80,24));

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
