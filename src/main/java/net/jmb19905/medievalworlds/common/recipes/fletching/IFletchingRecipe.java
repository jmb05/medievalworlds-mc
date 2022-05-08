package net.jmb19905.medievalworlds.common.recipes.fletching;

import net.jmb19905.medievalworlds.MedievalWorlds;
import net.jmb19905.medievalworlds.core.registries.MWRecipeSerializers;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.items.wrapper.RecipeWrapper;

import javax.annotation.Nonnull;

public interface IFletchingRecipe extends Recipe<RecipeWrapper> {

    ResourceLocation RECIPE_TYPE_ID = new ResourceLocation(MedievalWorlds.MOD_ID, "fletching");

    @Nonnull
    @Override
    default RecipeType<?> getType(){
        return MWRecipeSerializers.FLETCHING_TYPE;
    }

    @Nonnull
    @Override
    default RecipeSerializer<?> getSerializer() {
        return MWRecipeSerializers.FLETCHING_SERIALIZER.get();
    }

    @Override
    default boolean canCraftInDimensions(int p_43999_, int p_44000_){
        return false;
    }

    Ingredient getInput1();

    Ingredient getInput2();

    Ingredient getInput3();
}
