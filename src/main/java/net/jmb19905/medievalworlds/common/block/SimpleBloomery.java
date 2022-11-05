package net.jmb19905.medievalworlds.common.block;

import net.jmb19905.medievalworlds.common.blockentities.SimpleBloomeryBlockEntity;
import net.jmb19905.medievalworlds.common.registries.MWBlockEntityTypes;
import net.jmb19905.medievalworlds.common.inv.MWItemHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.FlintAndSteelItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@SuppressWarnings("deprecation")
public class SimpleBloomery extends Block implements EntityBlock {

    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final BooleanProperty CLAY = BooleanProperty.create("clay");
    public static final EnumProperty<BloomeryActivationProperty> LIT = EnumProperty.create("lit", BloomeryActivationProperty.class);
    public static final EnumProperty<DoubleBlockHalf> HALF = BlockStateProperties.DOUBLE_BLOCK_HALF;

    private static final VoxelShape TOP_SHAPE = Block.box(1, 0, 1, 15, 14, 15);
    private static final VoxelShape BOTTOM_SHAPE = Block.box(0, 0, 0, 16, 16, 16);

    public SimpleBloomery(Properties properties) {
        super(properties);
        registerDefaultState(getStateDefinition().any().setValue(FACING, Direction.NORTH).setValue(CLAY, true).setValue(LIT, BloomeryActivationProperty.OFF).setValue(HALF, DoubleBlockHalf.LOWER));
    }

