package com.mmodding.innovation_insights.items;

import com.mmodding.innovation_insights.InnovationInsights;
import com.mmodding.mmodding_lib.library.items.CustomItem;
import com.mmodding.mmodding_lib.library.utils.Colors;
import com.mmodding.mmodding_lib.library.utils.TextUtils;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.text.Texts;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.registry.Registry;

public class InnovationEnergyFluxMeter extends CustomItem {

	public InnovationEnergyFluxMeter(Settings settings) {
		super(settings);
	}

	@Override
	public ActionResult useOnBlock(ItemUsageContext context) {
		BlockState blockState = context.getWorld().getBlockState(context.getBlockPos());

		if (context.getPlayer() != null) {
			if (Registry.BLOCK.getId(blockState.getBlock()).getNamespace().equals(InnovationInsights.id())) {
				if (blockState.hasBlockEntity()) {
					BlockEntity blockEntity = context.getWorld().getBlockEntity(context.getBlockPos());

					if (blockEntity instanceof InnovationInsights.IEF IEF) {
						MutableText amount = Texts.bracketed(Text.translatable("ief.innovation_insights.amount"))
							.styled(style -> style.withColor(new Colors.RGB(60, 75, 245).toDecimal()));

						MutableText capacity = Texts.bracketed(Text.translatable("ief.innovation_insights.capacity"))
							.styled(style -> style.withColor(new Colors.RGB(120, 15, 245).toDecimal()));

						Text message = TextUtils.spaceBetween(
							amount,
							Text.literal(String.valueOf(IEF.getEnergyStorage().amount)).styled(style -> style.withColor(Formatting.GREEN)),
							Text.literal("/").styled(style -> style.withColor(new Colors.RGB(90, 45, 245).toDecimal())),
							capacity,
							Text.literal(String.valueOf(IEF.getEnergyStorage().capacity)).styled(style -> style.withColor(Formatting.RED))
						);

						context.getPlayer().sendMessage(message, true);
					}
				}
			}
		}

		return super.useOnBlock(context);
	}
}
