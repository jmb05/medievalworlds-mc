package net.jmb19905.medievalworlds.common.block;

import net.jmb19905.medievalworlds.common.blockentities.SmithingTableBlockEntity;
import net.jmb19905.medievalworlds.common.registries.MWBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class CustomSmithingTable extends Block implements EntityBlock{

    public CustomSmithingTable(Properties properties) {
        super(properties);
    }

    @SuppressWarnings("deprecation")
    @NotNull
    public InteractionResult use(@NotNull BlockState state, Level level, @NotNull BlockPos pos, @NotNull Player player, @NotNull InteractionHand hand, @NotNull BlockHitResult result) {
        if (level.isClientSide) {
            return InteractionResult.SUCCESS;
        } else {
            BlockEntity entity = level.getBlockEntity(pos);
            if (entity instanceof SmithingTableBlockEntity smithingEntity) {
                NetworkHooks.openScreen((ServerPlayer) player, smithingEntity, pos);
                player.awardStat(Stats.INTERACT_WITH_SMITHING_TABLE);
            }
            return InteractionResult.CONSUME;
        }
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
        return MWBlockEntityTypes.SMITHING_TABLE.get().create(pos, state);
    }
}