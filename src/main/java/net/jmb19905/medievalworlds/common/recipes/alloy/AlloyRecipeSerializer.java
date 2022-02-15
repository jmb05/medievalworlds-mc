package net.jmb19905.medievalworlds.common.recipes.alloy;

import com.google.gson.JsonObject;
import net.jmb19905.medievalworlds.core.registries.MWRecipeSerializers;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.registries.ForgeRegistryEntry;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class AlloyRecipeSerializer extends ForgeRegistryEntry<RecipeSerializer<?>> implements RecipeSerializer<IAlloyRecipe> {

    @Nonnull
    @Override
    public AlloyRecipe fromJson(@Nonnull ResourceLocation recipeId, @Nonnull JsonObject json) {
        ItemStack output = CraftingHelper.getItemStack(GsonHelper.getAsJsonObject(json, "output"), true);
        ItemStack input1 = CraftingHelper.getItemStack(GsonHelper.getAsJsonObject(json, "input1"), true);
        ItemStack input2 = CraftingHelper.getItemStack(GsonHelper.getAsJsonObject(json, "input2"), true);

        return new AlloyRecipe(recipeId, input1, input2, output);
    }

    @Nullable
    @Override
    public AlloyRecipe fromNetwork(@Nonnull ResourceLocation recipeId, FriendlyByteBuf buffer) {
        ItemStack output = buffer.readItem();
        ItemStack input1 = buffer.readItem();
        ItemStack input2 = buffer.readItem();

        return new AlloyRecipe(recipeId, input1, input2, output);
    }

    @Override
    public void toNetwork(FriendlyByteBuf buffer, IAlloyRecipe recipe) {
        buffer.writeItemStack(recipe.getInput2(), true);
        buffer.writeItemStack(recipe.getInput1(), true);
        buffer.writeItemStack(recipe.getResultItem(), false);
    }
}
