package com.mmodding.innovation_insights.recipes;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.mmodding.mmodding_lib.library.utils.RecipeSerializationUtils;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.registry.Registry;

public class CompressionSerializer implements RecipeSerializer<Compression> {

    @Override
    public Compression read(Identifier id, JsonObject json) {
        Compression.CompressionsJsonFormat recipeJson = new Gson().fromJson(json, Compression.CompressionsJsonFormat.class);

		int time = recipeJson.compressionTime != 0 ? recipeJson.compressionTime : 100;

		DefaultedList<Ingredient> ingredients = RecipeSerializationUtils.getIngredients(json, "ingredients", 2);

		ItemStack result = new ItemStack(
			Registry.ITEM.getOrEmpty(new Identifier(recipeJson.result)).orElseThrow(),
			recipeJson.count
		);

        return new Compression(id, time, ingredients, result);
    }

    @Override
    public Compression read(Identifier id, PacketByteBuf buf) {

		int time = buf.readInt();

		DefaultedList<Ingredient> ingredients = RecipeSerializationUtils.readIngredients(buf);

		ItemStack result = buf.readItemStack();

        return new Compression(id, time, ingredients, result);
    }

    @Override
    public void write(PacketByteBuf buf, Compression recipe) {
		buf.writeInt(recipe.getCompressionTime());
		RecipeSerializationUtils.writeIngredients(buf, recipe.getIngredients());
        buf.writeItemStack(recipe.getOutput());
        buf.writeInt(recipe.getCompressionTime());
    }
}
