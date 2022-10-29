package net.jmb19905.medievalworlds.common.compatability.jei.categories;

import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.jmb19905.medievalworlds.MedievalWorlds;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.NotNull;

public abstract class MWRecipeCategory<T extends Recipe<?>> implements IRecipeCategory<T> {
    protected static final ResourceLocation TEXTURE = new ResourceLocation(MedievalWorlds.MOD_ID, "textures/gui/jei.png");
    private final IDrawable background;
    private final IDrawable icon;
    private final ItemLike iconItem;
    private final RecipeType<T> type;
    private final ItemStack iconStack;

    public MWRecipeCategory(ItemLike iconItem, RecipeType<T> type, int u, int v, int w, int h, IGuiHelper helper) {
        this.iconItem = iconItem;
        this.type = type;
        background = helper.createDrawable(TEXTURE, u, v, w, h);
        iconStack = new ItemStack(iconItem);
        icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, iconStack);
    }

    @Override
    public @NotNull Component getTitle() {
        return iconItem.asItem().getName(iconStack);
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
    public @NotNull RecipeType<T> getRecipeType() {
        return type;
    }
}