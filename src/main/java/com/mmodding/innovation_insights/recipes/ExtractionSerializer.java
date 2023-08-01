package com.mmodding.innovation_insights.recipes;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ExtractionSerializer implements RecipeSerializer<Extraction> {

    @Override
    public Extraction read(Identifier id, JsonObject json) {
        Extraction.ExtractionsJsonFormat recipeJson = new Gson().fromJson(json, Extraction.ExtractionsJsonFormat.class);

        if (recipeJson.input == null || recipeJson.defaultOutput == null) {
            throw new JsonSyntaxException("Missing 'input' or 'defaultOutput' !");
        }

        Ingredient input = Ingredient.fromJson(recipeJson.input);

        Item defaultOutput = Registry.ITEM.getOrEmpty(new Identifier(recipeJson.defaultOutput)).get();
        ItemStack defaultOutputItemStack = new ItemStack(defaultOutput, recipeJson.defaultOutputAmount);

        ItemStack outputAItemStack;
        int outputALuck;
        if (recipeJson.outputA != null) {
            Item outputA = Registry.ITEM.getOrEmpty(new Identifier(recipeJson.outputA)).get();
            if (recipeJson.outputAAmount == 0) recipeJson.outputAAmount = 1;
            outputAItemStack = new ItemStack(outputA, recipeJson.outputAAmount);
            if (recipeJson.outputALuck == 0) outputALuck = 100; else outputALuck = recipeJson.outputALuck;
        }
        else {
            outputAItemStack = ItemStack.EMPTY;
            outputALuck = 100;
        }

        ItemStack outputBItemStack;
        int outputBLuck;
        if (recipeJson.outputB != null) {
            Item outputB = Registry.ITEM.getOrEmpty(new Identifier(recipeJson.outputB)).get();
            if (recipeJson.outputBAmount == 0) recipeJson.outputBAmount = 1;
            outputBItemStack = new ItemStack(outputB, recipeJson.outputBAmount);
            if (recipeJson.outputBLuck == 0) outputBLuck = 100; else outputBLuck = recipeJson.outputBLuck;
        }
        else {
            outputBItemStack = ItemStack.EMPTY;
            outputBLuck = 100;
        }

        ItemStack outputCItemStack;
        int outputCLuck;
        if (recipeJson.outputC != null) {
            Item outputC = Registry.ITEM.getOrEmpty(new Identifier(recipeJson.outputC)).get();
            if (recipeJson.outputCAmount == 0) recipeJson.outputCAmount = 1;
            outputCItemStack = new ItemStack(outputC, recipeJson.outputCAmount);
            if (recipeJson.outputCLuck == 0) outputCLuck = 100; else outputCLuck = recipeJson.outputCLuck;
        }
        else {
            outputCItemStack = ItemStack.EMPTY;
            outputCLuck = 100;
        }

        ItemStack outputDItemStack;
        int outputDLuck;
        if (recipeJson.outputD != null) {
            Item outputD = Registry.ITEM.getOrEmpty(new Identifier(recipeJson.outputD)).get();
            if (recipeJson.outputDAmount == 0) recipeJson.outputDAmount = 1;
            outputDItemStack = new ItemStack(outputD, recipeJson.outputDAmount);
            if (recipeJson.outputDLuck == 0) outputDLuck = 100; else outputDLuck = recipeJson.outputDLuck;
        }
        else {
            outputDItemStack = ItemStack.EMPTY;
            outputDLuck = 100;
        }

        ItemStack outputEItemStack;
        int outputELuck;
        if (recipeJson.outputE != null) {
            Item outputE = Registry.ITEM.getOrEmpty(new Identifier(recipeJson.outputE)).get();
            if (recipeJson.outputEAmount == 0) recipeJson.outputEAmount = 1;
            outputEItemStack = new ItemStack(outputE, recipeJson.outputEAmount);
            if (recipeJson.outputELuck == 0) outputELuck = 100; else outputELuck = recipeJson.outputELuck;
        }
        else {
            outputEItemStack = ItemStack.EMPTY;
            outputELuck = 100;
        }

        int extractionTime;
        if (recipeJson.extractionTime != 0) extractionTime = recipeJson.extractionTime; else extractionTime = 2000;

        return new Extraction(
            id,
            input,
            defaultOutputItemStack,
            outputAItemStack,
            outputBItemStack,
            outputCItemStack,
            outputDItemStack,
            outputEItemStack,
            outputALuck,
            outputBLuck,
            outputCLuck,
            outputDLuck,
            outputELuck,
            extractionTime
        );
    }

    @Override
    public Extraction read(Identifier id, PacketByteBuf buf) {

        Ingredient input = Ingredient.fromPacket(buf);

        ItemStack defaultOutput = buf.readItemStack();

        ItemStack outputA = buf.readItemStack();
        int outputALuck = buf.readInt();

        ItemStack outputB = buf.readItemStack();
        int outputBLuck = buf.readInt();

        ItemStack outputC = buf.readItemStack();
        int outputCLuck = buf.readInt();

        ItemStack outputD = buf.readItemStack();
        int outputDLuck = buf.readInt();

        ItemStack outputE = buf.readItemStack();
        int outputELuck = buf.readInt();

        int extractionTime = buf.readInt();

        return new Extraction(
            id,
            input,
            defaultOutput,
            outputA,
            outputB,
            outputC,
            outputD,
            outputE,
            outputALuck,
            outputBLuck,
            outputCLuck,
            outputDLuck,
            outputELuck,
            extractionTime
        );
    }

    @Override
    public void write(PacketByteBuf buf, Extraction recipe) {

        recipe.getInput().write(buf);

        buf.writeItemStack(recipe.getOutput());

        buf.writeItemStack(recipe.getOutputA());
        buf.writeInt(recipe.getOutputALuck());

        buf.writeItemStack(recipe.getOutputB());
        buf.writeInt(recipe.getOutputBLuck());

        buf.writeItemStack(recipe.getOutputC());
        buf.writeInt(recipe.getOutputCLuck());

        buf.writeItemStack(recipe.getOutputD());
        buf.writeInt(recipe.getOutputDLuck());

        buf.writeItemStack(recipe.getOutputE());
        buf.writeInt(recipe.getOutputELuck());

        buf.writeInt(recipe.getExtractionTime());
    }
}
