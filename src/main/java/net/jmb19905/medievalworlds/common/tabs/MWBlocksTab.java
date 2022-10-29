package net.jmb19905.medievalworlds.common.tabs;

import net.jmb19905.medievalworlds.common.registries.MWBlocks;
import net.jmb19905.medievalworlds.common.registries.MWItems;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nonnull;
import java.util.Comparator;

public class MWBlocksTab extends MWSortedTab {

    public MWBlocksTab() {
        super("medievalworlds.blocks");
    }

    @Override
    protected Comparator<ItemStack> getSorter() {
        return toSortingList(MWItems.getBlockItemTabOrder());
    }

    @Nonnull
    @Override
    public ItemStack makeIcon() {
        return new ItemStack(MWBlocks.SLACK_TUB.get());
    }
}