    public void playerWillDestroy(Level level, @NotNull BlockPos pos, @NotNull BlockState state, @NotNull Player player) {
        if (!level.isClientSide && player.isCreative()) {
            DoubleBlockHalf doubleblockhalf = state.getValue(HALF);
            if (doubleblockhalf == DoubleBlockHalf.UPPER) {
                BlockPos blockpos = pos.below();
                BlockState blockstate = level.getBlockState(blockpos);
                if (blockstate.is(state.getBlock()) && blockstate.getValue(HALF) == DoubleBlockHalf.LOWER) {
                    level.setBlock(blockpos, Blocks.AIR.defaultBlockState(), 35);
                    level.levelEvent(player, 2001, blockpos, Block.getId(blockstate));
                }
            }
        }
        super.playerWillDestroy(level, pos, state, player);
    }

    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        BlockPos blockpos = pos.below();
        BlockState blockstate = level.getBlockState(blockpos);
        return state.getValue(HALF) == DoubleBlockHalf.LOWER ? blockstate.isFaceSturdy(level, blockpos, Direction.UP) : blockstate.is(this);
    }

    @Nonnull
    @Override
    public BlockState updateShape(@NotNull BlockState state, @NotNull Direction direction, @NotNull BlockState state2, @NotNull LevelAccessor level, @NotNull BlockPos pos1, @NotNull BlockPos pos2) {
        DoubleBlockHalf doubleblockhalf = state.getValue(HALF);
        if (direction.getAxis() != Direction.Axis.Y || doubleblockhalf == DoubleBlockHalf.LOWER != (direction == Direction.UP) || state2.is(this) && state2.getValue(HALF) != doubleblockhalf) {
            return doubleblockhalf == DoubleBlockHalf.LOWER && direction == Direction.DOWN && !state.canSurvive(level, pos1) ? Blocks.AIR.defaultBlockState() : super.updateShape(state, direction, state2, level, pos1, pos2);
        } else {
            return Blocks.AIR.defaultBlockState();
        }
    }

    @NotNull
    public PushReaction getPistonPushReaction(@NotNull BlockState state) {
        return PushReaction.DESTROY;
    }

    @Override
    public int getLightEmission(BlockState state, BlockGetter world, BlockPos pos) {
        if(state.getValue(HALF) == DoubleBlockHalf.LOWER) {
            if(state.getValue(LIT) == BloomeryActivationProperty.ACTIVATED) return 13;
            return state.getValue(LIT) == BloomeryActivationProperty.LIT ? 7 : 0;
        }
        return 0;
    }

    @NotNull
    @Override
    public VoxelShape getShape(@NotNull BlockState state, @NotNull BlockGetter blockGetter, @NotNull BlockPos pos, @NotNull CollisionContext ctx) {
        if(state.getValue(HALF) == DoubleBlockHalf.LOWER) return BOTTOM_SHAPE;
        else return TOP_SHAPE;
    }

    @Override
    protected void createBlockStateDefinition(@Nonnull StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(FACING, CLAY, LIT, HALF);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        return this.defaultBlockState().setValue(FACING, ctx.getHorizontalDirection().getOpposite());
    }

    @Override
    public void setPlacedBy(@Nonnull Level level, @Nonnull BlockPos pos, @Nonnull BlockState state, @Nullable LivingEntity placer, @Nonnull ItemStack stack) {
        super.setPlacedBy(level, pos, state, placer, stack);
        level.setBlock(pos.above(), state.setValue(HALF, DoubleBlockHalf.UPPER), 3);
    }

    @Nonnull
    @Override
    public InteractionResult use(@Nonnull BlockState state, Level level, @Nonnull BlockPos pos, @Nonnull Player player, @Nonnull InteractionHand hand, @Nonnull BlockHitResult hit) {
        if(!level.isClientSide) {
            if(state.getValue(HALF) == DoubleBlockHalf.LOWER) {
                BlockEntity blockEntity = level.getBlockEntity(pos);
                if (blockEntity instanceof SimpleBloomeryBlockEntity entity) {
                    ItemStack playerStack = player.getItemInHand(hand);
                    ItemStack fuelStack = entity.getInventory().getStackInSlot(1);
                    ItemStack inputStack = entity.getInventory().getStackInSlot(0);
                    if (playerStack.getItem() == Items.CHARCOAL) {
                        if(fuelStack.isEmpty()) {
                            entity.getInventory().setStackInSlot(1, playerStack.copy());
                            player.setItemInHand(hand, new ItemStack(Items.AIR));
                            return InteractionResult.SUCCESS;
                        }else if(fuelStack.getItem() == Items.CHARCOAL) {
                            int maxAddCount = 64 - fuelStack.getCount();
                            int addCount = playerStack.getCount();
                            addCount = Math.min(maxAddCount, addCount);
                            if(addCount > 0) {
                                fuelStack.grow(addCount);
                                playerStack.shrink(addCount);
                                return InteractionResult.SUCCESS;
                            }
                        }
                    } else if (playerStack.getItem() instanceof FlintAndSteelItem && (state.getValue(CLAY) || isItemStackBloomable(level, inputStack))) {
                        if(fuelStack.getCount() * 8 < inputStack.getCount()) {
                            player.displayClientMessage(Component.literal("Not enough charcoal"), true);
                            return InteractionResult.SUCCESS;
                        }
                        entity.start();
                        playerStack.hurtAndBreak(1, player, (living) -> living.broadcastBreakEvent(hand));
                        level.playSound(null, pos, SoundEvents.FLINTANDSTEEL_USE, SoundSource.PLAYERS, 1.0F, level.getRandom().nextFloat() * 0.4F + 0.8F);
                        return InteractionResult.SUCCESS;
                    } else if(!state.getValue(CLAY) && isItemStackBloomable(level, playerStack)){
                        if(inputStack.isEmpty()) {
                            entity.getInventory().setStackInSlot(0, playerStack.copy());
                            player.setItemInHand(hand, new ItemStack(Items.AIR));
                            return InteractionResult.SUCCESS;
                        }else if(inputStack.getItem() == playerStack.getItem()){
                            int maxAddCount = 64 - inputStack.getCount();
                            int addCount = playerStack.getCount();
                            addCount = Math.min(maxAddCount, addCount);
                            if(addCount > 0) {
                                inputStack.grow(addCount);
                                playerStack.shrink(addCount);
                                return InteractionResult.SUCCESS;
                            }
                        }
                    }
                }
            }
        }
        return InteractionResult.SUCCESS;
    }

    private boolean isItemStackBloomable(Level level, ItemStack itemStack) {
        if(itemStack.isEmpty()) return false;
        return SimpleBloomeryBlockEntity.getRecipe(level, itemStack) != null;
    }

    @Override
    public void animateTick(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull RandomSource random) {
        double dx1 = random.nextDouble() / 4.0D * (double)(random.nextBoolean() ? 1 : -1);
        double dz1 = random.nextDouble() / 4.0D * (double)(random.nextBoolean() ? 1 : -1);
        if(state.getValue(LIT) == BloomeryActivationProperty.LIT) {
            if (random.nextDouble() < 0.1D) {
                level.playLocalSound(pos.getX() + .5, pos.getY(), pos.getZ() + .5, SoundEvents.FURNACE_FIRE_CRACKLE, SoundSource.BLOCKS, 1.0F, 1.0F, false);
            }

            double dx2 = random.nextDouble() / 2 + .25;
            double dz2 = random.nextDouble() / 2 + .25;

            level.addAlwaysVisibleParticle(ParticleTypes.CAMPFIRE_COSY_SMOKE, true, pos.getX() + .5 + dx1, pos.getY() + 1, pos.getZ() + .5 + dz1, 0.0D, 0.07D, 0.0D);
            level.addParticle(ParticleTypes.SMOKE, pos.getX() + dx2, pos.getY() + 1, pos.getZ() + dz2, 0,0.005,0);

        } else if(state.getValue(LIT) == BloomeryActivationProperty.ACTIVATED) {
            if (random.nextDouble() < 0.1D) {
                level.playLocalSound(pos.getX() + .5, pos.getY(), pos.getZ() + .5, SoundEvents.FURNACE_FIRE_CRACKLE, SoundSource.BLOCKS, 3.0F, 1.0F, false);
            }

            double dx2 = random.nextDouble() / 2 + .25;
            double dz2 = random.nextDouble() / 2 + .25;

            level.addAlwaysVisibleParticle(ParticleTypes.CAMPFIRE_COSY_SMOKE, true, pos.getX() + .5 + dx1, pos.getY() + 1, pos.getZ() + .5 + dz1, 0.0D, 0.07D, 0.0D);
            dx1 = random.nextDouble() / 4.0D * (double)(random.nextBoolean() ? 1 : -1);
            dz1 = random.nextDouble() / 4.0D * (double)(random.nextBoolean() ? 1 : -1);
            level.addAlwaysVisibleParticle(ParticleTypes.CAMPFIRE_COSY_SMOKE, true, pos.getX() + .5 + dx1, pos.getY() + 1, pos.getZ() + .5 + dz1, 0.0D, 0.08D, 0.0D);
            dx1 = random.nextDouble() / 4.0D * (double)(random.nextBoolean() ? 1 : -1);
            dz1 = random.nextDouble() / 4.0D * (double)(random.nextBoolean() ? 1 : -1);
            level.addAlwaysVisibleParticle(ParticleTypes.CAMPFIRE_COSY_SMOKE, true, pos.getX() + .5 + dx1, pos.getY() + 1, pos.getZ() + .5 + dz1, 0.0D, 0.06D, 0.0D);
            level.addParticle(ParticleTypes.SMOKE, pos.getX() + dx2, pos.getY() + 1, pos.getZ() + dz2, 0,0.005,0);

        }
    }

    @org.jetbrains.annotations.Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NotNull Level level, @NotNull BlockState state, @NotNull BlockEntityType<T> entityType) {
        return level.isClientSide ? null : entityType == MWBlockEntityTypes.SIMPLE_BLOOMERY.get() ? SimpleBloomeryBlockEntity::serverTick : null;
    }

    @Override
    public void onRemove(@Nonnull BlockState state, Level level, @Nonnull BlockPos pos, @Nonnull BlockState newState, boolean isMoving) {
        BlockEntity entity = level.getBlockEntity(pos);
        if(entity instanceof SimpleBloomeryBlockEntity simpleBloomeryBlockEntity && state.getBlock() != newState.getBlock()) {
            ((MWItemHandler) simpleBloomeryBlockEntity.getInventory()).toNonNullList().forEach(stack -> {
                ItemEntity itemEntity = new ItemEntity(level, pos.getX(), pos.getY(), pos.getZ(), stack);
                level.addFreshEntity(itemEntity);
            });
        }

        if(state.hasBlockEntity() && state.getBlock() != newState.getBlock()) {
            level.removeBlockEntity(pos);
        }
    }

    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
        return MWBlockEntityTypes.SIMPLE_BLOOMERY.get().create(pos, state);
    }

}