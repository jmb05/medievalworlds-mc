package net.jmb19905.medievalworlds.core.tabs;

import net.jmb19905.medievalworlds.core.registries.MWBlocks;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nonnull;
import java.util.Comparator;

public class MWBlocksTab extends MWSortedTab {

    public MWBlocksTab() {
        super("medievalworlds.blocks");
    }

    @Override
    protected Comparator<ItemStack> getSorter() {
        return toSortingList(MWBlocks.getBlockItemTabOrder());
    }

    @Nonnull
    @Override
    public ItemStack makeIcon() {
        return new ItemStack(MWBlocks.ALLOY_FURNACE_BLOCK_ITEM.get());
    }
}
