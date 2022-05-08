package net.jmb19905.medievalworlds.core.compatability.jei;

import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.jmb19905.medievalworlds.MedievalWorlds;
import net.jmb19905.medievalworlds.common.recipes.fletching.IFletchingRecipe;
import net.jmb19905.medievalworlds.core.registries.VanillaReplacements;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("removal")
public class FletchingRecipeCategory implements IRecipeCategory<IFletchingRecipe> {
    public static final ResourceLocation TEXTURE = new ResourceLocation(MedievalWorlds.MOD_ID, "textures/gui/jei.png");

    private final IDrawable background;
    private final IDrawable icon;

    public FletchingRecipeCategory(IGuiHelper helper) {
        this.background = helper.createDrawable(TEXTURE, 0, 0, 90, 56);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM, new ItemStack(VanillaReplacements.FLETCHING_TABLE_ITEM.get()));
    }

    @Override
    public @NotNull Component getTitle() {
        return new TranslatableComponent("container.medievalworlds.fletching");
    }

    @Override
    public @NotNull IDrawable getBackground() {
        return background;
    }

    @Override
    public @NotNull IDrawable getIcon() {
        return icon;
    }

    @Override
    public @NotNull ResourceLocation getUid() {
        return IFletchingRecipe.RECIPE_TYPE_ID;
    }

    @Override
    public @NotNull Class<? extends IFletchingRecipe> getRecipeClass() {
        return IFletchingRecipe.class;
    }

    @Override
    public void setRecipe(@NotNull IRecipeLayoutBuilder builder, @NotNull IFletchingRecipe recipe, @NotNull IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.OUTPUT, 116, 33).addItemStack(recipe.getResultItem());
        builder.addSlot(RecipeIngredientRole.INPUT, 44, 14).addIngredients(recipe.getInput1());
        builder.addSlot(RecipeIngredientRole.INPUT, 44, 33).addIngredients(recipe.getInput2());
        builder.addSlot(RecipeIngredientRole.INPUT, 44, 52).addIngredients(recipe.getInput3());
    }
}
