package net.jmb19905.medievalworlds.common.recipes.burn;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.items.wrapper.RecipeWrapper;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class BurnRecipe implements IBurnRecipe{

    private final ResourceLocation id;
    private final String inputTag;
    private final Block output;

    public BurnRecipe(ResourceLocation id, String inputTag, Block output) {
        this.id = id;
        this.inputTag = inputTag;
        this.output = output;
    }

    @Override
    public String getInputTag() {
        return inputTag;
    }

    @Override
    public Block getOutput() {
        return output;
    }

    public boolean matches(Block block) {
        return Objects.requireNonNull(BlockTags.getAllTags().getTag(new ResourceLocation(inputTag))).contains(block);
    }

    @Override
    public boolean matches(@NotNull RecipeWrapper wrapper, @NotNull Level level) {
        return false;
    }

    @NotNull
    @Override
    public ItemStack assemble(@NotNull RecipeWrapper wrapper) {
        return ItemStack.EMPTY;
    }

    @NotNull
    @Override
    public ItemStack getResultItem() {
        return ItemStack.EMPTY;
    }

    @NotNull
    @Override
    public ResourceLocation getId() {
        return id;
    }
}
