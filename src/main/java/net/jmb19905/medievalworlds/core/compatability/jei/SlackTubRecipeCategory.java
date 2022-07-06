package net.jmb19905.medievalworlds.core.compatability.jei;

import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import net.jmb19905.medievalworlds.common.recipes.slackTub.ISlackTubRecipe;
import net.jmb19905.medievalworlds.core.registries.MWBlocks;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class SlackTubRecipeCategory extends MWRecipeCategory<ISlackTubRecipe> {

    public SlackTubRecipeCategory(IGuiHelper helper) {
        super(MWBlocks.SLACK_TUB_BLOCK.get(), 0, 109, 76, 18, helper);
    }

    @Override
    public @NotNull RecipeType<ISlackTubRecipe> getRecipeType() {
        return MWJEIRecipeTypes.SLACK_TUB;
    }

    @Override
    public void setRecipe(@NotNull IRecipeLayoutBuilder builder, @NotNull ISlackTubRecipe recipe, @NotNull IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT, 1,1).addIngredients(recipe.getInput());
        builder.addSlot(RecipeIngredientRole.OUTPUT, 59, 1).addItemStack(recipe.getResultItem());
        builder.addSlot(RecipeIngredientRole.RENDER_ONLY, 30, 1).addItemStack(new ItemStack(MWBlocks.SLACK_TUB_BLOCK.get()));
    }
}
