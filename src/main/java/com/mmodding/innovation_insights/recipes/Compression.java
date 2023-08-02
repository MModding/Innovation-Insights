package com.mmodding.innovation_insights.recipes;

import com.google.gson.JsonObject;
import com.mmodding.innovation_insights.init.IIRecipeSerializers;
import com.mmodding.innovation_insights.init.IIRecipeTypes;
import com.mmodding.innovation_insights.inventories.ImplementedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public class Compression implements Recipe<ImplementedInventory> {

    private final Ingredient inputA;
    private final Ingredient inputB;
    private final ItemStack output;
    private final Identifier compressionId;
    private final int compressionTime;

    public Compression(Identifier id, ItemStack result, Ingredient a, Ingredient b, int time) {
        compressionId = id;
        inputA = a;
        inputB = b;
        output = result;
        compressionTime = time;
    }

    public static class Type implements RecipeType<Compression> {}

    static class CompressionsJsonFormat {
        JsonObject slotLeft;
        JsonObject slotRight;
        String compressedItem;
        int compressedItemAmount;
        int compressionTime;
    }

    @Override
    public boolean matches(ImplementedInventory inventory, World world) {
        return inputA.test(inventory.getStack(1)) && inputB.test(inventory.getStack(2));
    }

    @Override
    public ItemStack craft(ImplementedInventory inventory) {
        return getOutput().copy();
    }

    public Ingredient getInputA() {
        return inputA;
    }

    public Ingredient getInputB() {
        return inputB;
    }

    @Override
    public boolean fits(int width, int height) {
        return false;
    }

    @Override
    public ItemStack getOutput() {
        return output;
    }

    @Override
    public Identifier getId() {
        return compressionId;
    }

    @Override
    public RecipeSerializer<Compression> getSerializer() {
        return IIRecipeSerializers.COMPRESSION_SERIALIZER;
    }

    public int getCompressionTime() {
        return compressionTime;
    }

    @Override
    public RecipeType<Compression> getType() {
        return IIRecipeTypes.COMPRESSION;
    }
}
