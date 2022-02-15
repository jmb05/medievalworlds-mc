package net.jmb19905.medievalworlds.common.recipes.forge;

import net.jmb19905.medievalworlds.core.registries.MWRecipeSerializers;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;
import net.minecraftforge.items.wrapper.RecipeWrapper;

import javax.annotation.Nonnull;

@SuppressWarnings("ClassCanBeRecord")
public class ForgeRecipe implements IForgeRecipe{

    private final ResourceLocation id;
    private final ItemStack input;
    private final ItemStack result;
    private final int heatTime;

    public ForgeRecipe(ResourceLocation id, ItemStack input, ItemStack result, int heatTime){
        this.id = id;
        this.input = input;
        this.result = result;
        this.heatTime = heatTime;
    }

    @Override
    public ItemStack getInput() {
        return this.input;
    }

    @Override
    public int getHeatTime() {
        return heatTime;
    }

    @Override
    public boolean matches(@Nonnull RecipeWrapper wrapper, @Nonnull Level level) {
        ItemStack stackInSlot = wrapper.getItem(0);
        return stackInSlot.getItem() == input.getItem() && stackInSlot.getCount() >= input.getCount();
    }

    @Nonnull
    @Override
    public ItemStack assemble(@Nonnull RecipeWrapper wrapper) {
        return this.result;
    }

    @Nonnull
    @Override
    public ItemStack getResultItem() {
        return this.result;
    }

    @Nonnull
    @Override
    public ResourceLocation getId() {
        return this.id;
    }

    @Nonnull
    @Override
    public RecipeSerializer<?> getSerializer() {
        return MWRecipeSerializers.FORGE_SERIALIZER.get();
    }
}
