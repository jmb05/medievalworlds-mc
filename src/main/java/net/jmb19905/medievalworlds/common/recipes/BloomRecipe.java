package net.jmb19905.medievalworlds.common.recipes;

import net.jmb19905.medievalworlds.common.registries.MWRecipeSerializers;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;
import net.minecraftforge.items.wrapper.RecipeWrapper;

import javax.annotation.Nonnull;

@SuppressWarnings("ClassCanBeRecord")
public class BloomRecipe implements IBloomRecipe{

    private final ResourceLocation id;
    private final ItemStack input;
    private final ItemStack output;

    public BloomRecipe(ResourceLocation id, ItemStack input, ItemStack output){
        this.id = id;
        this.input = input;
        this.output = output;
    }

    @Override
    public ItemStack getInput() {
        return input;
    }

    @Override
    public boolean matches(RecipeWrapper inv, @Nonnull Level level) {
        ItemStack stackInInputSlot = inv.getItem(0);
        return (stackInInputSlot.getItem() == input.getItem() && stackInInputSlot.getCount() >= input.getCount());
    }

    @Nonnull
    @Override
    public ItemStack assemble(@Nonnull RecipeWrapper inv) {
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
        return this.id;
    }

    @Nonnull
    @Override
    public RecipeSerializer<?> getSerializer() {
        return MWRecipeSerializers.BLOOM_SERIALIZER.get();
    }
}
