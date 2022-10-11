package net.jmb19905.medievalworlds.core.datagen;

import net.jmb19905.medievalworlds.MedievalWorlds;
import net.jmb19905.medievalworlds.core.datagen.custom.BurnRecipeBuilder;
import net.jmb19905.medievalworlds.core.registries.MWItems;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class MWRecipeProvider extends RecipeProvider implements IConditionBuilder {
    public MWRecipeProvider(DataGenerator generator) {
        super(generator);
    }

    @Override
    protected void buildCraftingRecipes(@NotNull Consumer<FinishedRecipe> recipeConsumer) {
        burnRecipe(recipeConsumer, "medievalworlds:charcoal_log", "minecraft:logs_that_burn");
        burnRecipe(recipeConsumer, "medievalworlds:charcoal_planks", "minecraft:planks");

        staffRecipe(recipeConsumer, Blocks.OAK_LOG, MWItems.OAK_STAFF.get());
        staffRecipe(recipeConsumer, Blocks.DARK_OAK_LOG, MWItems.DARK_OAK_STAFF.get());
        staffRecipe(recipeConsumer, Blocks.BIRCH_LOG, MWItems.BIRCH_STAFF.get());
        staffRecipe(recipeConsumer, Blocks.SPRUCE_LOG, MWItems.SPRUCE_STAFF.get());
        staffRecipe(recipeConsumer, Blocks.ACACIA_LOG, MWItems.ACACIA_STAFF.get());
        staffRecipe(recipeConsumer, Blocks.JUNGLE_LOG, MWItems.JUNGLE_STAFF.get());
        staffRecipe(recipeConsumer, Blocks.MANGROVE_LOG, MWItems.MANGROVE_STAFF.get());
    }

    private static void staffRecipe(Consumer<FinishedRecipe> recipeConsumer, ItemLike log, ItemLike staff) {
        ShapedRecipeBuilder
                .shaped(staff, 4)
                .define('/', log)
                .pattern("/")
                .pattern("/")
                .unlockedBy("has_log", has(log))
                .save(recipeConsumer);
    }

    private static void burnRecipe(Consumer<FinishedRecipe> recipeConsumer, String output, String input) {
        String[] outputParts = output.split(":");
        new BurnRecipeBuilder(output, input)
                .unlockedBy("has_flint_and_steel", has(Items.FLINT_AND_STEEL))
                //.unlockedBy("has_fire_starter", has(MWItems.FIRE_STARTER.get()))
                .save(recipeConsumer, new ResourceLocation(MedievalWorlds.MOD_ID, outputParts[outputParts.length - 1] + "_burning"));
    }

}
