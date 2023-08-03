package com.mmodding.innovation_insights.screenhandlers.engines;

import com.mmodding.innovation_insights.init.IIScreenHandlers;
import com.mmodding.mmodding_lib.library.screenhandlers.BasicScreenHandler;
import com.mmodding.mmodding_lib.library.screenhandlers.slots.OutputSlot;
import com.mmodding.mmodding_lib.library.utils.ScreenHandlerUtils;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.screen.slot.Slot;

public class ExtractorScreenHandler extends BasicScreenHandler {

    public ExtractorScreenHandler(int syncId, PlayerInventory playerInventory) {
        this(syncId, playerInventory, new SimpleInventory(7), 7);
    }

    public ExtractorScreenHandler(int syncID, PlayerInventory playerInventory, Inventory inventory, int inventorySize) {
        super(IIScreenHandlers.EXTRACTOR_HANDLER, syncID, playerInventory, inventory, inventorySize);

        this.addSlot(new Slot(this.inventory, 0, 17,34));
        this.addSlot(new OutputSlot(this.inventory, 1, 54, 34));
        this.addSlot(new OutputSlot(this.inventory, 2, 72, 34));
        this.addSlot(new OutputSlot(this.inventory, 3, 90, 34));
        this.addSlot(new OutputSlot(this.inventory, 4, 108, 34));
        this.addSlot(new OutputSlot(this.inventory, 5, 126, 34));
        this.addSlot(new OutputSlot(this.inventory, 6, 144, 34));

		ScreenHandlerUtils.createPlayerSlots(this, playerInventory);
    }
}
