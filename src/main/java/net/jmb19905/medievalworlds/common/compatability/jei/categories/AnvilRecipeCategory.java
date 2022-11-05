package net.jmb19905.medievalworlds.common.compatability.jei.categories;

import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import net.jmb19905.medievalworlds.common.compatability.jei.MWJEIRecipeTypes;
import net.jmb19905.medievalworlds.common.item.ForgeHammerItem;
import net.jmb19905.medievalworlds.common.recipes.AnvilRecipe;
import net.jmb19905.medievalworlds.common.registries.VanillaOverride;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;

public class AnvilRecipeCategory extends MWRecipeCategory<AnvilRecipe> {

    public AnvilRecipeCategory(IGuiHelper helper) {
        super(VanillaOverride.ANVIL.get(), MWJEIRecipeTypes.ANVIL, 0, 109, 76, 18, helper);
    }

    @Override
    public void setRecipe(@NotNull IRecipeLayoutBuilder builder, @NotNull AnvilRecipe recipe, @NotNull IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT, 1,1).addItemStack(recipe.getInput());
        builder.addSlot(RecipeIngredientRole.OUTPUT, 59, 1).addItemStack(recipe.getResultItem());
        List<Item> items = ForgeRegistries.ITEMS.getEntries().stream().map(Map.Entry::getValue).filter(item -> item instanceof ForgeHammerItem).toList();
        Ingredient hammerIngredient = Ingredient.of(items.toArray(new Item[0]));
        builder.addSlot(RecipeIngredientRole.RENDER_ONLY, 30, 1).addIngredients(hammerIngredient);
    }
}