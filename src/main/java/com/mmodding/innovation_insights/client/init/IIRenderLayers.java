package com.mmodding.innovation_insights.client.init;

import com.mmodding.innovation_insights.init.IIBlocks;
import com.mmodding.mmodding_lib.library.initializers.ClientElementsInitializer;

public class IIRenderLayers implements ClientElementsInitializer {

    @Override
    public void registerClient() {
        IIBlocks.THERMAL_REACTOR_CONTAINER.cutout();
        IIBlocks.THERMAL_GLASS.cutout();
        IIBlocks.COMPRESSOR.cutout();
    }
}
