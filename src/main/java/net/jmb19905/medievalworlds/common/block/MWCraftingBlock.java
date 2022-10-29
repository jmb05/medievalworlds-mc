package net.jmb19905.medievalworlds.common.block;

import net.jmb19905.medievalworlds.common.blockentities.MWCraftingBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
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

public class MWCraftingBlock<T extends MWCraftingBlockEntity> extends Block implements EntityBlock {

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
            if (entity instanceof MWCraftingBlockEntity menuEntity) {
                NetworkHooks.openScreen((ServerPlayer) player, menuEntity, pos);
                awardUseStat(player);
            }
            return InteractionResult.CONSUME;
        }
    }

    protected void awardUseStat(Player player) {
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
        return blockEntityType.get().create(pos, state);
    }
}
