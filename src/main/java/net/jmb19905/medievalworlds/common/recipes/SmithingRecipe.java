package net.jmb19905.medievalworlds.common.recipes;

import com.google.gson.JsonObject;
import net.jmb19905.medievalworlds.MedievalWorlds;
import net.jmb19905.medievalworlds.common.registries.MWRecipeSerializers;
import net.minecraft.nbt.CompoundTag;
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
import org.jetbrains.annotations.Nullable;

public class SmithingRecipe implements Recipe<RecipeWrapper> {

    public static final ResourceLocation ID = new ResourceLocation(MedievalWorlds.MOD_ID, "smithing");

    private final ResourceLocation id;
    private final ItemStack input;
    private final ItemStack addition;
    private final ItemStack result;

    private final int cost;

    public SmithingRecipe(ResourceLocation id, ItemStack input, ItemStack addition, ItemStack result, int cost) {
        this.id = id;
        this.input = input;
        this.addition = addition;
        this.result = result;
        this.cost = cost;
    }

    public ItemStack getInput() {
        return input;
    }

    public ItemStack getAddition() {
        return addition;
    }

    public int getCost() {
        return cost;
    }

    @Override
    public boolean matches(@NotNull RecipeWrapper container, @NotNull Level level) {
        if(input.isEmpty() || addition.isEmpty()) return false;
        ItemStack inputSlotItem = container.getItem(0);
        ItemStack additionSlot = container.getItem(1);
        return inputSlotItem.getItem() == input.getItem() && inputSlotItem.getCount() >= input.getCount()
                && additionSlot.getItem() == addition.getItem() && additionSlot.getCount() >= addition.getCount();
    }

    @NotNull
    @Override
    public ItemStack assemble(RecipeWrapper container) {
        ItemStack itemstack = this.result.copy();
        CompoundTag compoundtag = container.getItem(0).getTag();
        if (compoundtag != null) {
            itemstack.setTag(compoundtag.copy());
        }

        return itemstack;
    }

    public boolean isAdditionIngredient(ItemStack stack) {
        return addition.getItem() == stack.getItem();
    }

    @NotNull
    @Override
    public ItemStack getResultItem() {
        return result;
    }

    @NotNull
    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return width * height >= 2;
    }
    @Override
    @NotNull
    public RecipeType<?> getType() {
        return MWRecipeSerializers.SMITHING_TYPE;
    }

    @Override
    @NotNull
    public RecipeSerializer<?> getSerializer(){
        return MWRecipeSerializers.SMITHING_SERIALIZER.get();
    }

    public static class Serializer implements RecipeSerializer<SmithingRecipe>{
        @Override
        @NotNull
        public SmithingRecipe fromJson(@NotNull ResourceLocation id, @NotNull JsonObject json) {
            ItemStack input = CraftingHelper.getItemStack(GsonHelper.getAsJsonObject(json, "base"), true);
            ItemStack addition = CraftingHelper.getItemStack(GsonHelper.getAsJsonObject(json, "addition"), true);
            ItemStack result = CraftingHelper.getItemStack(GsonHelper.getAsJsonObject(json, "result"), true);
            int cost = GsonHelper.getAsInt(json, "cost");

            return new SmithingRecipe(id, input, addition, result, cost);
        }

        @Nullable
        @Override
        public SmithingRecipe fromNetwork(@NotNull ResourceLocation id, @NotNull FriendlyByteBuf buffer) {
            ItemStack input = buffer.readItem();
            ItemStack addition = buffer.readItem();
            ItemStack result = buffer.readItem();
            int cost = buffer.readInt();
            return new SmithingRecipe(id, input, addition, result, cost);
        }

        @Override
        public void toNetwork(@NotNull FriendlyByteBuf buffer, @NotNull SmithingRecipe recipe) {
            buffer.writeItemStack(recipe.getInput(), true);
            buffer.writeItemStack(recipe.getAddition(), true);
            buffer.writeItemStack(recipe.getResultItem(), true);
            buffer.writeInt(recipe.getCost());
        }
    }

}