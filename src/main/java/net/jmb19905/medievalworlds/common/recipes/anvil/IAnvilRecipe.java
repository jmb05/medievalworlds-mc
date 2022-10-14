package net.jmb19905.medievalworlds.common.recipes.anvil;

import net.jmb19905.medievalworlds.MedievalWorlds;
import net.jmb19905.medievalworlds.common.registries.MWRecipeSerializers;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.items.wrapper.RecipeWrapper;

import javax.annotation.Nonnull;

public interface IAnvilRecipe extends Recipe<RecipeWrapper> {

    ResourceLocation ID = new ResourceLocation(MedievalWorlds.MOD_ID, "anvil");

    @Nonnull
    @Override
    default RecipeType<?> getType(){
        return MWRecipeSerializers.ANVIL_TYPE;
    }

    @Override
    default boolean canCraftInDimensions(int p_43999_, int p_44000_){
        return false;
    }

    ItemStack getInput();
    int getMinAnvilLevel();
}
