package net.jmb19905.medievalworlds.common.recipes;

import com.google.gson.JsonObject;
import net.jmb19905.medievalworlds.MedievalWorlds;
import net.jmb19905.medievalworlds.common.registries.MWRecipeSerializers;
import net.minecraft.core.NonNullList;
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
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class AlloyRecipe implements Recipe<RecipeWrapper> {

    public static final ResourceLocation ID = new ResourceLocation(MedievalWorlds.MOD_ID, "alloy");

    private final ResourceLocation id;
    private final ItemStack input1;
    private final ItemStack input2;
    private final ItemStack output;

    public AlloyRecipe(ResourceLocation id, ItemStack input1, ItemStack input2, ItemStack output) {
        this.id = id;
        this.input1 = input1;
        this.input2 = input2;
        this.output = output;
    }

    public boolean matchesUniversally(RecipeWrapper inv, @Nonnull Level worldIn, boolean flipped){
        if(flipped){
            return matchesFlipped(inv);
        }else{
            return matches(inv, worldIn);
        }
    }

    public boolean matchesNormal(RecipeWrapper inv){
        ItemStack stackInSlot0 = inv.getItem(0);
        ItemStack stackInSlot1 = inv.getItem(1);
        return (stackInSlot0.getItem() == input1.getItem() && stackInSlot0.getCount() >= input1.getCount() &&
                stackInSlot1.getItem() == input2.getItem() && stackInSlot1.getCount() >= input2.getCount());
    }

    public boolean matchesFlipped(RecipeWrapper inv){
        ItemStack stackInSlot0 = inv.getItem(1);
        ItemStack stackInSlot1 = inv.getItem(0);
        return (stackInSlot0.getItem() == input1.getItem() && stackInSlot0.getCount() >= input1.getCount() &&
                stackInSlot1.getItem() == input2.getItem() && stackInSlot1.getCount() >= input2.getCount());
    }

    @Override
    public boolean matches(@NotNull RecipeWrapper inv, @Nonnull Level worldIn) {
        return matchesNormal(inv) || matchesFlipped(inv);
    }

    @Nonnull
    @Override
    public ItemStack assemble(@Nonnull RecipeWrapper inv) {
        return this.output.copy();
    }

    public ItemStack getInput1() {
        return this.input1;
    }

    public ItemStack getInput2() {
        return this.input2;
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

    public NonNullList<ItemStack> getInputs() {
        return NonNullList.of(this.input1, this.input2);
    }

    @Nonnull
    @Override
    public RecipeType<?> getType(){
        return MWRecipeSerializers.ALLOY_TYPE;
    }

    @Nonnull
    @Override
    public RecipeSerializer<?> getSerializer() {
        return MWRecipeSerializers.ALLOY_SERIALIZER.get();
    }

    @Override
    public boolean canCraftInDimensions(int rows, int columns){
        return false;
    }

    public static class Serializer implements RecipeSerializer<AlloyRecipe> {
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
        public void toNetwork(FriendlyByteBuf buffer, AlloyRecipe recipe) {
            buffer.writeItemStack(recipe.getResultItem(), false);
            buffer.writeItemStack(recipe.getInput1(), true);
            buffer.writeItemStack(recipe.getInput2(), true);
        }
    }
}