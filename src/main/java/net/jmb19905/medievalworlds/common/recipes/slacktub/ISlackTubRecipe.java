package net.jmb19905.medievalworlds.common.recipes.slacktub;

import net.jmb19905.medievalworlds.MedievalWorlds;
import net.jmb19905.medievalworlds.common.registries.MWRecipeSerializers;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.items.wrapper.RecipeWrapper;

import javax.annotation.Nonnull;

public interface ISlackTubRecipe extends Recipe<RecipeWrapper> {

    ResourceLocation ID = new ResourceLocation(MedievalWorlds.MOD_ID, "slack_tub");

    @Nonnull
    @Override
    default RecipeType<?> getType(){
        return MWRecipeSerializers.SLACK_TUB_TYPE;
    }

    @Override
    default boolean canCraftInDimensions(int p_43999_, int p_44000_){
        return false;
    }
    Ingredient getInput();
    int getInputCount();
}