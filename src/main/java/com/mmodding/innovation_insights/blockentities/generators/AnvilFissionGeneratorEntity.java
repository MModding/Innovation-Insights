package com.mmodding.innovation_insights.blockentities.generators;

import com.mmodding.innovation_insights.init.IIBlockEntities;
import com.mmodding.innovation_insights.inventories.ImplementedInventory;
import com.mmodding.innovation_insights.screenhandlers.generators.AnvilFissionGeneratorScreenHandler;
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

public class AnvilFissionGeneratorEntity extends BlockEntity implements NamedScreenHandlerFactory, ImplementedInventory {

    private int breakTime;

    public AnvilFissionGeneratorEntity(BlockPos pos, BlockState state) {
        super(IIBlockEntities.ANVIL_FISSION_GENERATOR_ENTITY.getBlockEntityTypeIfCreated(), pos, state);
    }

    private DefaultedList<ItemStack> items = DefaultedList.ofSize(1, ItemStack.EMPTY);

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
        this.breakTime = nbt.getInt("breakTime");
    }

    @Override
    public void writeNbt(NbtCompound nbt) {
        nbt.putInt("breakTime", this.breakTime);
        Inventories.writeNbt(nbt, this.items);
        super.writeNbt(nbt);
    }

    @Override
    public Text getDisplayName() {
        return Text.of("Anvil Fission Generator");
    }

    public int getBreakTime() {
        return breakTime;
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {
        return new AnvilFissionGeneratorScreenHandler(syncId, inv, this);
    }
}
