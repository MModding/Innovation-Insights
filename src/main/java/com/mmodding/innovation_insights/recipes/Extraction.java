package com.mmodding.innovation_insights.recipes;

import com.google.gson.JsonObject;
import com.mmodding.innovation_insights.init.IIRecipeSerializers;
import com.mmodding.innovation_insights.init.IIRecipeTypes;
import com.mmodding.innovation_insights.inventories.ImplementedInventory;
import com.mmodding.mmodding_lib.library.utils.RecipeSerializationUtils;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.world.World;

public class Extraction implements Recipe<ImplementedInventory> {

    private final Identifier extractionId;
    private final int extractionTime;
    private final Ingredient input;
    private final DefaultOutputEntry defaultOutput;
    private final AdditionalOutputEntry firstAdditionalOutput;
    private final AdditionalOutputEntry secondAdditionalOutput;
    private final AdditionalOutputEntry thirdAdditionalOutput;
    private final AdditionalOutputEntry fourthAdditionalOutput;
    private final AdditionalOutputEntry fifthAdditionalOutput;

    public Extraction(Identifier extractionId, int extractionTime, Ingredient input, DefaultOutputEntry defaultOutput, AdditionalOutputEntry firstAdditionalOutput, AdditionalOutputEntry secondAdditionalOutput, AdditionalOutputEntry thirdAdditionalOutput, AdditionalOutputEntry fourthAdditionalOutput, AdditionalOutputEntry fifthAdditionalOutput) {
        this.extractionId = extractionId;
        this.extractionTime = extractionTime;
        this.input = input;
        this.defaultOutput = defaultOutput;
        this.firstAdditionalOutput = firstAdditionalOutput;
        this.secondAdditionalOutput = secondAdditionalOutput;
        this.thirdAdditionalOutput = thirdAdditionalOutput;
        this.fourthAdditionalOutput = fourthAdditionalOutput;
        this.fifthAdditionalOutput = fifthAdditionalOutput;
    }

    public static class Type implements RecipeType<Extraction> {}

    public static class Json {
        int extractionTime;
        JsonObject input;
        JsonObject defaultOutput;
        JsonObject firstAdditionalOutput;
        JsonObject secondAdditionalOutput;
        JsonObject thirdAdditionalOutput;
        JsonObject fourthAdditionalOutput;
        JsonObject fifthAdditionalOutput;
    }

    @Override
    public Identifier getId() {
        return this.extractionId;
    }

    public int getExtractionTime() {
        return extractionTime;
    }

    public Ingredient getInput() {
        return this.input;
    }

    @Override
    public ItemStack getOutput() {
        return this.getDefaultOutput().stack();
    }

	public DefaultOutputEntry getDefaultOutput() {
		return this.defaultOutput;
	}
    public AdditionalOutputEntry getFirstAdditionalOutput() {
        return firstAdditionalOutput;
    }

    public AdditionalOutputEntry getSecondAdditionalOutput() {
        return secondAdditionalOutput;
    }

    public AdditionalOutputEntry getThirdAdditionalOutput() {
        return thirdAdditionalOutput;
    }

    public AdditionalOutputEntry getFourthAdditionalOutput() {
        return fourthAdditionalOutput;
    }

    public AdditionalOutputEntry getFifthAdditionalOutput() {
        return fifthAdditionalOutput;
    }

    @Override
    public ItemStack craft(ImplementedInventory inventory) {
        return getOutput().copy();
    }

    @Override
    public boolean matches(ImplementedInventory inventory, World world) {
        return input.test(inventory.getStack(0));
    }

    @Override
    public boolean fits(int width, int height) {
        return false;
    }

    @Override
    public RecipeSerializer<Extraction> getSerializer() {
        return IIRecipeSerializers.EXTRACTION_SERIALIZER;
    }

    @Override
    public RecipeType<Extraction> getType() {
        return IIRecipeTypes.EXTRACTION;
    }

	public record DefaultOutputEntry(ItemStack stack) {

		public static DefaultOutputEntry fromJson(JsonObject json) {
			return new DefaultOutputEntry(RecipeSerializationUtils.getStack(json));
		}

        public static DefaultOutputEntry read(PacketByteBuf buf) {
            return new DefaultOutputEntry(buf.readItemStack());
        }

        public void write(PacketByteBuf buf) {
            buf.writeItemStack(this.stack());
        }
	}

	public record AdditionalOutputEntry(ItemStack stack, int luck) {

        public static AdditionalOutputEntry fromJson(JsonObject json) {
            ItemStack stack = RecipeSerializationUtils.getStack(json);
            int luck = JsonHelper.getInt(json, "luck");
            luck = luck != 0 ? luck : 1;
            return new AdditionalOutputEntry(stack, luck);
        }

        public static AdditionalOutputEntry read(PacketByteBuf buf) {
            return new AdditionalOutputEntry(buf.readItemStack(), buf.readInt());
        }

        public void write(PacketByteBuf buf) {
            buf.writeItemStack(this.stack());
            buf.writeInt(this.luck());
        }
    }
}
