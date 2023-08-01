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

public class Extraction implements Recipe<ImplementedInventory> {

    private final Ingredient input;
    private final ItemStack defaultOutput;
    private final ItemStack outputA;
    private final ItemStack outputB;
    private final ItemStack outputC;
    private final ItemStack outputD;
    private final ItemStack outputE;
    private final int outputALuck;
    private final int outputBLuck;
    private final int outputCLuck;
    private final int outputDLuck;
    private final int outputELuck;
    private final Identifier extractionId;
    private final int extractionTime;

    public Extraction(Identifier id, Ingredient ingredient, ItemStack alwaysOutput, ItemStack a, ItemStack b, ItemStack c, ItemStack d, ItemStack e,
                      int aLuck, int bLuck, int cLuck, int dLuck, int eLuck, int time) {
        extractionId = id;
        input = ingredient;
        defaultOutput = alwaysOutput;
        outputA = a;
        outputB = b;
        outputC = c;
        outputD = d;
        outputE = e;
        outputALuck = aLuck;
        outputBLuck = bLuck;
        outputCLuck = cLuck;
        outputDLuck = dLuck;
        outputELuck = eLuck;
        extractionTime = time;
    }

    public static class Type implements RecipeType<Extraction> {}

    static class ExtractionsJsonFormat {

        JsonObject input;

        String defaultOutput;
        int defaultOutputAmount;

        String outputA;
        int outputAAmount;
        int outputALuck;

        String outputB;
        int outputBAmount;
        int outputBLuck;

        String outputC;
        int outputCAmount;
        int outputCLuck;

        String outputD;
        int outputDAmount;
        int outputDLuck;

        String outputE;
        int outputEAmount;
        int outputELuck;

        int extractionTime;

    }

    @Override
    public boolean matches(ImplementedInventory inventory, World world) {
        return input.test(inventory.getStack(0));

    }

    @Override
    public ItemStack craft(ImplementedInventory inventory) {
        return getOutput().copy();
    }

    public Ingredient getInput() {
        return input;
    }

    @Override
    public ItemStack getOutput() {
        return defaultOutput;
    }

    public ItemStack getOutputA() {
        return outputA;
    }

    public ItemStack getOutputB() {
        return outputB;
    }

    public ItemStack getOutputC() {
        return outputC;
    }

    public ItemStack getOutputD() {
        return outputD;
    }

    public ItemStack getOutputE() {
        return outputE;
    }

    public int getOutputALuck() {
        return outputALuck;
    }

    public int getOutputBLuck() {
        return outputBLuck;
    }

    public int getOutputCLuck() {
        return outputCLuck;
    }

    public int getOutputDLuck() {
        return outputDLuck;
    }

    public int getOutputELuck() {
        return outputELuck;
    }

    public int getExtractionTime() {
        return extractionTime;
    }

    @Override
    public boolean fits(int width, int height) {
        return false;
    }

    @Override
    public Identifier getId() {
        return extractionId;
    }

    @Override
    public RecipeSerializer<Extraction> getSerializer() {
        return IIRecipeSerializers.EXTRACTION_SERIALIZER;
    }

    @Override
    public RecipeType<Extraction> getType() {
        return IIRecipeTypes.EXTRACTION;
    }
}
