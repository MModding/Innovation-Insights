package com.mmodding.innovation_insights.init;

import com.mmodding.innovation_insights.InnovationInsights;
import com.mmodding.innovation_insights.items.Wrench;
import com.mmodding.mmodding_lib.library.initializers.ElementsInitializer;
import com.mmodding.mmodding_lib.library.items.CustomItem;
import com.mmodding.mmodding_lib.library.items.settings.AdvancedItemSettings;

public class IIItems implements ElementsInitializer {

    public static final Wrench WRENCH = new Wrench(new AdvancedItemSettings().group(IIItemGroups.INNOVATION_INSIGHTS_BASICS).maxCount(1));

    public static final CustomItem STEEL_INGOT = new CustomItem(new AdvancedItemSettings().group(IIItemGroups.INNOVATION_INSIGHTS_MATERIALS));

    public static final CustomItem ALUMINIUM_SHARD = new CustomItem(new AdvancedItemSettings().group(IIItemGroups.INNOVATION_INSIGHTS_MATERIALS));

    // AssemblerFurnace Aluminium Shard
    public static final CustomItem ALUMINIUM_INGOT = new CustomItem(new AdvancedItemSettings().group(IIItemGroups.INNOVATION_INSIGHTS_MATERIALS));

    public static final CustomItem ALUMINIUM_THERMAL_PROTECTION = new CustomItem(new AdvancedItemSettings().group(IIItemGroups.INNOVATION_INSIGHTS_MATERIALS));

    public static final CustomItem BAUXITE_FRAGMENT = new CustomItem(new AdvancedItemSettings().group(IIItemGroups.INNOVATION_INSIGHTS_MATERIALS));

	@Override
	public void register() {
		WRENCH.register(InnovationInsights.createId("wrench"));
		STEEL_INGOT.register(InnovationInsights.createId("steel_ingot"));
		ALUMINIUM_SHARD.register(InnovationInsights.createId("aluminium_shard"));
		ALUMINIUM_INGOT.register(InnovationInsights.createId("aluminium_ingot"));
		ALUMINIUM_THERMAL_PROTECTION.register(InnovationInsights.createId("aluminium_thermal_protection"));
		BAUXITE_FRAGMENT.register(InnovationInsights.createId("bauxite_fragment"));
	}
}
