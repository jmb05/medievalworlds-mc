package net.jmb19905.medievalworlds.core.compatability.jei;

import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import net.jmb19905.medievalworlds.common.recipes.bloom.IBloomRecipe;
import net.jmb19905.medievalworlds.core.registries.MWBlocks;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.NotNull;

public class BloomRecipeCategory extends MWRecipeCategory<IBloomRecipe>{
    public BloomRecipeCategory(IGuiHelper helper) {
        super(MWBlocks.BLOOMERY.get(), 0, 145, 91, 52, helper);
    }

    @Override
    public @NotNull RecipeType<IBloomRecipe> getRecipeType() {
        return MWJEIRecipeTypes.BLOOM;
    }

    @Override
    public void setRecipe(@NotNull IRecipeLayoutBuilder builder, @NotNull IBloomRecipe recipe, @NotNull IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT, 1,1).addIngredients(recipe.getInput());
        builder.addSlot(RecipeIngredientRole.CATALYST, 1, 35).addItemStack(new ItemStack(Items.CHARCOAL));

        builder.addSlot(RecipeIngredientRole.OUTPUT, 61, 5).addItemStack(recipe.getPrimaryOutput());
        builder.addSlot(RecipeIngredientRole.OUTPUT, 61, 31).addItemStack(recipe.getSecondaryOutput());
    }
}
