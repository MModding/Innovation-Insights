package com.mmodding.innovation_insights.items;

import com.mmodding.mmodding_lib.library.items.CustomItem;
import net.minecraft.block.Block;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;

public class Wrench extends CustomItem {

    public Wrench(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        Block block = context.getWorld().getBlockState(context.getBlockPos()).getBlock();

        assert context.getPlayer() != null;
        context.getPlayer().sendMessage(Text.of(block.getName().getString()), true);

        return super.useOnBlock(context);
    }
}
