package net.jmb19905.medievalworlds.common.recipes.smithing;

import net.jmb19905.medievalworlds.MedievalWorlds;
import net.jmb19905.medievalworlds.common.registries.MWRecipeSerializers;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.items.wrapper.RecipeWrapper;
import org.jetbrains.annotations.NotNull;

public interface ISmithingRecipe extends Recipe<RecipeWrapper> {

    ResourceLocation ID = new ResourceLocation(MedievalWorlds.MOD_ID, "smithing");

    @Override
    @NotNull
    default RecipeType<?> getType() {
        return MWRecipeSerializers.SMITHING_TYPE;
    }

    @Override
    @NotNull
    default RecipeSerializer<?> getSerializer(){
        return MWRecipeSerializers.SMITHING_SERIALIZER.get();
    }

    boolean isAdditionIngredient(ItemStack stack);

    ItemStack getInput();
    ItemStack getAddition();
    int getCost();

}