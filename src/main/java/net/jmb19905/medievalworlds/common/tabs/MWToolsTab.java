package net.jmb19905.medievalworlds.common.tabs;


import net.jmb19905.medievalworlds.common.registries.MWItems;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nonnull;
import java.util.Comparator;

public class MWToolsTab extends MWSortedTab {

    public MWToolsTab() {
        super("medievalworlds.tools");
    }

    @Override
    public Comparator<ItemStack> getSorter() {
        return toSortingList(MWItems.getToolItemOrder());
    }

    @Nonnull
    @Override
    public ItemStack makeIcon() {
        return new ItemStack(MWItems.IRON_FORGE_HAMMER.get());
    }
}