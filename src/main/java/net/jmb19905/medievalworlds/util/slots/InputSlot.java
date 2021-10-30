package net.jmb19905.medievalworlds.util.slots;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;

public class InputSlot extends SlotItemHandler {

    private RecipeType<?> recipeType;

    public InputSlot(IItemHandler itemHandler, int index, int xPosition, int yPosition, RecipeType<?> recipeType) {
        super(itemHandler, index, xPosition, yPosition);
        this.recipeType = recipeType;
    }

    @Override
    public boolean mayPlace(@Nonnull ItemStack stack) {
        return true;
    }
}
