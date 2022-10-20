package net.jmb19905.medievalworlds.common.recipes.slacktub;

import net.jmb19905.medievalworlds.common.registries.MWRecipeSerializers;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;
import net.minecraftforge.items.wrapper.RecipeWrapper;
import org.jetbrains.annotations.NotNull;

public class SlackTubRecipe implements ISlackTubRecipe{

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

    @Override
    public boolean canCraftInDimensions(int x, int y) {
        return false;
    }

    @Override
    public Ingredient getInput() {
        return input;
    }

    @Override
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
}