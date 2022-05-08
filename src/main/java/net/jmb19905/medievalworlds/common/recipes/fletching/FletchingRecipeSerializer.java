package net.jmb19905.medievalworlds.common.recipes.fletching;

import com.google.gson.JsonObject;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.registries.ForgeRegistryEntry;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class FletchingRecipeSerializer extends ForgeRegistryEntry<RecipeSerializer<?>> implements RecipeSerializer<IFletchingRecipe>{

    @Nonnull
    @Override
    public FletchingRecipe fromJson(@Nonnull ResourceLocation recipeId, @Nonnull JsonObject json) {
        ItemStack output = CraftingHelper.getItemStack(GsonHelper.getAsJsonObject(json, "output"), true);
        Ingredient input1 = Ingredient.fromJson(json.get("input1"));
        Ingredient input2 = Ingredient.fromJson(json.get("input2"));
        Ingredient input3 = Ingredient.fromJson(json.get("input3"));


        return new FletchingRecipe(recipeId, input1, input2, input3, output);
    }

    @Nullable
    @Override
    public FletchingRecipe fromNetwork(@Nonnull ResourceLocation recipeId, FriendlyByteBuf buffer) {
        ItemStack output = buffer.readItem();
        Ingredient input1 = Ingredient.fromNetwork(buffer);
        Ingredient input2 = Ingredient.fromNetwork(buffer);
        Ingredient input3 = Ingredient.fromNetwork(buffer);

        return new FletchingRecipe(recipeId, input1, input2, input3, output);
    }

    @Override
    public void toNetwork(FriendlyByteBuf buffer, IFletchingRecipe recipe) {
        buffer.writeItemStack(recipe.getResultItem(), false);
        recipe.getInput1().toNetwork(buffer);
        recipe.getInput2().toNetwork(buffer);
        recipe.getInput3().toNetwork(buffer);
    }
}
