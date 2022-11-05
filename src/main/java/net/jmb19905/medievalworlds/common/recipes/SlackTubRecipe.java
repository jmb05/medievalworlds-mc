package net.jmb19905.medievalworlds.common.recipes;

import com.google.gson.JsonObject;
import net.jmb19905.medievalworlds.MedievalWorlds;
import net.jmb19905.medievalworlds.common.registries.MWRecipeSerializers;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.items.wrapper.RecipeWrapper;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class SlackTubRecipe implements Recipe<RecipeWrapper> {

    public static ResourceLocation ID = new ResourceLocation(MedievalWorlds.MOD_ID, "slack_tub");

    private final ResourceLocation id;
    private final Ingredient input;
    private final int inputCount;
    private final ItemStack output;

    public SlackTubRecipe(ResourceLocation id, Ingredient input, int inputCount, ItemStack output) {
        this.id = id;
        this.input = input;
        this.inputCount = inputCount;
        this.output = output;
    }

    @Override
    public boolean matches(@NotNull RecipeWrapper wrapper, @NotNull Level level) {
        return false;
    }

    @Override
    public @NotNull ItemStack assemble(@NotNull RecipeWrapper wrapper) {
        return output.copy();
    }

    public Ingredient getInput() {
        return input;
    }

    public int getInputCount() {
        return inputCount;
    }

    @Override
    public @NotNull ItemStack getResultItem() {
        return output;
    }

    @Override
    public @NotNull ResourceLocation getId() {
        return id;
    }

    @Override
    public @NotNull RecipeSerializer<?> getSerializer() {
        return MWRecipeSerializers.SLACK_TUB_SERIALIZER.get();
    }

    @Nonnull
    @Override
    public RecipeType<?> getType(){
        return MWRecipeSerializers.SLACK_TUB_TYPE;
    }

    @Override
    public boolean canCraftInDimensions(int rows, int columns){
        return false;
    }

    public static class Serializer implements RecipeSerializer<SlackTubRecipe> {
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

}