package com.mmodding.innovation_insights;

import com.mmodding.innovation_insights.init.*;
import com.mmodding.mmodding_lib.library.base.MModdingModInitializer;
import com.mmodding.mmodding_lib.library.config.Config;
import com.mmodding.mmodding_lib.library.initializers.ElementsInitializer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;
import org.quiltmc.loader.api.ModContainer;
import team.reborn.energy.api.base.SimpleEnergyItem;
import team.reborn.energy.api.base.SimpleEnergyStorage;

import java.util.ArrayList;
import java.util.List;

public class InnovationInsights implements MModdingModInitializer {

	@Override
	public @Nullable Config getConfig() {
		return null;
	}

	@Override
	public List<ElementsInitializer> getElementsInitializers() {
		List<ElementsInitializer> elementsInitializers = new ArrayList<>();
		elementsInitializers.add(new IIBlocks());
		elementsInitializers.add(new IIBlockEntities());
		elementsInitializers.add(new IIItems());
		elementsInitializers.add(new IIEvents());
		elementsInitializers.add(new IIItemGroups());
		elementsInitializers.add(new IIFeatures());
		elementsInitializers.add(new IIScreenHandlers());
		elementsInitializers.add(new IIRecipeSerializers());
		elementsInitializers.add(new IIRecipeTypes());
		return elementsInitializers;
	}

	@Override
	public void onInitialize(ModContainer mod) {
		MModdingModInitializer.super.onInitialize(mod);
	}

	public static String id() {
		return "innovation_insights";
	}

	public static Identifier createId(String path) {
		return new Identifier(InnovationInsights.id(), path);
	}

	public static boolean excludeBasics(ItemStack stack) {
		return !stack.isOf(IIItems.INNOVATION_ENERGY_FLUX_METER) && !stack.isOf(IIItems.WRENCH);
	}
}
