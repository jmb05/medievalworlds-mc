package net.jmb19905.medievalworlds.common.datagen;

import com.google.common.collect.ImmutableList;
import net.jmb19905.medievalworlds.MedievalWorlds;
import net.jmb19905.medievalworlds.common.datagen.custom.AnvilRecipeBuilder;
import net.jmb19905.medievalworlds.common.datagen.custom.BurnRecipeBuilder;
import net.jmb19905.medievalworlds.common.registries.MWBlocks;
import net.jmb19905.medievalworlds.common.registries.MWItems;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.function.Consumer;

public class MWRecipeProvider extends RecipeProvider implements IConditionBuilder {

    protected static final ImmutableList<ItemLike> SILVER_SMELTABLES = ImmutableList.of(MWBlocks.SILVER_ORE.get(), MWBlocks.DEEPSLATE_SILVER_ORE.get(), MWItems.RAW_SILVER.get());
    protected static final ImmutableList<ItemLike> TIN_SMELTABLES = ImmutableList.of(MWBlocks.TIN_ORE.get(), MWBlocks.DEEPSLATE_TIN_ORE.get(), MWItems.RAW_TIN.get());

    public MWRecipeProvider(DataGenerator generator) {
        super(generator);
    }

    @Override
    protected void buildCraftingRecipes(@NotNull Consumer<FinishedRecipe> recipeConsumer) {
        burnRecipe(recipeConsumer, "medievalworlds:charcoal_log", "minecraft:logs_that_burn");
        burnRecipe(recipeConsumer, "medievalworlds:charcoal_planks", "minecraft:planks");

        anvilRecipe(recipeConsumer, new ItemStack(Items.IRON_BARS, 3), new ItemStack(Items.IRON_INGOT, 1), 0);
        anvilRecipe(recipeConsumer, new ItemStack(Items.DIAMOND, 1), new ItemStack(Items.NETHERITE_AXE, 1), 1);

        staffRecipe(recipeConsumer, Blocks.OAK_LOG, MWItems.OAK_STAFF.get());
        staffRecipe(recipeConsumer, Blocks.DARK_OAK_LOG, MWItems.DARK_OAK_STAFF.get());
        staffRecipe(recipeConsumer, Blocks.BIRCH_LOG, MWItems.BIRCH_STAFF.get());
        staffRecipe(recipeConsumer, Blocks.SPRUCE_LOG, MWItems.SPRUCE_STAFF.get());
        staffRecipe(recipeConsumer, Blocks.ACACIA_LOG, MWItems.ACACIA_STAFF.get());
        staffRecipe(recipeConsumer, Blocks.JUNGLE_LOG, MWItems.JUNGLE_STAFF.get());
        staffRecipe(recipeConsumer, Blocks.MANGROVE_LOG, MWItems.MANGROVE_STAFF.get());

        nineBlockStorageRecipesRecipesWithCustomUnpacking(recipeConsumer, MWItems.STEEL_INGOT.get(), MWBlocks.STEEL_BLOCK.get(), "steel_ingot_from_steel_block", "steel_ingot");
        nineBlockStorageRecipesWithCustomPacking(recipeConsumer, MWItems.STEEL_NUGGET.get(), MWItems.STEEL_INGOT.get(), "steel_ingot_from_nuggets", "steel_ingot");
        nineBlockStorageRecipesRecipesWithCustomUnpacking(recipeConsumer, MWItems.SILVER_INGOT.get(), MWBlocks.SILVER_BLOCK.get(), "silver_ingot_from_steel_block", "silver_ingot");
        nineBlockStorageRecipesWithCustomPacking(recipeConsumer, MWItems.SILVER_NUGGET.get(), MWItems.SILVER_INGOT.get(), "silver_ingot_from_nuggets", "silver_ingot");
        nineBlockStorageRecipesRecipesWithCustomUnpacking(recipeConsumer, MWItems.BRONZE_INGOT.get(), MWBlocks.BRONZE_BLOCK.get(), "bronze_ingot_from_steel_block", "bronze_ingot");
        nineBlockStorageRecipesWithCustomPacking(recipeConsumer, MWItems.BRONZE_NUGGET.get(), MWItems.BRONZE_INGOT.get(), "bronze_ingot_from_nuggets", "bronze_ingot");
        nineBlockStorageRecipesRecipesWithCustomUnpacking(recipeConsumer, MWItems.TIN_INGOT.get(), MWBlocks.TIN_BLOCK.get(), "tin_ingot_from_steel_block", "tin_ingot");
        nineBlockStorageRecipesWithCustomPacking(recipeConsumer, MWItems.TIN_NUGGET.get(), MWItems.TIN_INGOT.get(), "tiningot_from_nuggets", "tin_ingot");

        nineBlockStorageRecipes(recipeConsumer, MWItems.RAW_SILVER.get(), MWBlocks.RAW_SILVER_BLOCK.get());
        nineBlockStorageRecipes(recipeConsumer, MWItems.RAW_TIN.get(), MWBlocks.RAW_TIN_BLOCK.get());

        oreSmelting(recipeConsumer, SILVER_SMELTABLES, MWItems.SILVER_INGOT.get(), 1.0F, 200, "silver_ingot");
        oreSmelting(recipeConsumer, TIN_SMELTABLES, MWItems.TIN_INGOT.get(), 0.7F, 200, "tin_ingot");

        oreBlasting(recipeConsumer, SILVER_SMELTABLES, MWItems.SILVER_INGOT.get(), 1.0F, 100, "silver_ingot");
        oreBlasting(recipeConsumer, TIN_SMELTABLES, MWItems.TIN_INGOT.get(), 0.7F, 100, "tin_ingot");
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

    private static void anvilRecipe(Consumer<FinishedRecipe> recipeConsumer, ItemStack output, ItemStack input, int minAnvilLevel) {
        new AnvilRecipeBuilder(output, input, minAnvilLevel)
                .unlockedBy("has_input", has(input.getItem()))
                .save(recipeConsumer, new ResourceLocation(MedievalWorlds.MOD_ID, Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(output.getItem())).getPath() + "_anvil"));
    }

}
