package net.jmb19905.medievalworlds.common.recipes.burn;

import com.google.gson.JsonObject;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import java.util.Objects;

public class BurnRecipeSerializer implements RecipeSerializer<IBurnRecipe> {
    @Nonnull
    @Override
    public BurnRecipe fromJson(@Nonnull ResourceLocation recipeId, @Nonnull JsonObject json) {
        String inputId = GsonHelper.getAsString(json, "input");
        String outputId = GsonHelper.getAsString(json, "output");
        Block output = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(outputId));
        return new BurnRecipe(recipeId, inputId, output);
    }

    @Nullable
    @Override
    public BurnRecipe fromNetwork(@Nonnull ResourceLocation recipeId, FriendlyByteBuf buffer) {
        String inputId = buffer.readUtf();
        String outputId = buffer.readUtf();
        Block output = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(outputId));
        return new BurnRecipe(recipeId, inputId, output);
    }

    @Override
    public void toNetwork(FriendlyByteBuf buffer, IBurnRecipe recipe) {
        buffer.writeUtf(recipe.getInputTag());
        buffer.writeUtf(Objects.requireNonNull(ForgeRegistries.BLOCKS.getKey(recipe.getOutput())).toString());
    }
}