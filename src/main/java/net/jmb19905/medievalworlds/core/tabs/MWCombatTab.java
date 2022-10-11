package net.jmb19905.medievalworlds.core.tabs;

import net.jmb19905.medievalworlds.core.registries.MWItems;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nonnull;
import java.util.Comparator;

public class MWCombatTab extends MWSortedTab{

    public MWCombatTab() {
        super("medievalworlds.combat");
    }

    @Override
    protected Comparator<ItemStack> getSorter() {
        return toSortingList(MWItems.getCombatItemOrder());
    }

    @Nonnull
    @Override
    public ItemStack makeIcon() {
        return new ItemStack(MWItems.LONGSWORD.get());
    }
}