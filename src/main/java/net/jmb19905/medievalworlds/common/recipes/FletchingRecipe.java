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
import org.jetbrains.annotations.Nullable;

public class FletchingRecipe implements Recipe<RecipeWrapper> {

    public static final ResourceLocation ID = new ResourceLocation(MedievalWorlds.MOD_ID, "fletching");

    private final ResourceLocation id;
    private final Ingredient arrowHead;
    private final Ingredient arrowShaft;
    private final Ingredient arrowFletching;

    private final ItemStack result;

    public FletchingRecipe(ResourceLocation id, Ingredient arrowHead, Ingredient arrowShaft, Ingredient arrowFletching, ItemStack result) {
        this.id = id;
        this.arrowHead = arrowHead;
        this.arrowShaft = arrowShaft;
        this.arrowFletching = arrowFletching;
        this.result = result;
    }

    public Ingredient getArrowHead() {
        return arrowHead;
    }

    public Ingredient getArrowShaft() {
        return arrowShaft;
    }

    public Ingredient getArrowFletching() {
        return arrowFletching;
    }

    @Override
    public boolean matches(@NotNull RecipeWrapper inv, @NotNull Level level) {
        ItemStack headStack = inv.getItem(0);
        ItemStack shaftStack = inv.getItem(1);
        ItemStack fletchingStack = inv.getItem(2);
        return arrowHead.test(headStack) && arrowShaft.test(shaftStack) && arrowFletching.test(fletchingStack);
    }

    @Override
    public @NotNull ItemStack assemble(@NotNull RecipeWrapper inv) {
        return this.result.copy();
    }

    @Override
    public boolean canCraftInDimensions(int rows, int columns) {
        return rows >= 1 && columns >= 3;
    }

    @Override
    public @NotNull ItemStack getResultItem() {
        return result;
    }

    @Override
    public @NotNull ResourceLocation getId() {
        return id;
    }
    @Override
    @NotNull
    public RecipeType<?> getType() {
        return MWRecipeSerializers.FLETCHING_TYPE;
    }

    @Override
    @NotNull
    public RecipeSerializer<?> getSerializer(){
        return MWRecipeSerializers.FLETCHING_SERIALIZER.get();
    }

    public static class Serializer implements RecipeSerializer<FletchingRecipe> {
        @Override
        @NotNull
        public FletchingRecipe fromJson(@NotNull ResourceLocation id, @NotNull JsonObject json) {
            Ingredient arrowHead = CraftingHelper.getIngredient(GsonHelper.getAsJsonObject(json, "head"));
            Ingredient arrowShaft = CraftingHelper.getIngredient(GsonHelper.getAsJsonObject(json, "shaft"));
            Ingredient arrowFletching = CraftingHelper.getIngredient(GsonHelper.getAsJsonObject(json, "fletching"));
            ItemStack result = CraftingHelper.getItemStack(GsonHelper.getAsJsonObject(json, "result"), true);

            return new FletchingRecipe(id, arrowHead, arrowShaft, arrowFletching, result);
        }

        @Nullable
        @Override
        public FletchingRecipe fromNetwork(@NotNull ResourceLocation id, @NotNull FriendlyByteBuf buffer) {
            Ingredient arrowHead = Ingredient.fromNetwork(buffer);
            Ingredient arrowShaft = Ingredient.fromNetwork(buffer);
            Ingredient arrowFletching = Ingredient.fromNetwork(buffer);
            ItemStack result = buffer.readItem();
            return new FletchingRecipe(id, arrowHead, arrowShaft, arrowFletching, result);
        }

        @Override
        public void toNetwork(@NotNull FriendlyByteBuf buffer, @NotNull FletchingRecipe recipe) {
            recipe.getArrowHead().toNetwork(buffer);
            recipe.getArrowShaft().toNetwork(buffer);
            recipe.getArrowFletching().toNetwork(buffer);
            buffer.writeItemStack(recipe.getResultItem(), false);
        }
    }


}