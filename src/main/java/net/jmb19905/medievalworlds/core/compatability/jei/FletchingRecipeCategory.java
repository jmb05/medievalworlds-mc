package net.jmb19905.medievalworlds.core.compatability.jei;

import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import net.jmb19905.medievalworlds.common.recipes.fletching.IFletchingRecipe;
import net.jmb19905.medievalworlds.core.registries.VanillaReplacements;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import org.jetbrains.annotations.NotNull;

public class FletchingRecipeCategory extends MWRecipeCategory<IFletchingRecipe> {

    public FletchingRecipeCategory(IGuiHelper helper) {
        super(VanillaReplacements.FLETCHING_TABLE.get(), 0, 0, 90, 56, helper);
    }
    @Override
    public @NotNull RecipeType<IFletchingRecipe> getRecipeType() {
        return MWJEIRecipeTypes.FLETCHING;
    }

    @Override
    public void setRecipe(@NotNull IRecipeLayoutBuilder builder, @NotNull IFletchingRecipe recipe, @NotNull IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.OUTPUT, 73, 20).addItemStack(recipe.getResultItem());
        builder.addSlot(RecipeIngredientRole.INPUT, 1, 1).addIngredients(recipe.getInput1());
        builder.addSlot(RecipeIngredientRole.INPUT, 1, 20).addIngredients(recipe.getInput2());
        builder.addSlot(RecipeIngredientRole.INPUT, 1, 39).addIngredients(recipe.getInput3());
    }
}
