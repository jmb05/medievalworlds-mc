package net.jmb19905.medievalworlds.common.tabs;

import net.jmb19905.medievalworlds.common.registries.MWItems;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nonnull;
import java.util.Comparator;

public class MWMaterialsTab extends MWSortedTab {

    public MWMaterialsTab() {
        super("medievalworlds.materials");
    }

    @Override
    protected Comparator<ItemStack> getSorter() {
        return toSortingList(MWItems.getMaterialsItemOrder());
    }

    @Nonnull
    @Override
    public ItemStack makeIcon() {
        return new ItemStack(MWItems.HEATED_STEEL_INGOT.get());
    }
}