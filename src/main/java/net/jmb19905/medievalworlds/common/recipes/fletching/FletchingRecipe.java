package net.jmb19905.medievalworlds.common.recipes.fletching;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraftforge.items.wrapper.RecipeWrapper;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("ClassCanBeRecord")
public class FletchingRecipe implements IFletchingRecipe{

    private final ResourceLocation id;
    private final Ingredient input1;
    private final Ingredient input2;
    private final Ingredient input3;
    private final ItemStack output;

    public FletchingRecipe(ResourceLocation id, Ingredient input1, Ingredient input2, Ingredient input3, ItemStack output) {
        this.id = id;
        this.input1 = input1;
        this.input2 = input2;
        this.input3 = input3;
        this.output = output;
    }

    @Override
    public Ingredient getInput1() {
        return input1;
    }

    @Override
    public Ingredient getInput2() {
        return input2;
    }

    @Override
    public Ingredient getInput3() {
        return input3;
    }

    @Override
    public boolean matches(@NotNull RecipeWrapper wrapper, @NotNull Level level) {
        return input1.test(wrapper.getItem(1)) && input2.test(wrapper.getItem(2)) && input3.test(wrapper.getItem(3));
    }

    @Override
    public @NotNull ItemStack assemble(@NotNull RecipeWrapper wrapper) {
        return this.output.copy();
    }

    @Override
    public @NotNull ItemStack getResultItem() {
        return output;
    }

    @Override
    public @NotNull ResourceLocation getId() {
        return id;
    }
}
