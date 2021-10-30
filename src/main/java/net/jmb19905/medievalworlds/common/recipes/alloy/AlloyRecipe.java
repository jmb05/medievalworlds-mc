package net.jmb19905.medievalworlds.common.recipes.alloy;

import net.jmb19905.medievalworlds.core.registries.MWRecipeSerializers;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;
import net.minecraftforge.items.wrapper.RecipeWrapper;

import javax.annotation.Nonnull;

@SuppressWarnings("ClassCanBeRecord")
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

    public boolean matchesUniversally(RecipeWrapper inv, @Nonnull Level worldIn, boolean flipped){
        if(flipped){
            return matchesFlipped(inv);
        }else{
            return matches(inv, worldIn);
        }
    }

    public boolean matchesFlipped(RecipeWrapper inv){
        ItemStack stackInSlot0 = inv.getItem(1);
        ItemStack stackInSlot1 = inv.getItem(0);
        return (stackInSlot0.getItem() == input1.getItem() && stackInSlot0.getCount() >= input1.getCount() &&
                stackInSlot1.getItem() == input2.getItem() && stackInSlot1.getCount() >= input2.getCount());
    }

    @Override
    public boolean matches(RecipeWrapper inv, @Nonnull Level worldIn) {
        ItemStack stackInSlot0 = inv.getItem(0);
        ItemStack stackInSlot1 = inv.getItem(1);
        return (stackInSlot0.getItem() == input1.getItem() && stackInSlot0.getCount() >= input1.getCount() &&
                stackInSlot1.getItem() == input2.getItem() && stackInSlot1.getCount() >= input2.getCount());
    }

    @Nonnull
    @Override
    public ItemStack assemble(@Nonnull RecipeWrapper inv) {
        return this.output;
    }

    @Override
    public ItemStack getInput1() {
        return this.input1;
    }

    @Override
    public ItemStack getInput2() {
        return this.input2;
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
        return MWRecipeSerializers.ALLOY_SERIALIZER.get();
    }

    public NonNullList<ItemStack> getInputs() {
        return NonNullList.of(this.input1, this.input2);
    }
}
