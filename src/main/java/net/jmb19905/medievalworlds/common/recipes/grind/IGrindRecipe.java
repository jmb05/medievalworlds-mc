package net.jmb19905.medievalworlds.common.recipes.grind;

import net.jmb19905.medievalworlds.MedievalWorlds;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.items.wrapper.RecipeWrapper;

import javax.annotation.Nonnull;
import java.util.Objects;

public interface IGrindRecipe extends Recipe<RecipeWrapper> {

    ResourceLocation RECIPE_TYPE_ID = new ResourceLocation(MedievalWorlds.MOD_ID, "grind");

    @Nonnull
    @Override
    default RecipeType<?> getType(){
        return Objects.requireNonNull(Registry.RECIPE_TYPE.get(RECIPE_TYPE_ID));
    }

    @Override
    default boolean canCraftInDimensions(int p_43999_, int p_44000_){
        return false;
    }

    ItemStack getInput();

}
