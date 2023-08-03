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
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.*;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.random.RandomGenerator;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.Random;

public class Extractor extends CustomBlockWithEntity implements BlockEntityProvider {

    public static final DirectionProperty FACING;

    public Extractor(Settings settings, boolean hasItem, ItemGroup itemGroup) {
        super(settings, hasItem, itemGroup);
        setDefaultState(this.getStateManager().getDefaultState().with(Properties.HORIZONTAL_FACING, Direction.NORTH));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> stateManager) {
        stateManager.add(Properties.HORIZONTAL_FACING);
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
                if ((extractorEntity.getStack(1).getItem() == match.get().getOutput().getItem() || extractorEntity.getStack(1) == ItemStack.EMPTY) && (extractorEntity.getStack(1).getCount() <= 64 - match.get().getOutput().getCount())) {
                    if ((extractorEntity.getStack(2).getItem() == match.get().getOutputA().getItem() || extractorEntity.getStack(2) == ItemStack.EMPTY) && (extractorEntity.getStack(2).getCount() <= 64 - match.get().getOutputA().getCount())) {
                        if ((extractorEntity.getStack(3).getItem() == match.get().getOutputB().getItem() || extractorEntity.getStack(3) == ItemStack.EMPTY) && (extractorEntity.getStack(3).getCount() <= 64 - match.get().getOutputB().getCount())) {
                            if ((extractorEntity.getStack(4).getItem() == match.get().getOutputC().getItem() || extractorEntity.getStack(4) == ItemStack.EMPTY) && (extractorEntity.getStack(4).getCount() <= 64 - match.get().getOutputC().getCount())) {
                                if ((extractorEntity.getStack(5).getItem() == match.get().getOutputD().getItem() || extractorEntity.getStack(5) == ItemStack.EMPTY) && (extractorEntity.getStack(5).getCount() <= 64 - match.get().getOutputD().getCount())) {
                                    if ((extractorEntity.getStack(6).getItem() == match.get().getOutputE().getItem() || extractorEntity.getStack(6) == ItemStack.EMPTY) && (extractorEntity.getStack(6).getCount() <= 64 - match.get().getOutputE().getCount())) {

                                        NbtCompound resetExtractionTimeNbt = new NbtCompound();
										extractorEntity.writeNbt(resetExtractionTimeNbt);

										resetExtractionTimeNbt.putInt("extractionTime", 0);
                                        extractorEntity.readNbt(resetExtractionTimeNbt);

                                        extractorEntity.removeStack(0, 1);

                                        extractorEntity.setStack(1, new ItemStack(match.get().getOutput().getItem(), extractorEntity.getStack(1).getCount() + match.get().getOutput().getCount()));

                                        if (new Random().nextInt(100) + 1 < match.get().getOutputALuck()) {
                                            extractorEntity.setStack(2, new ItemStack(match.get().getOutputA().getItem(), extractorEntity.getStack(2).getCount() + match.get().getOutputA().getCount()));
                                        }

                                        if (new Random().nextInt(100) + 1 < match.get().getOutputBLuck()) {
                                            extractorEntity.setStack(3, new ItemStack(match.get().getOutputB().getItem(), extractorEntity.getStack(3).getCount() + match.get().getOutputB().getCount()));
                                        }

                                        if (new Random().nextInt(100) + 1 < match.get().getOutputCLuck()) {
                                            extractorEntity.setStack(4, new ItemStack(match.get().getOutputC().getItem(), extractorEntity.getStack(4).getCount() + match.get().getOutputC().getCount()));
                                        }

                                        if (new Random().nextInt(100) + 1 < match.get().getOutputDLuck()) {
                                            extractorEntity.setStack(5, new ItemStack(match.get().getOutputD().getItem(), extractorEntity.getStack(5).getCount() + match.get().getOutputD().getCount()));
                                        }

                                        if (new Random().nextInt(100) + 1 < match.get().getOutputELuck()) {
                                            extractorEntity.setStack(6, new ItemStack(match.get().getOutputE().getItem(), extractorEntity.getStack(6).getCount() + match.get().getOutputE().getCount()));
                                        }

                                    }
                                }
                            }
                        }
                    }
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
        return state.with(FACING, rotation.rotate(state.get(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, BlockMirror mirror) {
        return state.rotate(mirror.getRotation(state.get(FACING)));
    }

    static {
        FACING = Properties.HORIZONTAL_FACING;
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
