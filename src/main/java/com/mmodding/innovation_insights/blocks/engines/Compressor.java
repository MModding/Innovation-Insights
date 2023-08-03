package com.mmodding.innovation_insights.blocks.engines;

import com.mmodding.innovation_insights.InnovationInsights;
import com.mmodding.innovation_insights.blockentities.engines.CompressorEntity;
import com.mmodding.innovation_insights.init.IIRecipeTypes;
import com.mmodding.innovation_insights.recipes.Compression;
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
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
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

public class Compressor extends CustomBlockWithEntity implements BlockEntityProvider {

    public static final DirectionProperty FACING;

    public Compressor(Settings settings, boolean hasItem, ItemGroup itemGroup) {
        super(settings, hasItem, itemGroup);
        setDefaultState(this.getStateManager().getDefaultState().with(Properties.HORIZONTAL_FACING, Direction.NORTH));
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new CompressorEntity(pos, state);
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
            if (blockEntity instanceof CompressorEntity) {
                ItemScatterer.spawn(world, pos, (CompressorEntity) blockEntity);
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

        CompressorEntity compressorEntity = (CompressorEntity) world.getBlockEntity(pos);

        assert compressorEntity != null;

        NbtCompound nbt = new NbtCompound();
		compressorEntity.writeNbt(nbt);

        int compressionTime = nbt.getInt("compressionTime") + 1;
        nbt.putInt("compressionTime", compressionTime);
        compressorEntity.readNbt(nbt);

        Optional<Compression> match = world.getRecipeManager().getFirstMatch(IIRecipeTypes.COMPRESSION, compressorEntity, world);

        if (match.isPresent() && compressionTime >= match.get().getCompressionTime()) {
            if (compressorEntity.getStack(0).getCount() < 64) {

                NbtCompound resetCompressionTimeNbt = new NbtCompound();
				compressorEntity.writeNbt(resetCompressionTimeNbt);

				resetCompressionTimeNbt.putInt("compressionTime", 0);
                compressorEntity.readNbt(resetCompressionTimeNbt);

                compressorEntity.removeStack(1, 1);
                compressorEntity.removeStack(2, 1);

                compressorEntity.setStack(0, new ItemStack(match.get().getOutput().copy().getItem(), compressorEntity.getStack(0).getCount() + 1));

                world.playSound(null, pos, SoundEvents.BLOCK_ANVIL_DESTROY, SoundCategory.BLOCKS, 1.0f, 1.0f);
            }
        }

        world.scheduleBlockTick(new BlockPos(pos.getX(), pos.getY(), pos.getZ()), this, 1);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> stateManager) {
        stateManager.add(Properties.HORIZONTAL_FACING);
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

    @Override
    public boolean hasComparatorOutput(BlockState state) {
        return true;
    }

    @Override
    public int getComparatorOutput(BlockState state, World world, BlockPos pos) {
        return ScreenHandler.calculateComparatorOutput(world.getBlockEntity(pos));
    }

    static {
        FACING = Properties.HORIZONTAL_FACING;
    }
}
