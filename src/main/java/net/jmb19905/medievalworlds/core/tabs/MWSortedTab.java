package net.jmb19905.medievalworlds.core.tabs;

import com.google.common.collect.Ordering;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nonnull;
import java.util.Comparator;
import java.util.List;

public abstract class MWSortedTab extends CreativeModeTab {

    public MWSortedTab(String label) {
        super(label);
    }

    protected abstract Comparator<ItemStack> getSorter();

    @SuppressWarnings("unchecked")
    public static <T extends Item> Comparator<ItemStack> toSortingList(List<T> order) {
        return Ordering.explicit(order).onResultOf(stack -> (T) stack.getItem());
    }

    @Override
    public void fillItemList(@Nonnull NonNullList<ItemStack> items) {
        super.fillItemList(items);
        try {
            items.sort(getSorter());
        }catch (ClassCastException ignored){}
    }
}