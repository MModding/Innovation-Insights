package com.mmodding.innovation_insights.recipes;

import com.mmodding.innovation_insights.init.IIRecipeSerializers;
import com.mmodding.innovation_insights.init.IIRecipeTypes;
import com.mmodding.innovation_insights.inventories.ImplementedInventory;
import com.mmodding.mmodding_lib.library.utils.RecipeUtils;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;

public class Compression implements Recipe<ImplementedInventory> {

	private final Identifier compressionId;
	private final int compressionTime;
	private final DefaultedList<Ingredient> ingredients;
	private final ItemStack result;

    public Compression(Identifier compressionId, int compressionTime, DefaultedList<Ingredient> ingredients, ItemStack result) {
		this.compressionId = compressionId;
		this.compressionTime = compressionTime;
		this.ingredients = ingredients;
		this.result = result;
    }

    public static class Type implements RecipeType<Compression> {}

    static class CompressionsJsonFormat {
		int compressionTime;
		String result;
		int count;
    }

	@Override
	public Identifier getId() {
		return this.compressionId;
	}

	public int getCompressionTime() {
		return this.compressionTime;
	}

	@Override
	public DefaultedList<Ingredient> getIngredients() {
		return this.ingredients;
	}

	@Override
	public ItemStack getOutput() {
		return this.result;
	}

	@Override
	public ItemStack craft(ImplementedInventory inventory) {
		return getOutput().copy();
	}

	@Override
	public boolean matches(ImplementedInventory inventory, World world) {
		return RecipeUtils.ingredientMatches(inventory, IntArrayList.of(1, 2), this.getIngredients());
	}

    @Override
    public boolean fits(int width, int height) {
        return false;
    }

    @Override
    public RecipeSerializer<Compression> getSerializer() {
        return IIRecipeSerializers.COMPRESSION_SERIALIZER;
    }

    @Override
    public RecipeType<Compression> getType() {
        return IIRecipeTypes.COMPRESSION;
    }
}
