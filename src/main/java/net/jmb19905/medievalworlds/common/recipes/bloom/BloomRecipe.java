package net.jmb19905.medievalworlds.common.recipes.bloom;

import net.jmb19905.medievalworlds.core.registries.MWRecipeSerializers;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;
import net.minecraftforge.items.wrapper.RecipeWrapper;

import javax.annotation.Nonnull;

@SuppressWarnings("ClassCanBeRecord")
public class BloomRecipe implements IBloomRecipe{

    private final ResourceLocation id;
    private final Ingredient input;
    private final ItemStack primaryOutput;
    private final ItemStack primaryPacked;
    private final int primaryOffset;
    private final ItemStack secondaryOutput;
    private final ItemStack secondaryPacked;
    private final int secondaryOffset;

    public BloomRecipe(ResourceLocation id, Ingredient input, ItemStack primaryOutput, ItemStack primaryPacked, int primaryOffset, ItemStack secondaryOutput, ItemStack secondaryPacked, int secondaryOffset){
        this.id = id;
        this.input = input;
        this.primaryOutput = primaryOutput;
        this.primaryPacked = primaryPacked;
        this.primaryOffset = primaryOffset;
        this.secondaryOutput = secondaryOutput;
        this.secondaryPacked = secondaryPacked;
        this.secondaryOffset = secondaryOffset;
    }

    @Override
    public Ingredient getInput() {
        return input;
    }

    @Override
    public ItemStack getPrimaryOutput() {
        return primaryOutput;
    }

    @Override
    public ItemStack getPrimaryOutputPacked() {
        return primaryPacked;
    }

    @Override
    public int getPrimaryOffset() {
        return primaryOffset;
    }

    @Override
    public ItemStack getSecondaryOutput() {
        return secondaryOutput;
    }

    @Override
    public ItemStack getSecondaryOutputPacked() {
        return secondaryPacked;
    }

    @Override
    public int getSecondaryOffset() {
        return secondaryOffset;
    }

    @Override
    public boolean matches(RecipeWrapper inv, @Nonnull Level level) {
        ItemStack stackInInputSlot = inv.getItem(0);
        return input.test(stackInInputSlot);
    }

    @Nonnull
    @Override
    public ItemStack assemble(@Nonnull RecipeWrapper inv) {
        return this.primaryOutput;
    }

    @Nonnull
    @Override
    public ItemStack getResultItem() {
        return this.primaryOutput;
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
