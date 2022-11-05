package net.jmb19905.medievalworlds.common.recipes;

import net.jmb19905.medievalworlds.common.registries.MWRecipeSerializers;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class ScrapItemRecipe extends CustomRecipe {

    public ScrapItemRecipe(ResourceLocation id) {
        super(id);
    }

    private static Optional<ScrapRecipe> findRecipe(Item item, Level level) {
        return MWRecipeHelper.findRecipeByType(MWRecipeSerializers.SCRAP_TYPE, level)
                .stream().map(recipe -> (ScrapRecipe) recipe)
                .filter(scrapRecipe -> scrapRecipe.getInput() == item)
                .findFirst();
    }

    private static boolean hasMultipleItems(@NotNull CraftingContainer container) {
        ItemStack itemStack = ItemStack.EMPTY;
        for (int i=0;i<container.getContainerSize();i++) {
            ItemStack nextItemStack = container.getItem(i);
            if (!nextItemStack.isEmpty()) {
                if (itemStack.isEmpty()) {
                    itemStack = nextItemStack;
                } else {
                    return true;
                }
            }
        }
        return false;
    }

    private static ItemStack findFirstItem(@NotNull CraftingContainer container) {
        for (int i=0;i<container.getContainerSize();i++) {
            ItemStack nextItemStack = container.getItem(i);
            if (!nextItemStack.isEmpty()) {
                return nextItemStack;
            }
        }
        return ItemStack.EMPTY;
    }

    @Override
    public boolean matches(@NotNull CraftingContainer container, @NotNull Level level) {
        if (hasMultipleItems(container)) return false;
        ScrapRecipe recipe = findRecipe(findFirstItem(container).getItem(), level).orElse(null);
        return recipe != null;
    }

    @Override
    public @NotNull ItemStack assemble(@NotNull CraftingContainer container) {
        return ItemStack.EMPTY;
    }

    public ItemStack assemble(@NotNull CraftingContainer container, Level level) {
        if (hasMultipleItems(container)) return ItemStack.EMPTY;
        ItemStack itemStack = findFirstItem(container);
        if (itemStack.isEmpty()) return itemStack;
        ScrapRecipe recipe = findRecipe(itemStack.getItem(), level).orElse(null);
        if (recipe == null) return ItemStack.EMPTY;
        ItemStack result = recipe.getResultItem().copy();
        result.setDamageValue(itemStack.getDamageValue());
        return result;
    }

    @Override
    public boolean canCraftInDimensions(int rows, int columns) {
        return rows * columns >= 2;
    }

    @Override
    public @NotNull RecipeSerializer<?> getSerializer() {
        return MWRecipeSerializers.SCRAP_ITEM_SERIALIZER.get();
    }
}
