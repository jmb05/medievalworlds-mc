package net.jmb19905.medievalworlds.common.recipes.grind;

import net.jmb19905.medievalworlds.core.registries.MWRecipeSerializers;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;
import net.minecraftforge.items.wrapper.RecipeWrapper;

import javax.annotation.Nonnull;

@SuppressWarnings("ClassCanBeRecord")
public class GrindRecipe implements IGrindRecipe{

    private final ResourceLocation id;
    private final ItemStack input;
    private final ItemStack output;

    public GrindRecipe(ResourceLocation id, ItemStack input, ItemStack output){
        this.id = id;
        this.input = input;
        this.output = output;
    }

    @Override
    public ItemStack getInput() {
        return input;
    }

    @Override
    public boolean matches(@Nonnull RecipeWrapper invWrapper, @Nonnull Level level) {
        return false;
    }

    @Nonnull
    @Override
    public ItemStack assemble(@Nonnull RecipeWrapper invWrapper) {
        return output;
    }

    @Nonnull
    @Override
    public ItemStack getResultItem() {
        return output;
    }

    @Nonnull
    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Nonnull
    @Override
    public RecipeSerializer<?> getSerializer() {
        return MWRecipeSerializers.GRIND_SERIALIZER.get();
    }
}
