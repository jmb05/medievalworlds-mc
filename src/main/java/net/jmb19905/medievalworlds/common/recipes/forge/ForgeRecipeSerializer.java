package net.jmb19905.medievalworlds.common.recipes.forge;

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

public class ForgeRecipeSerializer extends ForgeRegistryEntry<RecipeSerializer<?>> implements RecipeSerializer<ForgeRecipe> {

    @Nonnull
    @Override
    public ForgeRecipe fromJson(@Nonnull ResourceLocation recipeId, @Nonnull JsonObject json) {
        ItemStack output = CraftingHelper.getItemStack(GsonHelper.getAsJsonObject(json, "output"), true);
        ItemStack input = CraftingHelper.getItemStack(GsonHelper.getAsJsonObject(json, "input"), true);

        return new ForgeRecipe(recipeId, input, output);
    }

    @Nullable
    @Override
    public ForgeRecipe fromNetwork(@Nonnull ResourceLocation recipeId, @Nonnull FriendlyByteBuf buffer) {
        ItemStack output = buffer.readItem();
        ItemStack input = buffer.readItem();

        return new ForgeRecipe(recipeId, input, output);
    }

    @Override
    public void toNetwork(@Nonnull FriendlyByteBuf buffer, @Nonnull ForgeRecipe recipe) {
        buffer.writeItemStack(recipe.getInput(), false);
        buffer.writeItemStack(recipe.getResultItem(), false);
    }
}
