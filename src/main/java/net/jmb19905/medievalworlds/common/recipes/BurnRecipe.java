package net.jmb19905.medievalworlds.common.recipes;

import com.google.gson.JsonObject;
import net.jmb19905.medievalworlds.MedievalWorlds;
import net.jmb19905.medievalworlds.common.registries.MWRecipeSerializers;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.items.wrapper.RecipeWrapper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.tags.ITagManager;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import java.util.Objects;

public class BurnRecipe implements Recipe<RecipeWrapper> {

    public static ResourceLocation ID = new ResourceLocation(MedievalWorlds.MOD_ID, "burn");

    private final ResourceLocation id;
    private final String inputTag;
    private final Block output;

    public BurnRecipe(ResourceLocation id, String inputTag, Block output) {
        this.id = id;
        this.inputTag = inputTag;
        this.output = output;
    }

    public String getInputTag() {
        return inputTag;
    }

    public Block getOutput() {
        return output;
    }

    @Override
    public boolean matches(@NotNull RecipeWrapper wrapper, @NotNull Level level) {
        return false;
    }

    public boolean matches(BlockState blockState) {
        ITagManager<Block> tagManager = ForgeRegistries.BLOCKS.tags();
        if (tagManager == null) return false;
        TagKey<Block> tagKey = tagManager.getTagNames()
                .filter(blockTagKey -> blockTagKey.location().equals(new ResourceLocation(inputTag)))
                .findFirst()
                .orElse(null);
        if (tagKey == null) return false;
        return blockState.is(tagKey);
    }

    @Nonnull
    @Override
    public ItemStack assemble(@NotNull RecipeWrapper wrapper) {
        return ItemStack.EMPTY;
    }

    @Nonnull
    @Override
    public ItemStack getResultItem() {
        return ItemStack.EMPTY;
    }

    @Nonnull
    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Nonnull
    @Override
    public RecipeType<?> getType() {
        return MWRecipeSerializers.BURN_TYPE;
    }

    @Nonnull
    @Override
    public RecipeSerializer<?> getSerializer() {
        return MWRecipeSerializers.BURN_SERIALIZER.get();
    }

    @Override
    public boolean canCraftInDimensions(int rows, int columns) {
        return false;
    }

    public static class Serializer implements RecipeSerializer<BurnRecipe> {
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
        public void toNetwork(FriendlyByteBuf buffer, BurnRecipe recipe) {
            buffer.writeUtf(recipe.getInputTag());
            buffer.writeUtf(Objects.requireNonNull(ForgeRegistries.BLOCKS.getKey(recipe.getOutput())).toString());
        }
    }
}
