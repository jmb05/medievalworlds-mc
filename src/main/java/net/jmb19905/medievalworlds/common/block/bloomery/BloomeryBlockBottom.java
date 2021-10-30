package net.jmb19905.medievalworlds.common.block.bloomery;

import net.jmb19905.medievalworlds.common.blockentities.bloomery.BloomeryBottomBlockEntity;
import net.jmb19905.medievalworlds.core.registries.MWBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Random;

@SuppressWarnings("deprecation")
public class BloomeryBlockBottom extends AbstractBloomeryBlock{

    private static final VoxelShape BOTTOM_SHAPE = Block.box(0, 0, 0, 16, 16, 16);

    public BloomeryBlockBottom(Properties properties) {
        super(properties);
    }

    @Override
    public int getLightEmission(BlockState state, BlockGetter world, BlockPos pos) {
        return state.getValue(LIT) ? 13 : 0;
    }

    @Nonnull
    @Override
    public VoxelShape getShape(@Nonnull BlockState p_60555_, @Nonnull BlockGetter p_60556_, @Nonnull BlockPos p_60557_, @Nonnull CollisionContext p_60558_) {
        return BOTTOM_SHAPE;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(@Nonnull BlockPos pos, @Nonnull BlockState state) {
        return MWBlockEntityTypes.BLOOMERY_BOTTOM.get().create(pos, state);
    }

    @Nonnull
    @Override
    public InteractionResult use(@Nonnull BlockState state, Level level, @Nonnull BlockPos pos, @Nonnull Player player, @Nonnull InteractionHand hand, @Nonnull BlockHitResult hit) {
        if(!level.isClientSide) {
            if(state.getValue(CLAY)) {
                BlockEntity blockEntity = level.getBlockEntity(pos);
                if (blockEntity instanceof BloomeryBottomBlockEntity entity) {
                    ItemStack playerHandItem = player.getItemInHand(hand);
                    if (playerHandItem.getItem() == Items.FLINT_AND_STEEL) {
                        entity.startHardening();
                        playerHandItem.hurtAndBreak(1, player, (living) -> living.broadcastBreakEvent(hand));
                        return InteractionResult.SUCCESS;
                    }
                }
            }
            return super.use(state, level, pos, player, hand, hit);
        }
        return InteractionResult.SUCCESS;
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, @Nonnull BlockState state, @Nonnull BlockEntityType<T> blockType) {
        return level.isClientSide ? null : blockType == MWBlockEntityTypes.BLOOMERY_BOTTOM.get() ? BloomeryBottomBlockEntity::tick : null;
    }

    @Override
    public void animateTick(@Nonnull BlockState state, @Nonnull Level level, @Nonnull BlockPos pos, @Nonnull Random random) {
        if(state.getValue(LIT)) {
            if (random.nextDouble() < 0.1D) {
                level.playLocalSound(pos.getX() + .5, pos.getY(), pos.getZ() + .5, SoundEvents.FURNACE_FIRE_CRACKLE, SoundSource.BLOCKS, 1.0F, 1.0F, false);
            }

            double dx = random.nextDouble() * .75 - .25;
            double dz = random.nextDouble() * .75 - .25;

            level.addParticle(ParticleTypes.SMOKE, pos.getX() + .5 + dx, pos.getY() + 1, pos.getZ() + .5 + dz, 0,1,0);
        }
    }
}
