package net.jmb19905.medievalworlds.util.slots;

import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;

public class BloomInputSlot extends SlotItemHandler {

    private ItemStack currentFuelItem;

    public BloomInputSlot(IItemHandler itemHandler, int index, int fuelIndex, int xPosition, int yPosition) {
        super(itemHandler, index, xPosition, yPosition);
        currentFuelItem = itemHandler.getStackInSlot(fuelIndex);
    }

    @Override
    public boolean isItemValid(@Nonnull ItemStack stack) {
        return stack.getItem() == Items.IRON_ORE;
    }

    @Override
    public int getSlotStackLimit() {
        return currentFuelItem.getCount() * 8;
    }
}
