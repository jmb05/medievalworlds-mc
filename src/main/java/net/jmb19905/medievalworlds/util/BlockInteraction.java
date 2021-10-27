package net.jmb19905.medievalworlds.util;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public interface BlockInteraction {
    InteractionResult interact(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, ItemStack stack);

    @SuppressWarnings("unchecked")
    static <T extends BlockInteraction> Object2ObjectOpenHashMap<Item, T> newInteractionMap() {
        return Util.make(new Object2ObjectOpenHashMap<>(), (map)
                -> map.defaultReturnValue((T) (BlockInteraction) (state, level, pos, player, hand, stack) -> InteractionResult.PASS));
    }

}
