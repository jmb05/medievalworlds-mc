package net.jmb19905.medievalworlds.common.recipes;

import com.google.gson.JsonObject;
import net.jmb19905.medievalworlds.MedievalWorlds;
import net.jmb19905.medievalworlds.common.registries.MWRecipeSerializers;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraftforge.items.wrapper.RecipeWrapper;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Objects;

public class ScrapRecipe implements Recipe<RecipeWrapper> {

    public static final ResourceLocation ID = new ResourceLocation(MedievalWorlds.MOD_ID, "scrap");

    private final ResourceLocation id;
    private final Item input;
    private final Item remainder;
    private final Item output;

    public ScrapRecipe(ResourceLocation id, Item input, Item remainder, Item output) {
        this.id = id;
        this.input = input;
        this.remainder = remainder;
        this.output = output;
    }

    public Item getInput() {
        return input;
    }

    public Item getRemainder() {
        return remainder;
    }

    @Override
    public boolean matches(@NotNull RecipeWrapper wrapper, @NotNull Level level) {
        return false;
    }

    @Override
    public @NotNull ItemStack assemble(@NotNull RecipeWrapper wrapper) {
        return getResultItem().copy();
    }

    @Override
    public @NotNull ItemStack getResultItem() {
        return new ItemStack(output);
    }

    @Override
    public @NotNull ResourceLocation getId() {
        return id;
    }

    @Nonnull
    @Override
    public RecipeType<?> getType(){
        return MWRecipeSerializers.SCRAP_TYPE;
    }

    @Nonnull
    @Override
    public RecipeSerializer<?> getSerializer() {
        return MWRecipeSerializers.SCRAP_SERIALIZER.get();
    }

    @Override
    public boolean canCraftInDimensions(int rows, int columns){
        return false;
    }

    public static class Serializer implements RecipeSerializer<ScrapRecipe> {

        @Nonnull
        @Override
        public ScrapRecipe fromJson(@Nonnull ResourceLocation recipeId, @Nonnull JsonObject json) {
            Item output = ForgeRegistries.ITEMS.getValue(new ResourceLocation(GsonHelper.getAsString(json, "output")));
            Item input = ForgeRegistries.ITEMS.getValue(new ResourceLocation(GsonHelper.getAsString(json, "input")));
            Item remainder = ForgeRegistries.ITEMS.getValue(new ResourceLocation(GsonHelper.getAsString(json, "remainder")));

            return new ScrapRecipe(recipeId, input, remainder, output);
        }

        @Nullable
        @Override
        public ScrapRecipe fromNetwork(@Nonnull ResourceLocation recipeId, FriendlyByteBuf buffer) {
            Item output = ForgeRegistries.ITEMS.getValue(new ResourceLocation(buffer.readUtf()));
            Item input = ForgeRegistries.ITEMS.getValue(new ResourceLocation(buffer.readUtf()));
            Item remainder = ForgeRegistries.ITEMS.getValue(new ResourceLocation(buffer.readUtf()));

            return new ScrapRecipe(recipeId, input, remainder, output);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, ScrapRecipe recipe) {
            buffer.writeUtf(Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(recipe.getResultItem().getItem())).toString());
            buffer.writeUtf(Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(recipe.getInput())).toString());
            buffer.writeUtf(Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(recipe.getRemainder())).toString());
        }
    }


}
