package com.mmodding.innovation_insights.items;

import com.mmodding.innovation_insights.InnovationInsights;
import com.mmodding.mmodding_lib.library.items.CustomItem;
import com.mmodding.mmodding_lib.library.utils.Colors;
import com.mmodding.mmodding_lib.library.utils.TextUtils;
import com.mmodding.mmodding_lib.library.utils.WorldUtils;
import net.fabricmc.fabric.api.item.v1.FabricItem;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.text.Texts;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.registry.Registry;

public class InnovationEnergyFluxMeter extends CustomItem implements FabricItem {

	public InnovationEnergyFluxMeter(Settings settings) {
		super(settings);
	}

	public static int getIndicatorRate(ItemStack stack) {
		return stack.getOrCreateNbt().getInt("IndicatorRate");
	}

	public static boolean isReturningToBasePos(ItemStack stack) {
		return stack.getOrCreateNbt().getBoolean("ReturningToBasePos");
	}

	private void setIndicatorRate(ItemStack stack, int indicatorRate) {
		stack.getOrCreateNbt().putInt("IndicatorRate", indicatorRate);
	}

	private void setReturningToBasePos(ItemStack stack, boolean returningToBasePos) {
		stack.getOrCreateNbt().putBoolean("ReturningToBasePos", returningToBasePos);
	}

	@Override
	public ActionResult useOnBlock(ItemUsageContext context) {

		if (context.getWorld().isClient()) {
			return super.useOnBlock(context);
		}

		BlockState blockState = context.getWorld().getBlockState(context.getBlockPos());

		if (Registry.BLOCK.getId(blockState.getBlock()).getNamespace().equals(InnovationInsights.id())) {
			if (blockState.hasBlockEntity()) {
				BlockEntity blockEntity = context.getWorld().getBlockEntity(context.getBlockPos());

				if (blockEntity instanceof InnovationInsights.IEF IEF) {

					ItemStack stack = context.getStack();

					this.setReturningToBasePos(stack, false);

					int totalIndicatorRateRange = (int) ((double) IEF.getIEF() / IEF.getCapacity() * 10);

					int indicatorRateRange;

					if (InnovationEnergyFluxMeter.getIndicatorRate(stack) > 0) {
						indicatorRateRange = totalIndicatorRateRange - InnovationEnergyFluxMeter.getIndicatorRate(stack);
					}
					else {
						indicatorRateRange = totalIndicatorRateRange;
					}

					WorldUtils.repeatSyncedTaskEachTimeUntil(
						context.getWorld(), 2, indicatorRateRange * 2L,
						() -> this.setIndicatorRate(stack, InnovationEnergyFluxMeter.getIndicatorRate(stack) + 1)
					);

					WorldUtils.doSyncedTaskAfter(context.getWorld(), indicatorRateRange * 2L + 60, () -> {
						this.setReturningToBasePos(stack, true);
						WorldUtils.repeatSyncedTaskEachTimeUntil(
							context.getWorld(), 5, totalIndicatorRateRange * 5L, () -> {
								if (InnovationEnergyFluxMeter.isReturningToBasePos(stack)) {
									this.setIndicatorRate(stack, InnovationEnergyFluxMeter.getIndicatorRate(stack) - 1);
								}
							}
						);
					});

					if (context.getPlayer() != null) {
						MutableText amount = Texts.bracketed(Text.translatable("ief.innovation_insights.amount"))
							.styled(style -> style.withColor(new Colors.RGB(60, 75, 245).toDecimal()));

						MutableText capacity = Texts.bracketed(Text.translatable("ief.innovation_insights.capacity"))
							.styled(style -> style.withColor(new Colors.RGB(120, 15, 245).toDecimal()));

						Text message = TextUtils.spaceBetween(
							amount,
							Text.literal(String.valueOf(IEF.getIEF())).styled(style -> style.withColor(Formatting.GREEN)),
							Text.literal("/").styled(style -> style.withColor(new Colors.RGB(90, 45, 245).toDecimal())),
							capacity,
							Text.literal(String.valueOf(IEF.getCapacity())).styled(style -> style.withColor(Formatting.RED))
						);

						context.getPlayer().sendMessage(message, true);
					}
				}
			}
		}

		return super.useOnBlock(context);
	}

	@Override
	public boolean allowNbtUpdateAnimation(PlayerEntity player, Hand hand, ItemStack oldStack, ItemStack newStack) {
		return false;
	}
}
