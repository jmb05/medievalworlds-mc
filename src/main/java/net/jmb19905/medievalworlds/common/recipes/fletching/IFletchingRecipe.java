package net.jmb19905.medievalworlds.common.recipes.fletching;

import net.jmb19905.medievalworlds.MedievalWorlds;
import net.jmb19905.medievalworlds.common.registries.MWRecipeSerializers;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.items.wrapper.RecipeWrapper;
import org.jetbrains.annotations.NotNull;

public interface IFletchingRecipe extends Recipe<RecipeWrapper> {

    ResourceLocation ID = new ResourceLocation(MedievalWorlds.MOD_ID, "fletching");

    @Override
    @NotNull
    default RecipeType<?> getType() {
        return MWRecipeSerializers.FLETCHING_TYPE;
    }

    @Override
    @NotNull
    default RecipeSerializer<?> getSerializer(){
        return MWRecipeSerializers.FLETCHING_SERIALIZER.get();
    }

    Ingredient getArrowHead();
    Ingredient getArrowShaft();
    Ingredient getArrowFletching();

}