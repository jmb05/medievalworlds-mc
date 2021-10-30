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
        ItemStack secondary = CraftingHelper.getItemStack(GsonHelper.getAsJsonObject(json, "secondary"), true);
        ItemStack input = CraftingHelper.getItemStack(GsonHelper.getAsJsonObject(json, "input"), true);
        float secondaryChance = GsonHelper.convertToFloat(json, "chance");
        return new BloomRecipe(recipeId, input, secondary, secondaryChance, output);
    }

    @Nullable
    @Override
    public BloomRecipe fromNetwork(@Nonnull ResourceLocation recipeId, FriendlyByteBuf buffer) {
        ItemStack output = buffer.readItem();
        ItemStack secondary = buffer.readItem();
        ItemStack input = buffer.readItem();
        float chance = buffer.readFloat();
        return new BloomRecipe(recipeId, input, secondary, chance, output);
    }

    @Override
    public void toNetwork(FriendlyByteBuf buffer, BloomRecipe recipe) {
        buffer.writeFloat(recipe.getSecondaryChance());
        buffer.writeItemStack(recipe.getInput(), true);
        buffer.writeItemStack(recipe.getSecondaryOutput(), true);
        buffer.writeItemStack(recipe.getResultItem(), false);
    }
}
