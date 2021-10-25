package net.jmb19905.medievalworlds.util.slots;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;

public class BloomInputSlot extends SlotItemHandler {

    private final ItemStack currentFuelItem;

    public BloomInputSlot(IItemHandler itemHandler, int index, int fuelIndex, int xPosition, int yPosition) {
        super(itemHandler, index, xPosition, yPosition);
        currentFuelItem = itemHandler.getStackInSlot(fuelIndex);
    }

    @Override
    public boolean mayPlace(@Nonnull ItemStack stack) {
        return stack.getItem() == Items.IRON_ORE;
    }

    @Override
    public int getMaxStackSize() {
        return currentFuelItem.getCount() * 8;
    }
}
