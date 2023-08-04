package com.mmodding.innovation_insights.blocks.engines;

import com.mmodding.innovation_insights.InnovationInsights;
import com.mmodding.innovation_insights.blockentities.engines.ExtractorEntity;
import com.mmodding.innovation_insights.init.IIRecipeTypes;
import com.mmodding.innovation_insights.recipes.Extraction;
import com.mmodding.mmodding_lib.library.blocks.CustomBlockWithEntity;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Properties;
import net.minecraft.util.*;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.random.RandomGenerator;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.stream.IntStream;

public class Extractor extends CustomBlockWithEntity implements BlockEntityProvider {

    public Extractor(Settings settings, boolean hasItem, ItemGroup itemGroup) {
        super(settings, hasItem, itemGroup);
        this.setDefaultState(this.getDefaultState().with(Properties.HORIZONTAL_FACING, Direction.NORTH));
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new ExtractorEntity(pos, state);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
		if (InnovationInsights.excludeBasics(player.getStackInHand(hand))) {
			if (!world.isClient()) {
				NamedScreenHandlerFactory screenHandlerFactory = state.createScreenHandlerFactory(world, pos);

				if (screenHandlerFactory != null) {
					player.openHandledScreen(screenHandlerFactory);
				}
			}
			return ActionResult.SUCCESS;
		}
		else {
			return ActionResult.PASS;
		}
    }

    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (state.getBlock() != newState.getBlock()) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof ExtractorEntity) {
                ItemScatterer.spawn(world, pos, (ExtractorEntity) blockEntity);
                world.updateComparators(pos, this);
            }
            super.onStateReplaced(state, world, pos, newState, moved);
        }
    }

    @Override
    public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
        super.onBlockAdded(state, world, pos, oldState, notify);
        world.scheduleBlockTick(new BlockPos(pos.getX(), pos.getY(), pos.getZ()), this, 1);
    }

	private ItemStack getOutputForSlot(Extraction extraction, int slotIndex) {
		return switch (slotIndex) {
			default -> extraction.getOutput();
			case 2 -> extraction.getOutputA();
			case 3 -> extraction.getOutputB();
			case 4 -> extraction.getOutputC();
			case 5 -> extraction.getOutputD();
			case 6 -> extraction.getOutputE();
		};
	}

	private int getOutputLuckForSlot(Extraction extraction, int slotIndex) {
		return switch (slotIndex) {
			default -> extraction.getOutputALuck();
			case 3 -> extraction.getOutputBLuck();
			case 4 -> extraction.getOutputCLuck();
			case 5 -> extraction.getOutputDLuck();
			case 6 -> extraction.getOutputELuck();
		};
	}

	private boolean isSlotValid(ExtractorEntity extractorEntity, Extraction extraction, int slotIndex) {
		ItemStack actualStack = extractorEntity.getStack(slotIndex);
		ItemStack outputStack = this.getOutputForSlot(extraction, slotIndex);

		if (actualStack.isItemEqual(outputStack) || actualStack.isEmpty()) {
			return actualStack.getCount() <= actualStack.getMaxCount() - outputStack.getCount();
		}
		else {
			return false;
		}
	}

	private boolean areSlotsValid(ExtractorEntity extractorEntity, Extraction extraction) {
		for (int slotIndex : IntStream.range(1, 7).toArray()) {
			if (!this.isSlotValid(extractorEntity, extraction, slotIndex)) {
				return false;
			}
		}
		return true;
	}

	private boolean isLuckValid(RandomGenerator random, Extraction extraction, int slotIndex) {
		return random.nextInt(100) < this.getOutputLuckForSlot(extraction, slotIndex);
	}

	private void createResult(RandomGenerator random, ExtractorEntity extractorEntity, Extraction extraction, int slotIndex) {
		ItemStack actualStack = extractorEntity.getStack(slotIndex);
		ItemStack outputStack = this.getOutputForSlot(extraction, slotIndex);

		ItemStack resultStack = new ItemStack(outputStack.getItem(), actualStack.getCount() + outputStack.getCount());

		if (slotIndex != 1) {
			if (this.isLuckValid(random, extraction, slotIndex)) {
				extractorEntity.setStack(slotIndex, resultStack);
			}
		}
		else {
			extractorEntity.setStack(slotIndex, resultStack);
		}
	}

	private void createResults(RandomGenerator random, ExtractorEntity extractorEntity, Extraction extraction) {
		extractorEntity.removeStack(0, 1);

		for (int slotIndex : IntStream.range(1, 7).toArray()) {
			this.createResult(random, extractorEntity, extraction, slotIndex);
		}
	}

	@Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, RandomGenerator random) {
        super.scheduledTick(state, world, pos, random);

        ExtractorEntity extractorEntity = (ExtractorEntity) world.getBlockEntity(pos);

        assert extractorEntity != null;

        NbtCompound nbt = new NbtCompound();
		extractorEntity.writeNbt(nbt);

        int extractionTime = nbt.getInt("extractionTime") + 1;
        nbt.putInt("extractionTime", extractionTime);
        extractorEntity.readNbt(nbt);

        Optional<Extraction> match = world.getRecipeManager().getFirstMatch(IIRecipeTypes.EXTRACTION, extractorEntity, world);

        if (match.isPresent()) {
            if (extractionTime >= match.get().getExtractionTime()) {
				if (this.areSlotsValid(extractorEntity, match.get())) {

					NbtCompound resetExtractionTimeNbt = new NbtCompound();
					extractorEntity.writeNbt(resetExtractionTimeNbt);

					resetExtractionTimeNbt.putInt("extractionTime", 0);
					extractorEntity.readNbt(resetExtractionTimeNbt);

					this.createResults(random, extractorEntity, match.get());
				}
            }
        }

        world.scheduleBlockTick(new BlockPos(pos.getX(), pos.getY(), pos.getZ()), this, 1);
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(Properties.HORIZONTAL_FACING, ctx.getPlayerFacing().getOpposite());
    }

    @Override
    public BlockState rotate(BlockState state, BlockRotation rotation) {
        return state.with(Properties.HORIZONTAL_FACING, rotation.rotate(state.get(Properties.HORIZONTAL_FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, BlockMirror mirror) {
        return state.rotate(mirror.getRotation(state.get(Properties.HORIZONTAL_FACING)));
    }

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> stateManager) {
		stateManager.add(Properties.HORIZONTAL_FACING);
	}

    @Override
    public boolean hasComparatorOutput(BlockState state) {
        return true;
    }

    @Override
    public int getComparatorOutput(BlockState state, World world, BlockPos pos) {
        return ScreenHandler.calculateComparatorOutput(world.getBlockEntity(pos));
    }
}
