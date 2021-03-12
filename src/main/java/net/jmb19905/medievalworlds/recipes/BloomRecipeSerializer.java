package net.jmb19905.medievalworlds.recipes;

import com.google.gson.JsonObject;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.registries.ForgeRegistryEntry;

import javax.annotation.Nullable;

public class BloomRecipeSerializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<BloomRecipe> {

    @Override
    public BloomRecipe read(ResourceLocation recipeId, JsonObject json) {
        ItemStack output = CraftingHelper.getItemStack(JSONUtils.getJsonObject(json, "output"), true);
        ItemStack input = CraftingHelper.getItemStack(JSONUtils.getJsonObject(json, "input"), true);
        return new BloomRecipe(recipeId, input, output);
    }

    @Nullable
    @Override
    public BloomRecipe read(ResourceLocation recipeId, PacketBuffer buffer) {
        ItemStack output = buffer.readItemStack();
        ItemStack input = buffer.readItemStack();
        return new BloomRecipe(recipeId, input, output);
    }

    @Override
    public void write(PacketBuffer buffer, BloomRecipe recipe) {
        buffer.writeItemStack(recipe.getInput());
        buffer.writeItemStack(recipe.getRecipeOutput(), false);
    }
}
