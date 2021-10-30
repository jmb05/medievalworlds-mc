package net.jmb19905.medievalworlds.common.recipes.grind;

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

public class GrindRecipeSerializer extends ForgeRegistryEntry<RecipeSerializer<?>> implements RecipeSerializer<GrindRecipe> {
    @Nonnull
    @Override
    public GrindRecipe fromJson(@Nonnull ResourceLocation recipeId, @Nonnull JsonObject json) {
        ItemStack output = CraftingHelper.getItemStack(GsonHelper.getAsJsonObject(json, "output"), true);
        ItemStack input = CraftingHelper.getItemStack(GsonHelper.getAsJsonObject(json, "input"), true);

        return new GrindRecipe(recipeId, input, output);
    }

    @Nullable
    @Override
    public GrindRecipe fromNetwork(@Nonnull ResourceLocation recipeId, @Nonnull FriendlyByteBuf buffer) {
        ItemStack output = buffer.readItem();
        ItemStack input = buffer.readItem();

        return new GrindRecipe(recipeId, input, output);
    }

    @Override
    public void toNetwork(@Nonnull FriendlyByteBuf buffer, @Nonnull GrindRecipe recipe) {
        buffer.writeItemStack(recipe.getInput(), true);
        buffer.writeItemStack(recipe.getResultItem(), false);
    }
}
