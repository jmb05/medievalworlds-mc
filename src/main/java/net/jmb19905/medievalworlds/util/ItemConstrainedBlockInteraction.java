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

import java.util.Map;

public interface ItemConstrainedBlockInteraction extends BlockInteraction{

    @SuppressWarnings("unchecked")
    static <T extends ItemConstrainedBlockInteraction> Object2ObjectOpenHashMap<Item, T> newInteractionMap() {
        return Util.make(new Object2ObjectOpenHashMap<>(), (map)
                -> map.defaultReturnValue((T) (ItemConstrainedBlockInteraction) (state, level, pos, player, hand, stack) -> InteractionResult.PASS));
    }

}
