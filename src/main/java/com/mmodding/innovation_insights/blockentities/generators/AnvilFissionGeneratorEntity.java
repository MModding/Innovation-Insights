package com.mmodding.innovation_insights.blockentities.generators;

import com.mmodding.innovation_insights.InnovationEnergyFlux;
import com.mmodding.innovation_insights.init.IIBlockEntities;
import com.mmodding.innovation_insights.init.IIItems;
import com.mmodding.innovation_insights.init.IITags;
import com.mmodding.innovation_insights.inventories.ImplementedInventory;
import com.mmodding.innovation_insights.screenhandlers.generators.AnvilFissionGeneratorScreenHandler;
import com.mmodding.mmodding_lib.library.blocks.interactions.data.FallingBlockInteractionData;
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
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import org.jetbrains.annotations.Nullable;
import team.reborn.energy.api.base.SimpleEnergyStorage;

public class AnvilFissionGeneratorEntity extends BlockEntity implements InnovationEnergyFlux.Container, NamedScreenHandlerFactory, ImplementedInventory {

	private final SimpleEnergyStorage energyStorage = new SimpleEnergyStorage(100000L, 100000L, 100000L);

    public AnvilFissionGeneratorEntity(BlockPos pos, BlockState state) {
        super(IIBlockEntities.ANVIL_FISSION_GENERATOR_ENTITY.getBlockEntityTypeIfCreated(), pos, state);
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

    public void tick() {
        ItemStack stack = this.items.get(0);
        if (stack.isIn(IITags.BATTERIES)) {
            this.transferTo(stack, this, 0, 1000);
        }
		else {
			InnovationEnergyFlux.findStorageForDirections(
                this.getWorld(), this.getPos(), Direction.Type.HORIZONTAL, storage -> InnovationEnergyFlux.transfer(this.getEnergyStorage(), storage, 1000)
            );
		}
    }

	public void triggerFission(FallingBlockInteractionData data) {
		ItemStack debris = this.items.get(1);
		ItemStack fragments = this.items.get(2);

		if (debris.getCount() < debris.getMaxCount() && fragments.getCount() < fragments.getMaxCount()) {
			this.addIEF((long) data.getFallHurtAmount() * 1000L);

			if (data.isDestroyedOnLanding()) {
				int count = MathHelper.clamp(0, fragments.getCount() + 1, fragments.getMaxCount());
				this.items.set(2, new ItemStack(IIItems.ANVIL_FRAGMENTS, count));
			}
			else if (!data.getCurrentBlockState().isOf(data.getInitialBlockState().getBlock())) {
				int count = MathHelper.clamp(0, debris.getCount() + 1, debris.getMaxCount());
				this.items.set(1, new ItemStack(IIItems.ANVIL_DEBRIS, count));
			}
		}
	}

    @Override
    public void readNbt(NbtCompound nbt) {
		this.readIEF(nbt);
        this.items = DefaultedList.ofSize(this.size(), ItemStack.EMPTY);
        Inventories.readNbt(nbt, this.items);
    }

    @Override
    public void writeNbt(NbtCompound nbt) {
		this.writeIEF(nbt);
		Inventories.writeNbt(nbt, this.items);
		super.writeNbt(nbt);
    }

    @Override
    public Text getDisplayName() {
        return Text.of("Anvil Fission Generator");
    }

	public SimpleEnergyStorage getEnergyStorage() {
		return this.energyStorage;
	}

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {
        return new AnvilFissionGeneratorScreenHandler(syncId, inv, this, 3);
    }
}
