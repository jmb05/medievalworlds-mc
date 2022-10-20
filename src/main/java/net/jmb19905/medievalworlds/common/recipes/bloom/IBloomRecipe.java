package net.jmb19905.medievalworlds.common.recipes.bloom;

import net.jmb19905.medievalworlds.MedievalWorlds;
import net.jmb19905.medievalworlds.common.registries.MWRecipeSerializers;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.items.wrapper.RecipeWrapper;

import javax.annotation.Nonnull;

public interface IBloomRecipe extends Recipe<RecipeWrapper> {

    ResourceLocation ID = new ResourceLocation(MedievalWorlds.MOD_ID, "bloom");

    @Nonnull
    @Override
    default RecipeType<?> getType(){
        return MWRecipeSerializers.BLOOM_TYPE;
    }

    @Override
    default boolean canCraftInDimensions(int p_43999_, int p_44000_){
        return false;
    }

    Ingredient getInput();
    ItemStack getPrimaryOutput();
    ItemStack getPrimaryOutputPacked();
    int getPrimaryOffset();
    ItemStack getSecondaryOutput();
    ItemStack getSecondaryOutputPacked();
    int getSecondaryOffset();
}