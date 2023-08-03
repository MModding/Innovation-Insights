package com.mmodding.innovation_insights.blockentities.engines;

import com.mmodding.innovation_insights.init.IIBlockEntities;
import com.mmodding.innovation_insights.inventories.ImplementedInventory;
import com.mmodding.innovation_insights.screenhandlers.engines.ExtractorScreenHandler;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

public class ExtractorEntity extends BlockEntity implements NamedScreenHandlerFactory, ImplementedInventory {

    private int extractionTime;

    public ExtractorEntity(BlockPos pos, BlockState state) {
        super(IIBlockEntities.EXTRACTOR_ENTITY.getBlockEntityTypeIfCreated(), pos, state);
    }

    private DefaultedList<ItemStack> items = DefaultedList.ofSize(7, ItemStack.EMPTY);

    @Override
    public DefaultedList<ItemStack> getItems() {
        return items;
    }

    @Override
    public ItemStack removeStack(int slot) {
        return null;
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        this.items = DefaultedList.ofSize(this.size(), ItemStack.EMPTY);
        Inventories.readNbt(nbt, this.items);
        this.extractionTime = nbt.getInt("extractionTime");
    }

    @Override
    public void writeNbt(NbtCompound nbt) {
        nbt.putInt("extractionTime", this.extractionTime);
        Inventories.writeNbt(nbt, items);
        super.writeNbt(nbt);
    }

    @Override
    public Text getDisplayName() {
        return Text.of("Extractor");
    }

    public int getExtractionTime() {
        return this.extractionTime;
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {
        return new ExtractorScreenHandler(syncId, inv, this, 7);
    }
}
