package com.mmodding.innovation_insights.recipes;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class CompressionSerializer implements RecipeSerializer<Compression> {

    @Override
    public Compression read(Identifier id, JsonObject json) {
        Compression.CompressionsJsonFormat recipeJson = new Gson().fromJson(json, Compression.CompressionsJsonFormat.class);

        Ingredient slotLeft = Ingredient.fromJson(recipeJson.slotLeft);
        Ingredient slotRight = Ingredient.fromJson(recipeJson.slotRight);

        Item compressedItem = Registry.ITEM.getOrEmpty(new Identifier(recipeJson.compressedItem)).get();
        ItemStack compressedItemStack = new ItemStack(compressedItem, recipeJson.compressedItemAmount);

        int compressionTime;
        if (recipeJson.compressionTime != 0) compressionTime = recipeJson.compressionTime; else compressionTime = 100;

        return new Compression(
            id,
            compressedItemStack,
            slotLeft,
            slotRight,
            compressionTime
        );
    }

    @Override
    public Compression read(Identifier id, PacketByteBuf buf) {

        Ingredient slotLeft = Ingredient.fromPacket(buf);
        Ingredient slotRight = Ingredient.fromPacket(buf);

        ItemStack compressedItemStack = buf.readItemStack();

        int compressionTime = buf.readInt();

        return new Compression(
            id,
            compressedItemStack,
            slotLeft,
            slotRight,
            compressionTime
        );
    }

    @Override
    public void write(PacketByteBuf buf, Compression recipe) {

        recipe.getInputA().write(buf);
        recipe.getInputB().write(buf);
        buf.writeItemStack(recipe.getOutput());
        buf.writeInt(recipe.getCompressionTime());
    }
}
