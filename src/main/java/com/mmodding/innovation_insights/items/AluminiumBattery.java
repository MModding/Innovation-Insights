package com.mmodding.innovation_insights.items;

import com.mmodding.innovation_insights.InnovationEnergyFlux;
import net.minecraft.item.ItemStack;

public class AluminiumBattery extends AbstractBattery implements InnovationEnergyFlux.Item {

	public AluminiumBattery(Settings settings) {
		super(settings);
	}

	@Override
	public long getEnergyCapacity(ItemStack stack) {
		return 250000;
	}

	@Override
	public long getEnergyMaxInput(ItemStack stack) {
		return 25000;
	}

	@Override
	public long getEnergyMaxOutput(ItemStack stack) {
		return 25000;
	}
}
