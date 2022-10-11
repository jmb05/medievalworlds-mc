package net.jmb19905.medievalworlds.core.tabs;

import net.jmb19905.medievalworlds.core.registries.MWItems;
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
        return ItemStack.EMPTY;
        //return new ItemStack(MWItems.CLOAK.get());
    }
}