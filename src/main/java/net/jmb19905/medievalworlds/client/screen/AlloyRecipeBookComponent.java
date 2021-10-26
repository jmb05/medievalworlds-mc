package net.jmb19905.medievalworlds.client.screen;

import net.minecraft.client.gui.screens.recipebook.RecipeBookComponent;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

@OnlyIn(Dist.CLIENT)
public class AlloyRecipeBookComponent extends RecipeBookComponent {

    @Nullable
    private Ingredient fuels;

    private static final Component FILTER_NAME = new TranslatableComponent("gui.recipebook.toggleRecipes.alloyable");

    @Override
    protected void initFilterButtonTextures() {
        this.filterButton.initTextureValues(152, 182, 28, 18, RECIPE_BOOK_LOCATION);
    }

    public void slotClicked(@Nullable Slot slot) {
        super.slotClicked(slot);
        if (slot != null && slot.index < this.menu.getSize()) {
            this.ghostRecipe.clear();
        }
    }

    public void setupGhostRecipe(Recipe<?> recipe, List<Slot> slots) {
        ItemStack resultStack = recipe.getResultItem();
        this.ghostRecipe.setRecipe(recipe);
        this.ghostRecipe.addIngredient(Ingredient.of(resultStack), (slots.get(3)).x, (slots.get(3)).y);
        NonNullList<Ingredient> ingredientList = recipe.getIngredients();
        Slot slot = slots.get(2);
        if (slot.getItem().isEmpty()) {
            if (this.fuels == null) {
                this.fuels = Ingredient.of(this.getFuelItems().stream().map(ItemStack::new));
            }
            this.ghostRecipe.addIngredient(this.fuels, slot.x, slot.y);
        }

        Iterator<Ingredient> iterator = ingredientList.iterator();

        for(int i = 0; i < 3; i++) {
            if (!iterator.hasNext()) {
                return;
            }

            Ingredient ingredient = iterator.next();
            if (!ingredient.isEmpty()) {
                Slot slot1 = slots.get(i);
                this.ghostRecipe.addIngredient(ingredient, slot1.x, slot1.y);
            }
        }
    }

    @Nonnull
    @Override
    protected Component getRecipeFilterName() {
        return FILTER_NAME;
    }

    @SuppressWarnings("deprecation")
    protected Set<Item> getFuelItems() {
        return AbstractFurnaceBlockEntity.getFuel().keySet();
    }

}
