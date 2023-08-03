package com.mmodding.innovation_insights.items;

import com.mmodding.innovation_insights.InnovationEnergyFlux;
import com.mmodding.mmodding_lib.library.items.CustomItem;
import net.minecraft.item.ItemStack;

public class CopperBattery extends CustomItem implements InnovationEnergyFlux.Item {

	public CopperBattery(Settings settings) {
		super(settings);
	}

	@Override
	public long getEnergyCapacity(ItemStack stack) {
		return 100000;
	}

	@Override
	public long getEnergyMaxInput(ItemStack stack) {
		return 10000;
	}

	@Override
	public long getEnergyMaxOutput(ItemStack stack) {
		return 10000;
	}

	@Override
	public boolean isItemBarVisible(ItemStack stack) {
		return this.isIEFVisible(stack);
	}

	@Override
	public int getItemBarStep(ItemStack stack) {
		return this.getIEFStep(stack);
	}

	@Override
	public int getItemBarColor(ItemStack stack) {
		return this.getIEFColor(stack).toDecimal();
	}
}
