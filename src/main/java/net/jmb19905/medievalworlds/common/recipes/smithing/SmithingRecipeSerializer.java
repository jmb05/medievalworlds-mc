package net.jmb19905.medievalworlds.common.recipes.smithing;

import com.google.gson.JsonObject;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.registries.ForgeRegistryEntry;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SmithingRecipeSerializer extends ForgeRegistryEntry<RecipeSerializer<?>> implements RecipeSerializer<ISmithingRecipe>{
    @Override
    @NotNull
    public ISmithingRecipe fromJson(@NotNull ResourceLocation id, @NotNull JsonObject json) {
        ItemStack input = CraftingHelper.getItemStack(GsonHelper.getAsJsonObject(json, "base"), true);
        ItemStack addition = CraftingHelper.getItemStack(GsonHelper.getAsJsonObject(json, "addition"), true);
        ItemStack result = CraftingHelper.getItemStack(GsonHelper.getAsJsonObject(json, "result"), true);
        int cost = GsonHelper.getAsInt(json, "cost");

        return new SmithingRecipe(id, input, addition, result, cost);
    }

    @Nullable
    @Override
    public ISmithingRecipe fromNetwork(@NotNull ResourceLocation id, @NotNull FriendlyByteBuf buffer) {
        ItemStack input = buffer.readItem();
        ItemStack addition = buffer.readItem();
        ItemStack result = buffer.readItem();
        int cost = buffer.readInt();
        return new SmithingRecipe(id, input, addition, result, cost);
    }

    @Override
    public void toNetwork(@NotNull FriendlyByteBuf buffer, @NotNull ISmithingRecipe recipe) {
        buffer.writeItemStack(recipe.getInput(), true);
        buffer.writeItemStack(recipe.getAddition(), true);
        buffer.writeItemStack(recipe.getResultItem(), true);
        buffer.writeInt(recipe.getCost());
    }
}
