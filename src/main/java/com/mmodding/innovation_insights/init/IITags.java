package com.mmodding.innovation_insights.init;

import com.mmodding.innovation_insights.InnovationInsights;
import net.minecraft.item.Item;
import net.minecraft.tag.TagKey;
import net.minecraft.util.registry.Registry;
import org.quiltmc.qsl.tag.api.QuiltTagKey;
import org.quiltmc.qsl.tag.api.TagType;

public class IITags {

	public static final TagKey<Item> BATTERIES = QuiltTagKey.of(Registry.ITEM_KEY, InnovationInsights.createId("batteries"), TagType.NORMAL);
}
