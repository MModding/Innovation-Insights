package com.mmodding.innovation_insights.init;

import com.mmodding.innovation_insights.InnovationInsights;
import com.mmodding.mmodding_lib.library.initializers.ElementsInitializer;
import com.mmodding.mmodding_lib.library.worldgen.features.defaults.CustomOreFeature;
import net.minecraft.world.dimension.DimensionOptions;
import net.minecraft.world.gen.feature.OreConfiguredFeatures;
import net.minecraft.world.gen.feature.OreFeatureConfig;

import java.util.List;

public class IIFeatures implements ElementsInitializer {

    public static final List<OreFeatureConfig.Target> BAUXITE_ORE_TARGETS = List.of(
        OreFeatureConfig.createTarget(OreConfiguredFeatures.STONE_ORE_REPLACEABLES, IIBlocks.BAUXITE_ORE.getDefaultState()),
        OreFeatureConfig.createTarget(OreConfiguredFeatures.DEEPSLATE_ORE_REPLACEABLES, IIBlocks.DEEPSLATE_BAUXITE_ORE.getDefaultState())
    );

    public static final CustomOreFeature BAUXITE_ORE_FEATURE = new CustomOreFeature(5, 5, -60, 22, BAUXITE_ORE_TARGETS);

    @Override
    public void register() {
        BAUXITE_ORE_FEATURE.register(InnovationInsights.createId("bauxite_ore_feature"));

        BAUXITE_ORE_FEATURE.addDefaultToBiomes(ctx -> ctx.canGenerateIn(DimensionOptions.OVERWORLD));
    }
}
