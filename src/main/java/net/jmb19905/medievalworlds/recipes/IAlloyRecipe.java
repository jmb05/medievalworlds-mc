package net.jmb19905.medievalworlds.recipes;

import net.jmb19905.medievalworlds.MedievalWorlds;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.items.wrapper.RecipeWrapper;

import javax.annotation.Nonnull;

public interface IAlloyRecipe extends IRecipe<RecipeWrapper> {

    ResourceLocation RECIPE_TYPE_ID = new ResourceLocation(MedievalWorlds.MOD_ID, "alloy");

    @Nonnull
    @Override
    default IRecipeType<?> getType(){
        return Registry.RECIPE_TYPE.getValue(RECIPE_TYPE_ID).get();
    }

    @Override
    default boolean canFit(int width, int height) {
        return false;
    }

    ItemStack getInput1();

    ItemStack getInput2();

}
