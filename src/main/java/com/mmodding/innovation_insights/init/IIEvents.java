package com.mmodding.innovation_insights.init;

import com.mmodding.innovation_insights.InnovationEnergyFlux;
import com.mmodding.mmodding_lib.library.initializers.ElementsInitializer;
import com.mmodding.mmodding_lib.library.utils.Colors;
import com.mmodding.mmodding_lib.library.utils.TextUtils;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.text.Texts;
import net.minecraft.util.Formatting;
import org.quiltmc.qsl.tooltip.api.client.ItemTooltipCallback;

public class IIEvents implements ElementsInitializer {

    @Override
    public void register() {
        ItemTooltipCallback.EVENT.register((stack, player, context, lines) -> {
            if (stack.getItem() instanceof InnovationEnergyFlux.Item IEF) {

                MutableText amountText = Texts.bracketed(Text.translatable("ief.innovation_insights.amount"))
                    .styled(style -> style.withColor(new Colors.RGB(60, 75, 245).toDecimal()));

                MutableText amountValue = Text.literal(String.valueOf(IEF.getIEF(stack))).styled(style -> style.withColor(Formatting.GREEN));

                MutableText capacityText = Texts.bracketed(Text.translatable("ief.innovation_insights.capacity"))
                    .styled(style -> style.withColor(new Colors.RGB(120, 15, 245).toDecimal()));

                MutableText capacityValue = Text.literal(String.valueOf(IEF.getCapacity(stack))).styled(style -> style.withColor(Formatting.RED));

                lines.add(TextUtils.spaceBetween(amountText, amountValue));
                lines.add(TextUtils.spaceBetween(capacityText, capacityValue));
            }
        });
    }
}
