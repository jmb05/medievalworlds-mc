package net.jmb19905.medievalworlds.common.recipes;

import net.jmb19905.medievalworlds.common.registries.RecipeSerializerRegistryHandler;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.items.wrapper.RecipeWrapper;

import javax.annotation.Nonnull;

public class AlloyRecipe implements IAlloyRecipe{

    private final ResourceLocation id;
    private final ItemStack input1;
    private final ItemStack input2;
    private final ItemStack output;

    public AlloyRecipe(ResourceLocation id, ItemStack input1, ItemStack input2, ItemStack output) {
        this.id = id;
        this.input1 = input1;
        this.input2 = input2;
        this.output = output;
    }


    public boolean matchesUniversally(RecipeWrapper inv, @Nonnull World worldIn, boolean flipped){
        if(flipped){
            return matchesFlipped(inv);
        }else{
            return matches(inv, worldIn);
        }
    }

    public boolean matchesFlipped(RecipeWrapper inv){
        ItemStack stackInSlot0 = inv.getStackInSlot(1);
        ItemStack stackInSlot1 = inv.getStackInSlot(0);
        return (stackInSlot0.getItem() == input1.getItem() && stackInSlot0.getCount() >= input1.getCount() &&
                stackInSlot1.getItem() == input2.getItem() && stackInSlot1.getCount() >= input2.getCount());
    }

    @Override
    public boolean matches(RecipeWrapper inv, @Nonnull World worldIn) {
        ItemStack stackInSlot0 = inv.getStackInSlot(0);
        ItemStack stackInSlot1 = inv.getStackInSlot(1);
        return (stackInSlot0.getItem() == input1.getItem() && stackInSlot0.getCount() >= input1.getCount() &&
                stackInSlot1.getItem() == input2.getItem() && stackInSlot1.getCount() >= input2.getCount());
    }

    @Nonnull
    @Override
    public ItemStack getCraftingResult(@Nonnull RecipeWrapper inv) {
        return this.output;
    }

    @Override
    public boolean canFit(int width, int height) {
        return false;
    }

    @Override
    public ItemStack getInput1() {
        return this.input1;
    }

    @Override
    public ItemStack getInput2() {
        return this.input2;
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
        return RecipeSerializerRegistryHandler.ALLOY_SERIALIZER.get();
    }

    public NonNullList<ItemStack> getInputs() {
        return NonNullList.from(null, this.input1, this.input2);
    }
}
