package net.jmb19905.medievalworlds.common.compatability.jei.categories;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.mojang.blaze3d.vertex.PoseStack;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.drawable.IDrawableStatic;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import net.jmb19905.medievalworlds.common.compatability.jei.MWJEIRecipeTypes;
import net.jmb19905.medievalworlds.common.recipes.AlloyRecipe;
import net.jmb19905.medievalworlds.common.registries.MWBlocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;

public class AlloyRecipeCategory extends MWRecipeCategory<AlloyRecipe> {
    protected final IDrawableStatic staticFlame;
    protected final IDrawableAnimated animatedFlame;
    private final LoadingCache<Integer, IDrawableAnimated> cachedArrows;

    public AlloyRecipeCategory(IGuiHelper helper) {
        super(MWBlocks.ALLOY_FURNACE.get(), MWJEIRecipeTypes.ALLOY, 0, 56, 118, 53, helper);
        staticFlame = helper.createDrawable(TEXTURE, 118, 56, 14, 14);
        animatedFlame = helper.createAnimatedDrawable(staticFlame, 298, IDrawableAnimated.StartDirection.TOP, true);
        this.cachedArrows = CacheBuilder.newBuilder()
                .maximumSize(25)
                .build(new CacheLoader<>() {
                    @Override
                    public @NotNull IDrawableAnimated load(@NotNull Integer cookTime) {
                        return helper.drawableBuilder(TEXTURE, 118, 70, 24, 17)
                                .buildAnimated(cookTime, IDrawableAnimated.StartDirection.LEFT, false);
                    }
                });
    }

    @Override
    public void draw(@NotNull AlloyRecipe recipe, @NotNull IRecipeSlotsView recipeSlotsView, @NotNull PoseStack stack, double mouseX, double mouseY) {
        animatedFlame.draw(stack, 20, 20);

        IDrawableAnimated arrow = cachedArrows.getUnchecked(298);
        arrow.draw(stack, 60, 20);

        drawCookTime(stack);
    }

    protected void drawCookTime(PoseStack poseStack) {
        int cookTime = 298;
        int cookTimeSeconds = cookTime / 20;
        Component timeString = Component.translatable("gui.jei.category.smelting.time.seconds", cookTimeSeconds);
        Minecraft minecraft = Minecraft.getInstance();
        Font fontRenderer = minecraft.font;
        int stringWidth = fontRenderer.width(timeString);
        fontRenderer.draw(poseStack, timeString, getBackground().getWidth() - stringWidth, 45, 0xFF808080);
    }

    @Override
    public boolean isHandled(AlloyRecipe recipe) {
        return !recipe.isSpecial();
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, AlloyRecipe recipe, @NotNull IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.OUTPUT, 97, 20).addItemStack(recipe.getResultItem());
        builder.addSlot(RecipeIngredientRole.INPUT, 1, 1).addItemStack(recipe.getInput1());
        builder.addSlot(RecipeIngredientRole.INPUT, 37, 1).addItemStack(recipe.getInput2());
    }
}
