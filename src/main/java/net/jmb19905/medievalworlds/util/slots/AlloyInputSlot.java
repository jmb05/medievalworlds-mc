package net.jmb19905.medievalworlds.util.slots;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;

public class AlloyInputSlot extends SlotItemHandler {

    private final Level level;

    public AlloyInputSlot(IItemHandler itemHandler, int index, int xPosition, int yPosition, Level level) {
        super(itemHandler, index, xPosition, yPosition);
        this.level = level;
    }

    @Override
    public boolean mayPlace(@Nonnull ItemStack stack) {
        //return AlloyFurnaceTileEntity.getAllRecipeInputs(RecipeSerializerRegistryHandler.ALLOY_TYPE, world).contains(stack);
        return true;
    }
}
