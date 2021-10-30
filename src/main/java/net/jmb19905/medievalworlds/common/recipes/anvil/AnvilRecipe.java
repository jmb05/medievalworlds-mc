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
    private final ItemStack input1;
    private final ItemStack input2;
    private final ItemStack output;

    public AnvilRecipe(ResourceLocation id, ItemStack input1, ItemStack input2, ItemStack output) {
        this.id = id;
        this.input1 = input1;
        this.input2 = input2;
        this.output = output;
    }

    @Override
    public ItemStack getInput1() {
        return input1;
    }

    @Override
    public ItemStack getInput2() {
        return input2;
    }

    @Override
    public boolean matches(@Nonnull RecipeWrapper invWrapper, @Nonnull Level level) {
        return invWrapper.getItem(0).getItem() == input1.getItem() && invWrapper.getItem(1).getItem() == input2.getItem();
    }

    @Nonnull
    @Override
    public ItemStack assemble(@Nonnull RecipeWrapper invWrapper) {
        return this.output;
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

}
