package com.mmodding.innovation_insights.blockentities.engines;

import com.mmodding.innovation_insights.init.IIBlockEntities;
import com.mmodding.innovation_insights.inventories.ImplementedInventory;
import com.mmodding.innovation_insights.screenhandlers.engines.CompressorScreenHandler;
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

public class CompressorEntity extends BlockEntity implements NamedScreenHandlerFactory, ImplementedInventory {

    private int compressionTime;

    public CompressorEntity(BlockPos pos, BlockState state) {
        super(IIBlockEntities.COMPRESSOR_ENTITY.getBlockEntityTypeIfCreated(), pos, state);
    }

    private DefaultedList<ItemStack> items = DefaultedList.ofSize(3, ItemStack.EMPTY);

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
        this.compressionTime = nbt.getInt("compressionTime");
    }

    @Override
    public void writeNbt(NbtCompound nbt) {
        nbt.putInt("compressionTime", this.compressionTime);
        Inventories.writeNbt(nbt, this.items);
        super.writeNbt(nbt);
    }

    @Override
    public Text getDisplayName() {
        return Text.of("Compressor");
    }

    public int getCompressionTime() {
        return compressionTime;
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {
        return new CompressorScreenHandler(syncId, inv, this, 3);
    }
}
