package net.jmb19905.medievalworlds.common.recipes.bloom;

import com.google.gson.JsonObject;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.registries.ForgeRegistryEntry;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class BloomRecipeSerializer extends ForgeRegistryEntry<RecipeSerializer<?>> implements RecipeSerializer<BloomRecipe> {

    @Nonnull
    @Override
    public BloomRecipe fromJson(@Nonnull ResourceLocation recipeId, @Nonnull JsonObject json) {
        ItemStack output = CraftingHelper.getItemStack(GsonHelper.getAsJsonObject(json, "output"), true);
        ItemStack input = CraftingHelper.getItemStack(GsonHelper.getAsJsonObject(json, "input"), true);
        return new BloomRecipe(recipeId, input, output);
    }

    @Nullable
    @Override
    public BloomRecipe fromNetwork(@Nonnull ResourceLocation recipeId, FriendlyByteBuf buffer) {
        ItemStack output = buffer.readItem();
        ItemStack input = buffer.readItem();
        return new BloomRecipe(recipeId, input, output);
    }

    @Override
    public void toNetwork(FriendlyByteBuf buffer, BloomRecipe recipe) {
        buffer.writeItemStack(recipe.getInput(), true);
        buffer.writeItemStack(recipe.getResultItem(), false);
    }
}
