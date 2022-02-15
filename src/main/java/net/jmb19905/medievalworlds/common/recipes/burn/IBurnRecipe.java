package net.jmb19905.medievalworlds.common.recipes.burn;

import net.jmb19905.medievalworlds.MedievalWorlds;
import net.jmb19905.medievalworlds.core.registries.MWRecipeSerializers;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.Tag;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.items.wrapper.RecipeWrapper;

import javax.annotation.Nonnull;

public interface IBurnRecipe extends Recipe<RecipeWrapper> {

    ResourceLocation RECIPE_TYPE_ID = new ResourceLocation(MedievalWorlds.MOD_ID, "burn");

    @Nonnull
    @Override
    default RecipeType<?> getType(){
        return MWRecipeSerializers.BURN_TYPE;
    }

    @Nonnull
    @Override
    default RecipeSerializer<?> getSerializer() {
        return MWRecipeSerializers.BURN_SERIALIZER.get();
    }

    @Override
    default boolean canCraftInDimensions(int p_43999_, int p_44000_){
        return false;
    }

    String getInputTag();
    Block getOutput();
}
