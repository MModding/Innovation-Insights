package com.mmodding.innovation_insights.blocks.generators;

import com.mmodding.innovation_insights.blockentities.generators.AnvilFissionGeneratorEntity;
import com.mmodding.innovation_insights.init.IIBlockEntities;
import com.mmodding.mmodding_lib.library.blocks.CustomBlockWithEntity;
import com.mmodding.mmodding_lib.library.blocks.interactions.FallingBlockInteraction;
import com.mmodding.mmodding_lib.library.blocks.interactions.data.FallingBlockInteractionData;
import net.minecraft.block.AnvilBlock;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemGroup;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.random.RandomGenerator;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import team.reborn.energy.api.base.SimpleEnergyStorage;

public class AnvilFissionGenerator extends CustomBlockWithEntity implements FallingBlockInteraction {

    public AnvilFissionGenerator(Settings settings, boolean hasItem, ItemGroup itemGroup) {
        super(settings, hasItem, itemGroup);
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new AnvilFissionGeneratorEntity(pos, state);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (!world.isClient()) {
            NamedScreenHandlerFactory screenHandlerFactory = state.createScreenHandlerFactory(world, pos);

            if (screenHandlerFactory != null) {
                player.openHandledScreen(screenHandlerFactory);
            }
        }
        return ActionResult.SUCCESS;
    }

    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (state.getBlock() != newState.getBlock()) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof AnvilFissionGeneratorEntity) {
                ItemScatterer.spawn(world, pos, (AnvilFissionGeneratorEntity) blockEntity);
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

        world.scheduleBlockTick(new BlockPos(pos.getX(), pos.getY(), pos.getZ()), this, 1);
    }

    @Override
    public boolean hasComparatorOutput(BlockState state) {
        return true;
    }

    @Override
    public int getComparatorOutput(BlockState state, World world, BlockPos pos) {
        return ScreenHandler.calculateComparatorOutput(world.getBlockEntity(pos));
    }

	@Override
	public void onFallingBlockInteract(FallingBlockInteractionData data) {
		if (data.getCurrentBlockState().getBlock() instanceof AnvilBlock) {
			data.getWorld().getBlockEntity(
				data.getInteractPos(),
				IIBlockEntities.ANVIL_FISSION_GENERATOR_ENTITY.getBlockEntityTypeIfCreated()
			).ifPresent(anvilFissionGeneratorEntity -> anvilFissionGeneratorEntity.addIEF((long) data.getFallHurtAmount() * 1000L));
		}
	}
}
