package net.jmb19905.medievalworlds.common.recipes.fletching;

import com.google.gson.JsonObject;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.common.crafting.CraftingHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class FletchingRecipeSerializer implements RecipeSerializer<IFletchingRecipe> {
    @Override
    @NotNull
    public IFletchingRecipe fromJson(@NotNull ResourceLocation id, @NotNull JsonObject json) {
        Ingredient arrowHead = CraftingHelper.getIngredient(GsonHelper.getAsJsonObject(json, "head"));
        Ingredient arrowShaft = CraftingHelper.getIngredient(GsonHelper.getAsJsonObject(json, "shaft"));
        Ingredient arrowFletching = CraftingHelper.getIngredient(GsonHelper.getAsJsonObject(json, "fletching"));
        ItemStack result = CraftingHelper.getItemStack(GsonHelper.getAsJsonObject(json, "result"), true);

        return new FletchingRecipe(id, arrowHead, arrowShaft, arrowFletching, result);
    }

    @Nullable
    @Override
    public IFletchingRecipe fromNetwork(@NotNull ResourceLocation id, @NotNull FriendlyByteBuf buffer) {
        Ingredient arrowHead = Ingredient.fromNetwork(buffer);
        Ingredient arrowShaft = Ingredient.fromNetwork(buffer);
        Ingredient arrowFletching = Ingredient.fromNetwork(buffer);
        ItemStack result = buffer.readItem();
        return new FletchingRecipe(id, arrowHead, arrowShaft, arrowFletching, result);
    }

    @Override
    public void toNetwork(@NotNull FriendlyByteBuf buffer, @NotNull IFletchingRecipe recipe) {
        recipe.getArrowHead().toNetwork(buffer);
        recipe.getArrowShaft().toNetwork(buffer);
        recipe.getArrowFletching().toNetwork(buffer);
        buffer.writeItemStack(recipe.getResultItem(), false);
    }
}
