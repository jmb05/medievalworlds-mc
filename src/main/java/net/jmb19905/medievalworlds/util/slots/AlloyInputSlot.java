package net.jmb19905.medievalworlds.util.slots;

import net.jmb19905.medievalworlds.registries.RecipeSerializerRegistryHandler;
import net.jmb19905.medievalworlds.tileentites.AlloyFurnaceTileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;

public class AlloyInputSlot extends SlotItemHandler {

    private final World world;

    public AlloyInputSlot(IItemHandler itemHandler, int index, int xPosition, int yPosition, World world) {
        super(itemHandler, index, xPosition, yPosition);
        this.world = world;
    }

    @Override
    public boolean isItemValid(@Nonnull ItemStack stack) {
        //return AlloyFurnaceTileEntity.getAllRecipeInputs(RecipeSerializerRegistryHandler.ALLOY_TYPE, world).contains(stack);
        return true;
    }
}
