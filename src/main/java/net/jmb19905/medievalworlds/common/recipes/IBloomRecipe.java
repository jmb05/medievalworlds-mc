package net.jmb19905.medievalworlds.common.recipes;

import net.jmb19905.medievalworlds.MedievalWorlds;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.items.wrapper.RecipeWrapper;

import javax.annotation.Nonnull;
import java.util.Objects;

public interface IBloomRecipe extends IRecipe<RecipeWrapper> {

    ResourceLocation RECIPE_TYPE_ID = new ResourceLocation(MedievalWorlds.MOD_ID, "bloom");

    @Nonnull
    @Override
    default IRecipeType<?> getType(){
        return Objects.requireNonNull(Registry.RECIPE_TYPE.getOrDefault(RECIPE_TYPE_ID));
    }

    @Override
    default boolean canFit(int width, int height) {
        return false;
    }

    ItemStack getInput();

}
