package net.jmb19905.medievalworlds.common.recipes.smithing;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.items.wrapper.RecipeWrapper;
import org.jetbrains.annotations.NotNull;

public class SmithingRecipe implements ISmithingRecipe{

    private final ResourceLocation id;
    private final ItemStack input;
    private final ItemStack addition;
    private final ItemStack result;

    private final int cost;

    public SmithingRecipe(ResourceLocation id, ItemStack input, ItemStack addition, ItemStack result, int cost) {
        this.id = id;
        this.input = input;
        this.addition = addition;
        this.result = result;
        this.cost = cost;
    }

    @Override
    public ItemStack getInput() {
        return input;
    }

    @Override
    public ItemStack getAddition() {
        return addition;
    }

    @Override
    public int getCost() {
        return cost;
    }

    @Override
    public boolean matches(@NotNull RecipeWrapper container, @NotNull Level level) {
        if(input.isEmpty() || addition.isEmpty()) return false;
        ItemStack inputSlotItem = container.getItem(0);
        ItemStack additionSlot = container.getItem(1);
        return inputSlotItem.getItem() == input.getItem() && inputSlotItem.getCount() >= input.getCount()
                && additionSlot.getItem() == addition.getItem() && additionSlot.getCount() >= addition.getCount();
    }

    @NotNull
    @Override
    public ItemStack assemble(RecipeWrapper container) {
        ItemStack itemstack = this.result.copy();
        CompoundTag compoundtag = container.getItem(0).getTag();
        if (compoundtag != null) {
            itemstack.setTag(compoundtag.copy());
        }

        return itemstack;
    }

    @Override
    public boolean isAdditionIngredient(ItemStack stack) {
        return addition.getItem() == stack.getItem();
    }

    @NotNull
    @Override
    public ItemStack getResultItem() {
        return result;
    }

    @NotNull
    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return width * height >= 2;
    }
}