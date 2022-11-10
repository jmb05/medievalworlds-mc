package net.jmb19905.medievalworlds.common.inv.slots;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;

public class FuelInputSlot extends SlotItemHandler {

    private RecipeType<?> type = RecipeType.BLASTING;

    public FuelInputSlot(IItemHandler itemHandler, int index, int xPosition, int yPosition) {
        super(itemHandler, index, xPosition, yPosition);
    }

    public FuelInputSlot(IItemHandler itemHandler, int index, int xPosition, int yPosition, RecipeType<?> type) {
        super(itemHandler, index, xPosition, yPosition);
        this.type = type;
    }

    @Override
    public boolean mayPlace(@Nonnull ItemStack stack) {
        return ForgeHooks.getBurnTime(stack, type) > 0;
    }

}