package net.jmb19905.medievalworlds.common.item.quiver;

import net.minecraft.core.NonNullList;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.ItemStack;

@SuppressWarnings("ClassCanBeRecord")
public class QuiverTooltip implements TooltipComponent {
    private final NonNullList<ItemStack> items;

    public QuiverTooltip(NonNullList<ItemStack> items) {
        this.items = items;
    }

    public NonNullList<ItemStack> getItems() {
        return items;
    }
}
