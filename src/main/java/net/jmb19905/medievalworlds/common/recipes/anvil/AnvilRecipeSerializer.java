package net.jmb19905.medievalworlds.common.recipes.anvil;

import com.google.gson.JsonObject;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.common.crafting.CraftingHelper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class AnvilRecipeSerializer implements RecipeSerializer<AnvilRecipe> {

    @Nonnull
    @Override
    public AnvilRecipe fromJson(@Nonnull ResourceLocation recipeId, @Nonnull JsonObject json) {
        ItemStack output = CraftingHelper.getItemStack(GsonHelper.getAsJsonObject(json, "output"), true);
        ItemStack input = CraftingHelper.getItemStack(GsonHelper.getAsJsonObject(json, "input"), true);

        return new AnvilRecipe(recipeId, input, output);
    }

    @Nullable
    @Override
    public AnvilRecipe fromNetwork(@Nonnull ResourceLocation recipeId, FriendlyByteBuf buffer) {
        ItemStack output = buffer.readItem();
        ItemStack input = buffer.readItem();

        return new AnvilRecipe(recipeId, input, output);
    }

    @Override
    public void toNetwork(@Nonnull FriendlyByteBuf buffer, @Nonnull AnvilRecipe recipe) {
        buffer.writeItemStack(recipe.getResultItem(), false);
        buffer.writeItemStack(recipe.getInput(), false);
    }
}
