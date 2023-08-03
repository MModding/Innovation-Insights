package com.mmodding.innovation_insights.screenhandlers.generators;

import com.mmodding.innovation_insights.init.IIScreenHandlers;
import com.mmodding.innovation_insights.init.IITags;
import com.mmodding.mmodding_lib.library.screenhandlers.BasicScreenHandler;
import com.mmodding.mmodding_lib.library.screenhandlers.slots.OutputSlot;
import com.mmodding.mmodding_lib.library.screenhandlers.slots.ProperTagSlot;
import com.mmodding.mmodding_lib.library.utils.ScreenHandlerUtils;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;

public class AnvilFissionGeneratorScreenHandler extends BasicScreenHandler {

	public AnvilFissionGeneratorScreenHandler(int syncId, PlayerInventory playerInventory) {
		this(syncId, playerInventory, new SimpleInventory(3), 3);
	}

    public AnvilFissionGeneratorScreenHandler(int syncID, PlayerInventory playerInventory, Inventory inventory, int inventorySize) {
        super(IIScreenHandlers.ANVIL_FISSION_GENERATOR_HANDLER, syncID, playerInventory, inventory, inventorySize);

        this.addSlot(new ProperTagSlot(this.inventory, 0, 80,40, IITags.BATTERIES));
        this.addSlot(new OutputSlot(this.inventory, 1, 54, 40));
        this.addSlot(new OutputSlot(this.inventory, 2, 106, 40));

		ScreenHandlerUtils.createPlayerSlots(this, playerInventory);
    }
}
