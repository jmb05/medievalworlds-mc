package net.jmb19905.medievalworlds.common.recipes.slackTub;

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

public class SlackTubRecipeSerializer extends ForgeRegistryEntry<RecipeSerializer<?>> implements RecipeSerializer<SlackTubRecipe> {
    @Nonnull
    @Override
    public SlackTubRecipe fromJson(@Nonnull ResourceLocation recipeId, @Nonnull JsonObject json) {
        ItemStack output = CraftingHelper.getItemStack(GsonHelper.getAsJsonObject(json, "output"), true);
        Ingredient input = Ingredient.fromJson(GsonHelper.getAsJsonObject(json, "input"));
        int inputCount = GsonHelper.getAsInt(json, "inputCount");

        return new SlackTubRecipe(recipeId, input, inputCount, output);
    }

    @Nullable
    @Override
    public SlackTubRecipe fromNetwork(@Nonnull ResourceLocation recipeId, FriendlyByteBuf buffer) {
        ItemStack output = buffer.readItem();
        Ingredient input = Ingredient.fromNetwork(buffer);
        int inputCount = buffer.readInt();

        return new SlackTubRecipe(recipeId, input, inputCount, output);
    }

    @Override
    public void toNetwork(@Nonnull FriendlyByteBuf buffer, @Nonnull SlackTubRecipe recipe) {
        buffer.writeItemStack(recipe.getResultItem(), false);
        recipe.getInput().toNetwork(buffer);
        buffer.writeInt(recipe.getInputCount());
    }
}
