package com.mmodding.innovation_insights.client.init;

import com.mmodding.innovation_insights.init.IIScreenHandlers;
import com.mmodding.innovation_insights.screens.CompressorScreen;
import com.mmodding.innovation_insights.screens.ExtractorScreen;
import com.mmodding.innovation_insights.screens.generators.AnvilFissionGeneratorScreen;
import com.mmodding.mmodding_lib.library.initializers.ClientElementsInitializer;
import net.minecraft.client.gui.screen.ingame.HandledScreens;

public class IIScreens implements ClientElementsInitializer {

    @Override
    public void registerClient() {
        HandledScreens.register(IIScreenHandlers.COMPRESSOR_HANDLER, CompressorScreen::new);
        HandledScreens.register(IIScreenHandlers.EXTRACTOR_HANDLER, ExtractorScreen::new);
        HandledScreens.register(IIScreenHandlers.ANVIL_FISSION_GENERATOR_HANDLER, AnvilFissionGeneratorScreen::new);
    }
}
