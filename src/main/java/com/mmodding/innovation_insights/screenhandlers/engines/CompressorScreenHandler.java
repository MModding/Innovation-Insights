package com.mmodding.innovation_insights.screenhandlers.engines;

import com.mmodding.innovation_insights.init.IIScreenHandlers;
import com.mmodding.mmodding_lib.library.screenhandlers.BasicScreenHandler;
import com.mmodding.mmodding_lib.library.screenhandlers.slots.OutputSlot;
import com.mmodding.mmodding_lib.library.utils.ScreenHandlerUtils;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.screen.slot.Slot;

public class CompressorScreenHandler extends BasicScreenHandler {

    public CompressorScreenHandler(int syncId, PlayerInventory playerInventory) {
        this(syncId, playerInventory, new SimpleInventory(3), 3);
    }

    public CompressorScreenHandler(int syncID, PlayerInventory playerInventory, Inventory inventory, int inventorySize) {
        super(IIScreenHandlers.COMPRESSOR_HANDLER, syncID, playerInventory, inventory, inventorySize);

        this.addSlot(new OutputSlot(this.inventory, 0, 80,49));
        this.addSlot(new Slot(this.inventory, 1, 10, 10));
        this.addSlot(new Slot(this.inventory, 2, 150, 10));

		ScreenHandlerUtils.createPlayerSlots(this, playerInventory);
    }
}
