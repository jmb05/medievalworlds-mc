package net.jmb19905.medievalworlds.common.block;

import net.jmb19905.medievalworlds.common.blockentities.abstr.MWNamedInventoryBlockEntity;
import net.jmb19905.medievalworlds.common.inv.MWItemHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;

public class MWCraftingBlock<T extends MWNamedInventoryBlockEntity> extends Block implements EntityBlock {

    private final RegistryObject<BlockEntityType<T>> blockEntityType;

    public MWCraftingBlock(RegistryObject<BlockEntityType<T>> blockEntityType, Properties properties) {
        super(properties);
        this.blockEntityType = blockEntityType;
    }

    @SuppressWarnings("deprecation")
    @NotNull
    public InteractionResult use(@NotNull BlockState state, Level level, @NotNull BlockPos pos, @NotNull Player player, @NotNull InteractionHand hand, @NotNull BlockHitResult result) {
        if (level.isClientSide) {
            return InteractionResult.SUCCESS;
        } else {
            BlockEntity entity = level.getBlockEntity(pos);
            if (entity instanceof MWNamedInventoryBlockEntity menuEntity) {
                NetworkHooks.openScreen((ServerPlayer) player, menuEntity, pos);
                awardUseStat(player);
            }
            return InteractionResult.CONSUME;
        }
    }

    protected void awardUseStat(Player player) {}

    @Nullable
    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
        return blockEntityType.get().create(pos, state);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onRemove(@Nonnull BlockState state, Level level, @Nonnull BlockPos pos, @Nonnull BlockState newState, boolean isMoving) {
        BlockEntity entity = level.getBlockEntity(pos);
        if(entity instanceof MWNamedInventoryBlockEntity blockEntity && state.getBlock() != newState.getBlock()){
            MWItemHandler inventory = blockEntity.getInventory();
            for (int i=0;i<inventory.getSlots();i++) {
                if(blockEntity.isDropSlot().test(i)) {
                    ItemEntity itemEntity = new ItemEntity(level, pos.getX(), pos.getY(), pos.getZ(), inventory.getStackInSlot(i));
                    level.addFreshEntity(itemEntity);
                }
            }
        }

        if(state.hasBlockEntity() && state.getBlock() != newState.getBlock()) {
            level.removeBlockEntity(pos);
        }
    }

}
