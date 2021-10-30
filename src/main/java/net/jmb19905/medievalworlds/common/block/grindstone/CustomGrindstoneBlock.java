package net.jmb19905.medievalworlds.common.block.grindstone;

import net.minecraft.core.BlockPos;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.GrindstoneBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

import javax.annotation.Nonnull;

public class CustomGrindstoneBlock extends GrindstoneBlock{

    public CustomGrindstoneBlock(Properties properties) {
        super(properties);
    }

    @Nonnull
    @Override
    public InteractionResult use(@Nonnull BlockState state, Level level, @Nonnull BlockPos pos, @Nonnull Player player, @Nonnull InteractionHand hand, @Nonnull BlockHitResult hitResult) {
        if(level.isClientSide) {
            return InteractionResult.SUCCESS;
        }else {
            player.awardStat(Stats.INTERACT_WITH_GRINDSTONE);
            return GrindstoneInteraction.GRIND_ITEM.interact(state, level, pos, player, hand, player.getItemInHand(hand));
        }
    }
}
