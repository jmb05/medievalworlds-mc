package net.jmb19905.medievalworlds.common.recipes.alloy;

import net.jmb19905.medievalworlds.MedievalWorlds;
import net.jmb19905.medievalworlds.common.registries.MWRecipeSerializers;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.items.wrapper.RecipeWrapper;

import javax.annotation.Nonnull;

public interface IAlloyRecipe extends Recipe<RecipeWrapper> {

    ResourceLocation ID = new ResourceLocation(MedievalWorlds.MOD_ID, "alloy");

    @Nonnull
    @Override
    default RecipeType<?> getType(){
        return MWRecipeSerializers.ALLOY_TYPE;
    }

    @Nonnull
    @Override
    default RecipeSerializer<?> getSerializer() {
        return MWRecipeSerializers.ALLOY_SERIALIZER.get();
    }

    @Override
    default boolean canCraftInDimensions(int p_43999_, int p_44000_){
        return false;
    }

    ItemStack getInput1();

    ItemStack getInput2();

}