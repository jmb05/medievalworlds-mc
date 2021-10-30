package net.jmb19905.medievalworlds.common.recipes.anvil;

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

public class AnvilRecipeSerializer extends ForgeRegistryEntry<RecipeSerializer<?>> implements RecipeSerializer<AnvilRecipe> {

    @Nonnull
    @Override
    public AnvilRecipe fromJson(@Nonnull ResourceLocation recipeId, @Nonnull JsonObject json) {
        ItemStack output = CraftingHelper.getItemStack(GsonHelper.getAsJsonObject(json, "output"), true);
        ItemStack input1 = CraftingHelper.getItemStack(GsonHelper.getAsJsonObject(json, "input1"), true);
        ItemStack input2 = CraftingHelper.getItemStack(GsonHelper.getAsJsonObject(json, "input2"), true);

        return new AnvilRecipe(recipeId, input1, input2, output);
    }

    @Nullable
    @Override
    public AnvilRecipe fromNetwork(@Nonnull ResourceLocation recipeId, FriendlyByteBuf buffer) {
        ItemStack output = buffer.readItem();
        ItemStack input1 = buffer.readItem();
        ItemStack input2 = buffer.readItem();

        return new AnvilRecipe(recipeId, input1, input2, output);
    }

    @Override
    public void toNetwork(@Nonnull FriendlyByteBuf buffer, @Nonnull AnvilRecipe recipe) {
        buffer.writeItemStack(recipe.getInput2(), false);
        buffer.writeItemStack(recipe.getInput1(), false);
        buffer.writeItemStack(recipe.getResultItem(), false);
    }
}
