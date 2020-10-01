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

public class AlloyRecipeSerializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<AlloyRecipe> {

    @Override
    public AlloyRecipe read(ResourceLocation recipeId, JsonObject json) {
        ItemStack output = CraftingHelper.getItemStack(JSONUtils.getJsonObject(json, "output"), true);
        ItemStack input1 = CraftingHelper.getItemStack(JSONUtils.getJsonObject(json, "input1"), true);
        ItemStack input2 = CraftingHelper.getItemStack(JSONUtils.getJsonObject(json, "input2"), true);

        return new AlloyRecipe(recipeId, input1, input2, output);
    }

    @Nullable
    @Override
    public AlloyRecipe read(ResourceLocation recipeId, PacketBuffer buffer) {
        ItemStack output = buffer.readItemStack();
        ItemStack input1 = buffer.readItemStack();
        ItemStack input2 = buffer.readItemStack();

        return new AlloyRecipe(recipeId, input1, input2, output);
    }

    @Override
    public void write(PacketBuffer buffer, AlloyRecipe recipe) {
        buffer.writeItemStack(recipe.getInputs().get(0));
        buffer.writeItemStack(recipe.getInputs().get(1));
        buffer.writeItemStack(recipe.getRecipeOutput(), false);
    }
}
