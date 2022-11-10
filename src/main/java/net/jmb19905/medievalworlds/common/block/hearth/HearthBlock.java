package net.jmb19905.medievalworlds.common.block.hearth;

import net.jmb19905.medievalworlds.common.block.BlockHelper;
import net.jmb19905.medievalworlds.common.blockentities.HearthBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("deprecation")
public class HearthBlock extends Block implements EntityBlock {

    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final EnumProperty<HearthLitProperty> LIT = EnumProperty.create("lit", HearthLitProperty.class);
    public static final EnumProperty<HearthPartProperty> PART = EnumProperty.create("part", HearthPartProperty.class);

    public HearthBlock(Properties properties) {
        super(properties);
        registerDefaultState(getStateDefinition().any().setValue(FACING, Direction.NORTH).setValue(LIT, HearthLitProperty.OFF).setValue(PART, HearthPartProperty.MAIN));
    }

    private void handleRemove(@NotNull Level level, @NotNull BlockPos pos, @NotNull BlockState state) {
        if (!level.isClientSide) {
            HearthPartProperty part = state.getValue(PART);
            Direction facing = state.getValue(FACING);
            BlockPos mainPos = part.getMainPosition(pos, facing);
            HearthShape shape = HearthShape.createPhantomHearthShape(level, mainPos, facing);
            if (!shape.isConstructed()) shape.deconstruct();
        }
    }

    @Override
    public void playerWillDestroy(@NotNull Level level, @NotNull BlockPos pos, @NotNull BlockState state, @NotNull Player player) {
        handleRemove(level, pos, state);
    }

    @Override
    public void onBlockExploded(BlockState state, Level level, BlockPos pos, Explosion explosion) {
        handleRemove(level, pos, state);
        super.onBlockExploded(state, level, pos, explosion);
    }

    @Override
    public void onRemove(@NotNull BlockState state, Level level, @NotNull BlockPos pos, @NotNull BlockState newState, boolean b) {
        BlockEntity entity = level.getBlockEntity(pos);
        if(entity instanceof HearthBlockEntity blockEntity && state.getBlock() != newState.getBlock()){
            if (!newState.hasProperty(PART) || newState.getValue(PART) != HearthPartProperty.MAIN) {
                BlockHelper.dropBlockEntityContents(level, pos, blockEntity);
            }
        }

        if(state.hasBlockEntity() && state.getBlock() != newState.getBlock()) {
            if (!newState.hasProperty(PART) || newState.getValue(PART) != HearthPartProperty.MAIN) {
                level.removeBlockEntity(pos);
            }
        }
    }

    @Override
    public @NotNull PushReaction getPistonPushReaction(@NotNull BlockState state) {
        return PushReaction.BLOCK;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.@NotNull Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(FACING, LIT, PART);
    }

    @Override
    public void animateTick(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull RandomSource random) {
        if (state.getValue(PART) == HearthPartProperty.MAIN) {
            if (random.nextDouble() < 0.1D) {
                level.playLocalSound(pos.getX() + .5, pos.getY(), pos.getZ() + .5, SoundEvents.FURNACE_FIRE_CRACKLE, SoundSource.BLOCKS, 1.0F, 1.0F, false);
            }
            if (level.getBlockEntity(pos) instanceof HearthBlockEntity entity) {
                for (int i=0;i<entity.getCoalFillLevelInt();i++) {
                    spawnFlameParticle(level, pos, random);
                }
            }
        } else if (state.getValue(PART) == HearthPartProperty.TOP_MIDDLE) {
            spawnSmokeParticle(level, pos, random);
        }
        super.animateTick(state, level, pos, random);
    }

    private void spawnSmokeParticle(Level level, BlockPos pos, RandomSource random) {
        double dx1 = random.nextDouble() / 4.0D * (double)(random.nextBoolean() ? 1 : -1);
        double dz1 = random.nextDouble() / 4.0D * (double)(random.nextBoolean() ? 1 : -1);

        level.addAlwaysVisibleParticle(ParticleTypes.CAMPFIRE_COSY_SMOKE, true, pos.getX() + .5 + dx1, pos.getY() + 0.5, pos.getZ() + .5 + dz1, 0.0D, 0.07D, 0.0D);
    }

    private void spawnFlameParticle(Level level, BlockPos pos, RandomSource random) {
        double dx2 = random.nextDouble() / 2 + .25;
        double dz2 = random.nextDouble() / 2 + .25;

        level.addParticle(ParticleTypes.SMALL_FLAME, pos.getX() + dx2, pos.getY() + 0.85f, pos.getZ() + dz2, 0.0001,0.0001,0.0001);
    }

    @Override
    public @NotNull InteractionResult use(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull Player player, @NotNull InteractionHand hand, @NotNull BlockHitResult result) {
        if (state.getValue(PART) != HearthPartProperty.MAIN) return InteractionResult.PASS;
        if (level.isClientSide) {
            return InteractionResult.SUCCESS;
        } else {
            BlockEntity entity = level.getBlockEntity(pos);
            if (entity instanceof HearthBlockEntity menuEntity && menuEntity.isReadyForUse()) {
                NetworkHooks.openScreen((ServerPlayer) player, menuEntity, pos);
            }
            return InteractionResult.CONSUME;
        }
    }

    @Override
    public @NotNull RenderShape getRenderShape(@NotNull BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    public @NotNull VoxelShape getShape(@NotNull BlockState state, @NotNull BlockGetter blockGetter, @NotNull BlockPos pos, @NotNull CollisionContext ctx) {
        return state.getValue(PART).getShape(state.getValue(FACING));
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
        return new HearthBlockEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NotNull Level level, @NotNull BlockState state, @NotNull BlockEntityType<T> type) {
        return !level.isClientSide && state.getValue(PART) == HearthPartProperty.MAIN ? HearthBlockEntity::tick : null;
    }
}