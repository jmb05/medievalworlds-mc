package net.jmb19905.medievalworlds.common.item;

import net.jmb19905.medievalworlds.core.registries.MWBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class ForgeHammerItem extends TieredItem {

    public ForgeHammerItem(Tier tier, Properties properties) {
        super(tier, properties);
    }

    @NotNull
    @Override
    public InteractionResult useOn(@NotNull UseOnContext ctx) {
        Player player = ctx.getPlayer();
        ItemStack item = ctx.getItemInHand();
        BlockPos pos = ctx.getClickedPos();
        BlockState state = ctx.getLevel().getBlockState(pos);
        assert player != null;
        if(state.is(BlockTags.BASE_STONE_OVERWORLD) && !player.getCooldowns().isOnCooldown(item.getItem())) {
            player.getCooldowns().addCooldown(item.getItem(), 10);
            ctx.getLevel().setBlock(pos, MWBlocks.STONE_ANVIL.get().defaultBlockState(), 3);
        }
        return super.useOn(ctx);
    }
}
