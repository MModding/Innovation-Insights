package com.mmodding.innovation_insights.client.init;

import com.mmodding.innovation_insights.InnovationInsights;
import com.mmodding.innovation_insights.init.IIItems;
import com.mmodding.innovation_insights.items.InnovationEnergyFluxMeter;
import com.mmodding.mmodding_lib.library.initializers.ClientElementsInitializer;
import net.minecraft.client.item.ModelPredicateProviderRegistry;

public class IIModelPredicates implements ClientElementsInitializer {

    @Override
    public void registerClient() {
        ModelPredicateProviderRegistry.register(
			IIItems.INNOVATION_ENERGY_FLUX_METER,
			InnovationInsights.createId("indicator_rate"),
			(stack, world, entity, seed) -> InnovationEnergyFluxMeter.getIndicatorRate(stack) / 10.0f
		);
    }
}
