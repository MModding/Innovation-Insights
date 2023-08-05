package com.mmodding.innovation_insights.recipes;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.util.Identifier;

public class ExtractionSerializer implements RecipeSerializer<Extraction> {

    public Extraction.DefaultOutputEntry defaultOutput(JsonObject json) {
		return Extraction.DefaultOutputEntry.fromJson(json);
    }

    public Extraction.DefaultOutputEntry defaultOutput(PacketByteBuf buf) {
        return Extraction.DefaultOutputEntry.read(buf);
    }

	public Extraction.AdditionalOutputEntry additionalOutput(JsonObject json) {
		if (json != null) {
        	return Extraction.AdditionalOutputEntry.fromJson(json);
		}
		else {
			return new Extraction.AdditionalOutputEntry(ItemStack.EMPTY, 100);
		}
	}

    public Extraction.AdditionalOutputEntry additionalOutput(PacketByteBuf buf) {
        return Extraction.AdditionalOutputEntry.read(buf);
    }

    @Override
    public Extraction read(Identifier id, JsonObject json) {
        Extraction.Json recipeJson = new Gson().fromJson(json, Extraction.Json.class);

        int time = recipeJson.extractionTime != 0 ? recipeJson.extractionTime : 2000;

        if (recipeJson.input == null || recipeJson.defaultOutput == null) {
            throw new JsonSyntaxException("Missing 'input' or 'defaultOutput' !");
        }

        Ingredient input = Ingredient.fromJson(recipeJson.input);

        Extraction.DefaultOutputEntry defaultOutput = this.defaultOutput(recipeJson.defaultOutput);

        Extraction.AdditionalOutputEntry firstAdditionalOutput = this.additionalOutput(recipeJson.firstAdditionalOutput);
        Extraction.AdditionalOutputEntry secondAdditionalOutput = this.additionalOutput(recipeJson.secondAdditionalOutput);
        Extraction.AdditionalOutputEntry thirdAdditionalOutput = this.additionalOutput(recipeJson.thirdAdditionalOutput);
        Extraction.AdditionalOutputEntry fourthAdditionalOutput = this.additionalOutput(recipeJson.fourthAdditionalOutput);
        Extraction.AdditionalOutputEntry fifthAdditionalOutput = this.additionalOutput(recipeJson.fifthAdditionalOutput);

        return new Extraction(id, time, input, defaultOutput, firstAdditionalOutput, secondAdditionalOutput, thirdAdditionalOutput, fourthAdditionalOutput, fifthAdditionalOutput);
    }

    @Override
    public Extraction read(Identifier id, PacketByteBuf buf) {

        int time = buf.readInt();

        Ingredient input = Ingredient.fromPacket(buf);

        Extraction.DefaultOutputEntry defaultOutput = this.defaultOutput(buf);

        Extraction.AdditionalOutputEntry firstAdditionalOutput = this.additionalOutput(buf);
        Extraction.AdditionalOutputEntry secondAdditionalOutput = this.additionalOutput(buf);
        Extraction.AdditionalOutputEntry thirdAdditionalOutput = this.additionalOutput(buf);
        Extraction.AdditionalOutputEntry fourthAdditionalOutput = this.additionalOutput(buf);
        Extraction.AdditionalOutputEntry fifthAdditionalOutput = this.additionalOutput(buf);

        return new Extraction(id, time, input, defaultOutput, firstAdditionalOutput, secondAdditionalOutput, thirdAdditionalOutput, fourthAdditionalOutput, fifthAdditionalOutput);
    }

    @Override
    public void write(PacketByteBuf buf, Extraction recipe) {

        buf.writeInt(recipe.getExtractionTime());

        recipe.getInput().write(buf);

        recipe.getDefaultOutput().write(buf);

        recipe.getFirstAdditionalOutput().write(buf);
        recipe.getSecondAdditionalOutput().write(buf);
        recipe.getThirdAdditionalOutput().write(buf);
        recipe.getFourthAdditionalOutput().write(buf);
        recipe.getFifthAdditionalOutput().write(buf);
    }
}
