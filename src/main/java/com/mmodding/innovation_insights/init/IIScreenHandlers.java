package com.mmodding.innovation_insights.init;

import com.mmodding.innovation_insights.InnovationInsights;
import com.mmodding.innovation_insights.screenhandlers.generators.AnvilFissionGeneratorScreenHandler;
import com.mmodding.innovation_insights.screenhandlers.CompressorScreenHandler;
import com.mmodding.innovation_insights.screenhandlers.ExtractorScreenHandler;
import com.mmodding.mmodding_lib.library.initializers.ElementsInitializer;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.registry.Registry;

public class IIScreenHandlers implements ElementsInitializer {

    public static final ScreenHandlerType<CompressorScreenHandler> COMPRESSOR_HANDLER = new ScreenHandlerType<>(CompressorScreenHandler::new);

    public static final ScreenHandlerType<ExtractorScreenHandler> EXTRACTOR_HANDLER = new ScreenHandlerType<>(ExtractorScreenHandler::new);

    public static final ScreenHandlerType<AnvilFissionGeneratorScreenHandler> ANVIL_FISSION_GENERATOR_HANDLER = new ScreenHandlerType<>(AnvilFissionGeneratorScreenHandler::new);

	@Override
	public void register() {
		Registry.register(Registry.SCREEN_HANDLER, InnovationInsights.createId("compressor_handler"), COMPRESSOR_HANDLER);
		Registry.register(Registry.SCREEN_HANDLER, InnovationInsights.createId("extractor_handler"), EXTRACTOR_HANDLER);
		Registry.register(Registry.SCREEN_HANDLER, InnovationInsights.createId("anvil_fission_generator_handler"), ANVIL_FISSION_GENERATOR_HANDLER);
	}
}
