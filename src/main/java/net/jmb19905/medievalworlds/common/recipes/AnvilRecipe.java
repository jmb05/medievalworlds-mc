package net.jmb19905.medievalworlds.common.recipes;

import com.google.gson.JsonObject;
import net.jmb19905.medievalworlds.MedievalWorlds;
import net.jmb19905.medievalworlds.common.registries.MWRecipeSerializers;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.items.wrapper.RecipeWrapper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class AnvilRecipe implements Recipe<RecipeWrapper> {

    public static final ResourceLocation ID = new ResourceLocation(MedievalWorlds.MOD_ID, "anvil");

    private final ResourceLocation id;
    private final ItemStack input;
    private final ItemStack output;
    private final int minAnvilLevel;

    public AnvilRecipe(ResourceLocation id, ItemStack input, ItemStack output, int minAnvilLevel) {
        this.id = id;
        this.input = input;
        this.output = output;
        this.minAnvilLevel = minAnvilLevel;
    }

    public ItemStack getDamageScaledResult(ItemStack input) {
        ItemStack result = getResultItem().copy();
        if (input.isDamageableItem()) {
            float maxDamage = input.getMaxDamage();
            float damage = input.getDamageValue();
            float scaled = 1f - (damage / maxDamage);
            float scaledCount = result.getCount() * scaled;
            int newCount = Math.max(Math.round(scaledCount), 1);
            result.setCount(newCount);
        }
        return result;
    }

    public ItemStack getInput() {
        return input;
    }

    public int getMinAnvilLevel() {
        return minAnvilLevel;
    }

    public boolean matches(ItemStack input) {
        return this.input.getItem() == input.getItem() && this.input.getCount() <= input.getCount();
    }

    @Override
    public boolean matches(@Nonnull RecipeWrapper invWrapper, @Nonnull Level level) {
        return false;
    }

    @Nonnull
    @Override
    public ItemStack assemble(@Nonnull RecipeWrapper invWrapper) {
        return this.output.copy();
    }

    @Nonnull
    @Override
    public ItemStack getResultItem() {
        return this.output;
    }

    @Nonnull
    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Nonnull
    @Override
    public RecipeSerializer<?> getSerializer() {
        return MWRecipeSerializers.ANVIL_SERIALIZER.get();
    }

    @Nonnull
    @Override
    public RecipeType<?> getType(){
        return MWRecipeSerializers.ANVIL_TYPE;
    }

    @Override
    public boolean canCraftInDimensions(int rows, int columns){
        return false;
    }

    public static class Serializer implements RecipeSerializer<AnvilRecipe> {

        @Nonnull
        @Override
        public AnvilRecipe fromJson(@Nonnull ResourceLocation recipeId, @Nonnull JsonObject json) {
            ItemStack output = CraftingHelper.getItemStack(GsonHelper.getAsJsonObject(json, "output"), true);
            ItemStack input = CraftingHelper.getItemStack(GsonHelper.getAsJsonObject(json, "input"), true);
            int minAnvilLevel = GsonHelper.getAsInt(json, "minAnvilLevel");

            return new AnvilRecipe(recipeId, input, output, minAnvilLevel);
        }

        @Nullable
        @Override
        public AnvilRecipe fromNetwork(@Nonnull ResourceLocation recipeId, FriendlyByteBuf buffer) {
            ItemStack output = buffer.readItem();
            ItemStack input = buffer.readItem();
            int minAnvilLevel = buffer.readInt();

            return new AnvilRecipe(recipeId, input, output, minAnvilLevel);
        }

        @Override
        public void toNetwork(@Nonnull FriendlyByteBuf buffer, @Nonnull AnvilRecipe recipe) {
            buffer.writeItemStack(recipe.getResultItem(), false);
            buffer.writeItemStack(recipe.getInput(), false);
            buffer.writeInt(recipe.getMinAnvilLevel());
        }
    }

}
