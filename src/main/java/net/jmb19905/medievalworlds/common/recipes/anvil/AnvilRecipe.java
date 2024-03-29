package net.jmb19905.medievalworlds.common.recipes.anvil;

import net.jmb19905.medievalworlds.core.registries.MWRecipeSerializers;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;
import net.minecraftforge.items.wrapper.RecipeWrapper;

import javax.annotation.Nonnull;

@SuppressWarnings("ClassCanBeRecord")
public class AnvilRecipe implements IAnvilRecipe{

    private final ResourceLocation id;
    private final ItemStack input;
    private final ItemStack output;

    public AnvilRecipe(ResourceLocation id, ItemStack input, ItemStack output) {
        this.id = id;
        this.input = input;
        this.output = output;
    }

    @Override
    public ItemStack getInput() {
        return input;
    }

    public boolean matches(ItemStack input) {
        return this.input.getItem() == input.getItem() && this.input.getCount() <= input.getCount();
    }

    @Override
    public boolean matches(@Nonnull RecipeWrapper invWrapper, @Nonnull Level level) {
        return false;
    }

    @Nonnull
    @Override
    public ItemStack assemble(@Nonnull RecipeWrapper invWrapper) {
        return this.output.copy();
    }

    @Nonnull
    @Override
    public ItemStack getResultItem() {
        return this.output;
    }

    @Nonnull
    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Nonnull
    @Override
    public RecipeSerializer<?> getSerializer() {
        return MWRecipeSerializers.ANVIL_SERIALIZER.get();
    }

    @Override
    public String toString() {
        return "AnvilRecipe{" +
                "id=" + id +
                ", input=" + input +
                ", output=" + output +
                '}';
    }
}
