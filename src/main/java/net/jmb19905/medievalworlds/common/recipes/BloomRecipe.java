package net.jmb19905.medievalworlds.common.recipes;

import net.jmb19905.medievalworlds.common.registries.RecipeSerializerRegistryHandler;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.items.wrapper.RecipeWrapper;

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
    public boolean matches(RecipeWrapper inv, World worldIn) {
        ItemStack stackInInputSlot = inv.getStackInSlot(0);
        return (stackInInputSlot.getItem() == input.getItem() && stackInInputSlot.getCount() >= input.getCount());
    }

    @Override
    public ItemStack getCraftingResult(RecipeWrapper inv) {
        return this.output;
    }

    @Override
    public ItemStack getRecipeOutput() {
        return this.output;
    }

    @Override
    public ResourceLocation getId() {
        return this.id;
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {
        return RecipeSerializerRegistryHandler.BLOOM_SERIALIZER.get();
    }
}
