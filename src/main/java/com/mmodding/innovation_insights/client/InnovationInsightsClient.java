package com.mmodding.innovation_insights.client;

import com.mmodding.innovation_insights.client.init.IIRenderLayers;
import com.mmodding.innovation_insights.client.init.IIScreens;
import com.mmodding.mmodding_lib.library.base.MModdingClientModInitializer;
import com.mmodding.mmodding_lib.library.config.Config;
import com.mmodding.mmodding_lib.library.initializers.ClientElementsInitializer;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class InnovationInsightsClient implements MModdingClientModInitializer {

    @Override
    public @Nullable Config getClientConfig() {
        return null;
    }

    @Override
    public List<ClientElementsInitializer> getClientElementsInitializers() {
        List<ClientElementsInitializer> clientElementsInitializers = new ArrayList<>();
        clientElementsInitializers.add(new IIRenderLayers());
        clientElementsInitializers.add(new IIScreens());
        return clientElementsInitializers;
    }
}
