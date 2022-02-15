package net.jmb19905.medievalworlds.common.recipes.bloom;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.registries.ForgeRegistryEntry;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class BloomRecipeSerializer extends ForgeRegistryEntry<RecipeSerializer<?>> implements RecipeSerializer<BloomRecipe> {

    @Nonnull
    @Override
    public BloomRecipe fromJson(@Nonnull ResourceLocation recipeId, @Nonnull JsonObject json) {
        JsonElement jsonelement = GsonHelper.isArrayNode(json, "input") ? GsonHelper.getAsJsonArray(json, "input") : GsonHelper.getAsJsonObject(json, "input");
        Ingredient input = Ingredient.fromJson(jsonelement);
        ItemStack primaryOutput = CraftingHelper.getItemStack(GsonHelper.getAsJsonObject(json, "primaryOutput"), true);
        ItemStack primaryPacked = CraftingHelper.getItemStack(GsonHelper.getAsJsonObject(json, "primaryPacked"), true);
        int primaryOffset = GsonHelper.getAsInt(json, "primaryOffset");
        ItemStack secondaryOutput = CraftingHelper.getItemStack(GsonHelper.getAsJsonObject(json, "secondaryOutput"), true);
        ItemStack secondaryPacked = CraftingHelper.getItemStack(GsonHelper.getAsJsonObject(json, "secondaryPacked"), true);
        int secondaryOffset = GsonHelper.getAsInt(json, "secondaryOffset");
        return new BloomRecipe(recipeId, input, primaryOutput, primaryPacked, primaryOffset, secondaryOutput, secondaryPacked, secondaryOffset);
    }

    @Nullable
    @Override
    public BloomRecipe fromNetwork(@Nonnull ResourceLocation recipeId, @NotNull FriendlyByteBuf buffer) {
        Ingredient input = Ingredient.fromNetwork(buffer);
        ItemStack primary = buffer.readItem();
        ItemStack primaryPacked = buffer.readItem();
        int primaryOffset = buffer.readInt();
        ItemStack secondary = buffer.readItem();
        ItemStack secondaryPacked = buffer.readItem();
        int secondaryOffset = buffer.readInt();
        return new BloomRecipe(recipeId, input, primary, primaryPacked, primaryOffset, secondary, secondaryPacked, secondaryOffset);
    }

    @Override
    public void toNetwork(FriendlyByteBuf buffer, BloomRecipe recipe) {
        buffer.writeInt(recipe.getPrimaryOffset());
        buffer.writeItemStack(recipe.getSecondaryOutputPacked(), true);
        buffer.writeItemStack(recipe.getSecondaryOutput(), true);
        buffer.writeInt(recipe.getSecondaryOffset());
        buffer.writeItemStack(recipe.getPrimaryOutputPacked(), true);
        buffer.writeItemStack(recipe.getPrimaryOutput(), true);
        recipe.getInput().toNetwork(buffer);
    }
}
