package net.jmb19905.medievalworlds.common.recipes.fletching;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraftforge.items.wrapper.RecipeWrapper;
import org.jetbrains.annotations.NotNull;

public class FletchingRecipe implements IFletchingRecipe {

    private final ResourceLocation id;
    private final Ingredient arrowHead;
    private final Ingredient arrowShaft;
    private final Ingredient arrowFletching;

    private final ItemStack result;

    public FletchingRecipe(ResourceLocation id, Ingredient arrowHead, Ingredient arrowShaft, Ingredient arrowFletching, ItemStack result) {
        this.id = id;
        this.arrowHead = arrowHead;
        this.arrowShaft = arrowShaft;
        this.arrowFletching = arrowFletching;
        this.result = result;
    }

    @Override
    public Ingredient getArrowHead() {
        return arrowHead;
    }

    @Override
    public Ingredient getArrowShaft() {
        return arrowShaft;
    }

    @Override
    public Ingredient getArrowFletching() {
        return arrowFletching;
    }

    @Override
    public boolean matches(@NotNull RecipeWrapper inv, @NotNull Level level) {
        ItemStack headStack = inv.getItem(0);
        ItemStack shaftStack = inv.getItem(1);
        ItemStack fletchingStack = inv.getItem(2);
        return arrowHead.test(headStack) && arrowShaft.test(shaftStack) && arrowFletching.test(fletchingStack);
    }

    @Override
    public @NotNull ItemStack assemble(@NotNull RecipeWrapper inv) {
        return this.result.copy();
    }

    @Override
    public boolean canCraftInDimensions(int x, int y) {
        return x >= 1 && y >= 3;
    }

    @Override
    public @NotNull ItemStack getResultItem() {
        return result;
    }

    @Override
    public @NotNull ResourceLocation getId() {
        return id;
    }
}