package net.jmb19905.medievalworlds.core.compatability.jei;

import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import net.jmb19905.medievalworlds.common.recipes.smithing.ISmithingRecipe;
import net.jmb19905.medievalworlds.core.registries.VanillaReplacements;
import org.jetbrains.annotations.NotNull;

public class CustomSmithingRecipeCategory extends MWRecipeCategory<ISmithingRecipe> {

    public CustomSmithingRecipeCategory(IGuiHelper helper) {
        super(VanillaReplacements.SMITHING_TABLE.get(), 0, 127, 125, 18, helper);
    }
    @Override
    public @NotNull RecipeType<ISmithingRecipe> getRecipeType() {
        return MWJEIRecipeTypes.SMITHING;
    }

    @Override
    public void setRecipe(@NotNull IRecipeLayoutBuilder builder, @NotNull ISmithingRecipe recipe, @NotNull IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT, 1, 1)
                .addItemStack(recipe.getInput());

        builder.addSlot(RecipeIngredientRole.INPUT, 50, 1)
                .addItemStack(recipe.getAddition());

        builder.addSlot(RecipeIngredientRole.OUTPUT, 108, 1)
                .addItemStack(recipe.getResultItem());
    }
}
