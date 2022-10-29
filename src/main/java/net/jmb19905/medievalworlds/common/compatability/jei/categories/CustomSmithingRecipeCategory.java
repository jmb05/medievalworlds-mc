package net.jmb19905.medievalworlds.common.compatability.jei.categories;

import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import net.jmb19905.medievalworlds.common.compatability.jei.MWJEIRecipeTypes;
import net.jmb19905.medievalworlds.common.recipes.smithing.ISmithingRecipe;
import net.jmb19905.medievalworlds.common.registries.VanillaOverride;
import org.jetbrains.annotations.NotNull;

public class CustomSmithingRecipeCategory extends MWRecipeCategory<ISmithingRecipe> {

    public CustomSmithingRecipeCategory(IGuiHelper helper) {
        super(VanillaOverride.SMITHING_TABLE.get(), MWJEIRecipeTypes.SMITHING, 0, 127, 125, 18, helper);
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