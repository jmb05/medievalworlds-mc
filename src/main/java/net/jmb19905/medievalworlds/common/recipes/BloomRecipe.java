package net.jmb19905.medievalworlds.common.recipes;

import com.google.gson.JsonElement;
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

public class BloomRecipe implements Recipe<RecipeWrapper> {

    public static final ResourceLocation ID = new ResourceLocation(MedievalWorlds.MOD_ID, "bloom");

    private final ResourceLocation id;
    private final Ingredient input;
    private final ItemStack primaryOutput;
    private final ItemStack primaryPacked;//The count from this ItemStack is the amount of output needed for one packed
    private final int primaryOffset;
    private final ItemStack secondaryOutput;
    private final ItemStack secondaryPacked;//The count from this ItemStack is the amount of output needed for one packed
    private final int secondaryOffset;

    public BloomRecipe(ResourceLocation id, Ingredient input, ItemStack primaryOutput, ItemStack primaryPacked, int primaryOffset, ItemStack secondaryOutput, ItemStack secondaryPacked, int secondaryOffset){
        this.id = id;
        this.input = input;
        this.primaryOutput = primaryOutput;
        this.primaryPacked = primaryPacked;
        this.primaryOffset = primaryOffset;
        this.secondaryOutput = secondaryOutput;
        this.secondaryPacked = secondaryPacked;
        this.secondaryOffset = secondaryOffset;
    }

    public Ingredient getInput() {
        return input;
    }

    public ItemStack getPrimaryOutput() {
        return primaryOutput;
    }

    public ItemStack getPrimaryOutputPacked() {
        return primaryPacked;
    }

    public int getPrimaryOffset() {
        return primaryOffset;
    }

    public ItemStack getSecondaryOutput() {
        return secondaryOutput;
    }

    public ItemStack getSecondaryOutputPacked() {
        return secondaryPacked;
    }

    public int getSecondaryOffset() {
        return secondaryOffset;
    }

    @Override
    public boolean matches(RecipeWrapper inv, @Nonnull Level level) {
        ItemStack stackInInputSlot = inv.getItem(0);
        return input.test(stackInInputSlot);
    }

    @Nonnull
    @Override
    public ItemStack assemble(@Nonnull RecipeWrapper inv) {
        return this.primaryOutput.copy();
    }

    @Nonnull
    @Override
    public ItemStack getResultItem() {
        return this.primaryOutput;
    }

    @Nonnull
    @Override
    public ResourceLocation getId() {
        return this.id;
    }

    @Nonnull
    @Override
    public RecipeSerializer<?> getSerializer() {
        return MWRecipeSerializers.BLOOM_SERIALIZER.get();
    }

    @Nonnull
    @Override
    public RecipeType<?> getType(){
        return MWRecipeSerializers.BLOOM_TYPE;
    }

    @Override
    public boolean canCraftInDimensions(int rows, int columns){
        return false;
    }

    public static class Serializer implements RecipeSerializer<BloomRecipe> {

        @Nonnull
        @Override
        public BloomRecipe fromJson(@Nonnull ResourceLocation recipeId, @Nonnull JsonObject json) {
            JsonElement jsonelement = GsonHelper.isArrayNode(json, "input") ? GsonHelper.getAsJsonArray(json, "input") : GsonHelper.getAsJsonObject(json, "input");
            Ingredient input = Ingredient.fromJson(jsonelement);
            ItemStack primaryOutput = CraftingHelper.getItemStack(GsonHelper.getAsJsonObject(json, "primaryOutput"), true);
            ItemStack primaryPacked = CraftingHelper.getItemStack(GsonHelper.getAsJsonObject(json, "primaryPacked"), true);
            int primaryOffset = GsonHelper.getAsInt(json, "primaryOffset");
            ItemStack secondaryOutput = CraftingHelper.getItemStack(GsonHelper.getAsJsonObject(json, "secondaryOutput"), true);
            ItemStack secondaryPacked = CraftingHelper.getItemStack(GsonHelper.getAsJsonObject(json, "secondaryPacked"), true);
            int secondaryOffset = GsonHelper.getAsInt(json, "secondaryOffset");
            return new BloomRecipe(recipeId, input, primaryOutput, primaryPacked, primaryOffset, secondaryOutput, secondaryPacked, secondaryOffset);
        }

        @Nullable
        @Override
        public BloomRecipe fromNetwork(@Nonnull ResourceLocation recipeId, @NotNull FriendlyByteBuf buffer) {
            Ingredient input = Ingredient.fromNetwork(buffer);
            ItemStack primary = buffer.readItem();
            ItemStack primaryPacked = buffer.readItem();
            int primaryOffset = buffer.readInt();
            ItemStack secondary = buffer.readItem();
            ItemStack secondaryPacked = buffer.readItem();
            int secondaryOffset = buffer.readInt();
            return new BloomRecipe(recipeId, input, primary, primaryPacked, primaryOffset, secondary, secondaryPacked, secondaryOffset);
        }

        @Override
        public void toNetwork(@NotNull FriendlyByteBuf buffer, BloomRecipe recipe) {
            recipe.getInput().toNetwork(buffer);
            buffer.writeItemStack(recipe.getPrimaryOutput(), true);
            buffer.writeItemStack(recipe.getPrimaryOutputPacked(), true);
            buffer.writeInt(recipe.getPrimaryOffset());
            buffer.writeItemStack(recipe.getSecondaryOutput(), true);
            buffer.writeItemStack(recipe.getSecondaryOutputPacked(), true);
            buffer.writeInt(recipe.getSecondaryOffset());
        }
    }

}