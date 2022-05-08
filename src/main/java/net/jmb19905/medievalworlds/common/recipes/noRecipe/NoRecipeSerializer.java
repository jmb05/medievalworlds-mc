package net.jmb19905.medievalworlds.common.recipes.noRecipe;

import com.google.gson.JsonObject;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.registries.ForgeRegistryEntry;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class NoRecipeSerializer extends ForgeRegistryEntry<RecipeSerializer<?>> implements RecipeSerializer<NoRecipe> {
    @Override
    public @NotNull NoRecipe fromJson(@NotNull ResourceLocation id, @NotNull JsonObject object) {
        return new NoRecipe(id);
    }

    @Nullable
    @Override
    public NoRecipe fromNetwork(@NotNull ResourceLocation id, @NotNull FriendlyByteBuf buffer) {
        return new NoRecipe(id);
    }

    @Override
    public void toNetwork(@NotNull FriendlyByteBuf buffer, @NotNull NoRecipe recipe) {}
}
