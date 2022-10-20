package net.jmb19905.medievalworlds.common.datagen;

import com.google.common.collect.ImmutableList;
import net.jmb19905.medievalworlds.MedievalWorlds;
import net.jmb19905.medievalworlds.common.datagen.custom.*;
import net.jmb19905.medievalworlds.common.item.heated.HeatedItem;
import net.jmb19905.medievalworlds.common.registries.MWBlocks;
import net.jmb19905.medievalworlds.common.registries.MWItems;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
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

        buildStaffRecipes(recipeConsumer);
        buildStorageBlockRecipes(recipeConsumer);
        buildOreProcessingRecipes(recipeConsumer);
        buildAlloyingRecipes(recipeConsumer);
        buildCampfireRecipes(recipeConsumer);
        buildBlastHeatingRecipes(recipeConsumer);
        buildToolAndWeaponRecipes(recipeConsumer);
        buildAnvilRecipes(recipeConsumer);

        for(RegistryObject<Item> ro : MWItems.TOOL_PARTS.values()) {
            if(ro.get() instanceof HeatedItem item) {
                slackTubRecipe(recipeConsumer, Ingredient.of(item), 1, item.getBaseItem(), 1);
            }
        }

        for(RegistryObject<Item> ro : MWItems.WEAPON_PARTS.values()) {
            if(ro.get() instanceof HeatedItem item) {
                slackTubRecipe(recipeConsumer, Ingredient.of(item), 1, item.getBaseItem(), 1);
            }
        }

        ShapedRecipeBuilder.shaped(Blocks.BLAST_FURNACE)
                .define('S', Blocks.SMOOTH_STONE)
                .define('I', MWItems.STEEL_INGOT.get())
                .define('F', Blocks.FURNACE)
                .pattern("III")
                .pattern("IFI")
                .pattern("SSS")
                .unlockedBy(getHasName(MWItems.STEEL_INGOT.get()), has(MWItems.STEEL_INGOT.get()))
                .save(recipeConsumer);

        ShapedRecipeBuilder.shaped(MWItems.FIRE_STARTER.get())
                .define('S', Items.STICK)
                .define('/', Items.STRING)
                .define('P', ItemTags.PLANKS)
                .pattern(" S ")
                .pattern("S/S")
                .pattern(" P ")
                .unlockedBy(getHasName(Items.STRING), has(Items.STRING))
                .save(recipeConsumer, new ResourceLocation(MedievalWorlds.MOD_ID, "fire_starter"));

        new BloomRecipeBuilder(
                Ingredient.of(Items.RAW_IRON, Items.IRON_ORE, Items.DEEPSLATE_IRON_ORE),
                Items.IRON_NUGGET, 9,
                Items.IRON_INGOT, 9,
                3,
                MWItems.STEEL_NUGGET.get(), 1,
                MWItems.STEEL_INGOT.get(), 9,
                1
        )
                .unlockedBy("has_iron_ore", has(Items.IRON_ORE))
                .unlockedBy("has_deepslate_iron_ore", has(Items.DEEPSLATE_IRON_ORE))
                .unlockedBy("has_raw_iron", has(Items.RAW_IRON))
                .save(recipeConsumer, new ResourceLocation(MedievalWorlds.MOD_ID, "iron_blooming"));
    }

    protected void buildStaffRecipes(@NotNull Consumer<FinishedRecipe> recipeConsumer) {
        staffRecipe(recipeConsumer, Blocks.OAK_LOG, MWItems.OAK_STAFF.get());
        staffRecipe(recipeConsumer, Blocks.DARK_OAK_LOG, MWItems.DARK_OAK_STAFF.get());
        staffRecipe(recipeConsumer, Blocks.BIRCH_LOG, MWItems.BIRCH_STAFF.get());
        staffRecipe(recipeConsumer, Blocks.SPRUCE_LOG, MWItems.SPRUCE_STAFF.get());
        staffRecipe(recipeConsumer, Blocks.ACACIA_LOG, MWItems.ACACIA_STAFF.get());
        staffRecipe(recipeConsumer, Blocks.JUNGLE_LOG, MWItems.JUNGLE_STAFF.get());
        staffRecipe(recipeConsumer, Blocks.MANGROVE_LOG, MWItems.MANGROVE_STAFF.get());
    }

    protected void buildStorageBlockRecipes(@NotNull Consumer<FinishedRecipe> recipeConsumer) {
        nineBlockStorageRecipesRecipesWithCustomUnpacking(recipeConsumer, MWItems.STEEL_INGOT.get(), MWBlocks.STEEL_BLOCK.get(), "steel_ingot_from_steel_block", "steel_ingot");
        nineBlockStorageRecipesWithCustomPacking(recipeConsumer, MWItems.STEEL_NUGGET.get(), MWItems.STEEL_INGOT.get(), "steel_ingot_from_nuggets", "steel_ingot");
        nineBlockStorageRecipesRecipesWithCustomUnpacking(recipeConsumer, MWItems.SILVER_INGOT.get(), MWBlocks.SILVER_BLOCK.get(), "silver_ingot_from_steel_block", "silver_ingot");
        nineBlockStorageRecipesWithCustomPacking(recipeConsumer, MWItems.SILVER_NUGGET.get(), MWItems.SILVER_INGOT.get(), "silver_ingot_from_nuggets", "silver_ingot");
        nineBlockStorageRecipesRecipesWithCustomUnpacking(recipeConsumer, MWItems.BRONZE_INGOT.get(), MWBlocks.BRONZE_BLOCK.get(), "bronze_ingot_from_steel_block", "bronze_ingot");
        nineBlockStorageRecipesWithCustomPacking(recipeConsumer, MWItems.BRONZE_NUGGET.get(), MWItems.BRONZE_INGOT.get(), "bronze_ingot_from_nuggets", "bronze_ingot");
        nineBlockStorageRecipesRecipesWithCustomUnpacking(recipeConsumer, MWItems.TIN_INGOT.get(), MWBlocks.TIN_BLOCK.get(), "tin_ingot_from_steel_block", "tin_ingot");
        nineBlockStorageRecipesWithCustomPacking(recipeConsumer, MWItems.TIN_NUGGET.get(), MWItems.TIN_INGOT.get(), "tin_ingot_from_nuggets", "tin_ingot");

        nineBlockStorageRecipes(recipeConsumer, MWItems.RAW_SILVER.get(), MWBlocks.RAW_SILVER_BLOCK.get());
        nineBlockStorageRecipes(recipeConsumer, MWItems.RAW_TIN.get(), MWBlocks.RAW_TIN_BLOCK.get());
    }

    protected void buildOreProcessingRecipes(@NotNull Consumer<FinishedRecipe> recipeConsumer) {
        oreSmelting(recipeConsumer, SILVER_SMELTABLES, MWItems.SILVER_INGOT.get(), 1.0F, 200, "silver_ingot");
        oreSmelting(recipeConsumer, TIN_SMELTABLES, MWItems.TIN_INGOT.get(), 0.7F, 200, "tin_ingot");

        oreBlasting(recipeConsumer, SILVER_SMELTABLES, MWItems.SILVER_INGOT.get(), 1.0F, 100, "silver_ingot");
        oreBlasting(recipeConsumer, TIN_SMELTABLES, MWItems.TIN_INGOT.get(), 0.7F, 100, "tin_ingot");
    }

    protected void buildAlloyingRecipes(@NotNull Consumer<FinishedRecipe> recipeConsumer) {
        alloying(recipeConsumer, MWItems.BRONZE_INGOT.get(), 4, Items.COPPER_INGOT, 3, MWItems.TIN_INGOT.get(), 1, "_from_ingot");
        alloying(recipeConsumer, MWItems.BRONZE_INGOT.get(), 4, Items.RAW_COPPER, 3, MWItems.RAW_TIN.get(), 1, "_from_raw");
        alloying(recipeConsumer, Items.NETHERITE_INGOT, 1, Items.GOLD_INGOT, 4, Items.NETHERITE_SCRAP, 4, "");
    }

    protected void buildCampfireRecipes(@NotNull Consumer<FinishedRecipe> recipeConsumer) {
        campfireCookingRecipe(recipeConsumer, Items.CLAY_BALL, 1000, Items.BRICK);
        campfireCookingRecipe(recipeConsumer, MWItems.BRONZE_INGOT.get(), 1200, MWItems.HEATED_BRONZE_INGOT.get());
        campfireCookingRecipe(recipeConsumer, Items.COPPER_INGOT, 1200, MWItems.HEATED_COPPER_INGOT.get());
        campfireCookingRecipe(recipeConsumer, Items.IRON_INGOT, 1800, MWItems.HEATED_IRON_INGOT.get());
    }

    protected void buildBlastHeatingRecipes(@NotNull Consumer<FinishedRecipe> recipeConsumer) {
        heatingBlasting(recipeConsumer, MWItems.HEATED_COPPER_INGOT.get());
        heatingBlasting(recipeConsumer, MWItems.HEATED_BRONZE_INGOT.get());
        heatingBlasting(recipeConsumer, MWItems.HEATED_GOLD_INGOT.get());
        heatingBlasting(recipeConsumer, MWItems.HEATED_IRON_INGOT.get());
        heatingBlasting(recipeConsumer, MWItems.HEATED_SILVER_INGOT.get());
        heatingBlasting(recipeConsumer, MWItems.HEATED_STEEL_INGOT.get());
        heatingBlasting(recipeConsumer, MWItems.HEATED_NETHERITE_INGOT.get());
    }

    protected void buildToolAndWeaponRecipes(@NotNull Consumer<FinishedRecipe> recipeConsumer) {
        forgeHammer(recipeConsumer, ItemTags.STONE_TOOL_MATERIALS, MWItems.STONE_FORGE_HAMMER.get(), "stone_tag");
        forgeHammer(recipeConsumer, Blocks.IRON_BLOCK, MWItems.IRON_FORGE_HAMMER.get());
        forgeHammer(recipeConsumer, MWBlocks.STEEL_BLOCK.get(), MWItems.STEEL_FORGE_HAMMER.get());

        daggerRecipe(recipeConsumer, Items.IRON_NUGGET, MWItems.IRON_DAGGER.get());
        daggerRecipe(recipeConsumer, MWItems.SILVER_NUGGET.get(), MWItems.SILVER_DAGGER.get());
        daggerRecipe(recipeConsumer, MWItems.STEEL_NUGGET.get(), MWItems.STEEL_DAGGER.get());
    }

    protected void buildAnvilRecipes(@NotNull Consumer<FinishedRecipe> recipeConsumer) {
        anvilRecipe(recipeConsumer, new ItemStack(Items.IRON_BARS, 3), new ItemStack(Items.IRON_INGOT, 1), "", 0);

        anvilRecipe(recipeConsumer, new ItemStack(MWItems.TOOL_PARTS.get("heated_bronze_armor_plate").get(), 1),    new ItemStack(MWItems.HEATED_BRONZE_INGOT.get(), 1), "", 0);
        anvilRecipe(recipeConsumer, new ItemStack(MWItems.TOOL_PARTS.get("heated_bronze_axe_head").get(), 1),       new ItemStack(MWItems.HEATED_BRONZE_INGOT.get(), 3), "", 0);
        anvilRecipe(recipeConsumer, new ItemStack(MWItems.TOOL_PARTS.get("heated_bronze_hoe_head").get(), 1),       new ItemStack(MWItems.HEATED_BRONZE_INGOT.get(), 2), "", 0);
        anvilRecipe(recipeConsumer, new ItemStack(MWItems.TOOL_PARTS.get("heated_bronze_pickaxe_head").get(), 1),   new ItemStack(MWItems.HEATED_BRONZE_INGOT.get(), 3), "", 0);
        anvilRecipe(recipeConsumer, new ItemStack(MWItems.TOOL_PARTS.get("heated_bronze_shovel_head").get(), 1),    new ItemStack(MWItems.HEATED_BRONZE_INGOT.get(), 1), "", 0);
        anvilRecipe(recipeConsumer, new ItemStack(MWItems.TOOL_PARTS.get("heated_bronze_sword_blade").get(), 1),    new ItemStack(MWItems.HEATED_BRONZE_INGOT.get(), 2), "", 0);

        anvilRecipe(recipeConsumer, new ItemStack(MWItems.HEATED_BRONZE_INGOT.get(), 1), new ItemStack(MWItems.TOOL_PARTS.get("heated_bronze_armor_plate").get(), 1), "_from_armor_plate", 0);
        anvilRecipe(recipeConsumer, new ItemStack(MWItems.HEATED_BRONZE_INGOT.get(), 3), new ItemStack(MWItems.TOOL_PARTS.get("heated_bronze_axe_head").get(), 1), "_from_axe_head", 0);
        anvilRecipe(recipeConsumer, new ItemStack(MWItems.HEATED_BRONZE_INGOT.get(), 2), new ItemStack(MWItems.TOOL_PARTS.get("heated_bronze_hoe_head").get(), 1), "_from_hoe_head", 0);
        anvilRecipe(recipeConsumer, new ItemStack(MWItems.HEATED_BRONZE_INGOT.get(), 3), new ItemStack(MWItems.TOOL_PARTS.get("heated_bronze_pickaxe_head").get(), 1), "_from_pickaxe_head", 0);
        anvilRecipe(recipeConsumer, new ItemStack(MWItems.HEATED_BRONZE_INGOT.get(), 1), new ItemStack(MWItems.TOOL_PARTS.get("heated_bronze_shovel_head").get(), 1), "_from_shovel_head", 0);
        anvilRecipe(recipeConsumer, new ItemStack(MWItems.HEATED_BRONZE_INGOT.get(), 2), new ItemStack(MWItems.TOOL_PARTS.get("heated_bronze_sword_blade").get(), 1), "_from_sword_blade", 0);

        anvilRecipe(recipeConsumer, new ItemStack(MWItems.TOOL_PARTS.get("heated_gold_armor_plate").get(), 1),  new ItemStack(MWItems.HEATED_GOLD_INGOT.get(), 1), "", 1);
        anvilRecipe(recipeConsumer, new ItemStack(MWItems.TOOL_PARTS.get("heated_gold_axe_head").get(), 1),     new ItemStack(MWItems.HEATED_GOLD_INGOT.get(), 3), "", 1);
        anvilRecipe(recipeConsumer, new ItemStack(MWItems.TOOL_PARTS.get("heated_gold_hoe_head").get(), 1),     new ItemStack(MWItems.HEATED_GOLD_INGOT.get(), 2), "", 1);
        anvilRecipe(recipeConsumer, new ItemStack(MWItems.TOOL_PARTS.get("heated_gold_pickaxe_head").get(), 1), new ItemStack(MWItems.HEATED_GOLD_INGOT.get(), 3), "", 1);
        anvilRecipe(recipeConsumer, new ItemStack(MWItems.TOOL_PARTS.get("heated_gold_shovel_head").get(), 1),  new ItemStack(MWItems.HEATED_GOLD_INGOT.get(), 1), "", 1);
        anvilRecipe(recipeConsumer, new ItemStack(MWItems.TOOL_PARTS.get("heated_gold_sword_blade").get(), 1),  new ItemStack(MWItems.HEATED_GOLD_INGOT.get(), 2), "", 1);

        anvilRecipe(recipeConsumer, new ItemStack(MWItems.HEATED_GOLD_INGOT.get(), 1), new ItemStack(MWItems.TOOL_PARTS.get("heated_gold_armor_plate").get(), 1), "_from_armor_plate", 1);
        anvilRecipe(recipeConsumer, new ItemStack(MWItems.HEATED_GOLD_INGOT.get(), 3), new ItemStack(MWItems.TOOL_PARTS.get("heated_gold_axe_head").get(), 1), "_from_axe_head", 1);
        anvilRecipe(recipeConsumer, new ItemStack(MWItems.HEATED_GOLD_INGOT.get(), 2), new ItemStack(MWItems.TOOL_PARTS.get("heated_gold_hoe_head").get(), 1), "_from_hoe_head", 1);
        anvilRecipe(recipeConsumer, new ItemStack(MWItems.HEATED_GOLD_INGOT.get(), 3), new ItemStack(MWItems.TOOL_PARTS.get("heated_gold_pickaxe_head").get(), 1), "_from_pickaxe_head", 1);
        anvilRecipe(recipeConsumer, new ItemStack(MWItems.HEATED_GOLD_INGOT.get(), 1), new ItemStack(MWItems.TOOL_PARTS.get("heated_gold_shovel_head").get(), 1), "_from_shovel_head", 1);
        anvilRecipe(recipeConsumer, new ItemStack(MWItems.HEATED_GOLD_INGOT.get(), 2), new ItemStack(MWItems.TOOL_PARTS.get("heated_gold_sword_blade").get(), 1), "_from_sword_blade", 1);

        anvilRecipe(recipeConsumer, new ItemStack(MWItems.TOOL_PARTS.get("heated_iron_armor_plate").get(), 1),  new ItemStack(MWItems.HEATED_IRON_INGOT.get(), 1), "", 0);
        anvilRecipe(recipeConsumer, new ItemStack(MWItems.TOOL_PARTS.get("heated_iron_axe_head").get(), 1),     new ItemStack(MWItems.HEATED_IRON_INGOT.get(), 3), "", 0);
        anvilRecipe(recipeConsumer, new ItemStack(MWItems.TOOL_PARTS.get("heated_iron_hoe_head").get(), 1),     new ItemStack(MWItems.HEATED_IRON_INGOT.get(), 2), "", 0);
        anvilRecipe(recipeConsumer, new ItemStack(MWItems.TOOL_PARTS.get("heated_iron_pickaxe_head").get(), 1), new ItemStack(MWItems.HEATED_IRON_INGOT.get(), 3), "", 0);
        anvilRecipe(recipeConsumer, new ItemStack(MWItems.TOOL_PARTS.get("heated_iron_shovel_head").get(), 1),  new ItemStack(MWItems.HEATED_IRON_INGOT.get(), 1), "", 0);
        anvilRecipe(recipeConsumer, new ItemStack(MWItems.TOOL_PARTS.get("heated_iron_sword_blade").get(), 1),  new ItemStack(MWItems.HEATED_IRON_INGOT.get(), 2), "", 0);

        anvilRecipe(recipeConsumer, new ItemStack(MWItems.HEATED_IRON_INGOT.get(), 1), new ItemStack(MWItems.TOOL_PARTS.get("heated_iron_armor_plate").get(), 1), "_from_armor_plate", 0);
        anvilRecipe(recipeConsumer, new ItemStack(MWItems.HEATED_IRON_INGOT.get(), 3), new ItemStack(MWItems.TOOL_PARTS.get("heated_iron_axe_head").get(), 1), "_from_axe_head", 0);
        anvilRecipe(recipeConsumer, new ItemStack(MWItems.HEATED_IRON_INGOT.get(), 2), new ItemStack(MWItems.TOOL_PARTS.get("heated_iron_hoe_head").get(), 1), "_from_hoe_head", 0);
        anvilRecipe(recipeConsumer, new ItemStack(MWItems.HEATED_IRON_INGOT.get(), 3), new ItemStack(MWItems.TOOL_PARTS.get("heated_iron_pickaxe_head").get(), 1), "_from_pickaxe_head", 0);
        anvilRecipe(recipeConsumer, new ItemStack(MWItems.HEATED_IRON_INGOT.get(), 1), new ItemStack(MWItems.TOOL_PARTS.get("heated_iron_shovel_head").get(), 1), "_from_shovel_head", 0);
        anvilRecipe(recipeConsumer, new ItemStack(MWItems.HEATED_IRON_INGOT.get(), 2), new ItemStack(MWItems.TOOL_PARTS.get("heated_iron_sword_blade").get(), 1), "_from_sword_blade", 0);

        anvilRecipe(recipeConsumer, new ItemStack(MWItems.TOOL_PARTS.get("heated_silver_armor_plate").get(), 1),    new ItemStack(MWItems.HEATED_SILVER_INGOT.get(), 1), "", 1);
        anvilRecipe(recipeConsumer, new ItemStack(MWItems.TOOL_PARTS.get("heated_silver_axe_head").get(), 1),       new ItemStack(MWItems.HEATED_SILVER_INGOT.get(), 3), "", 1);
        anvilRecipe(recipeConsumer, new ItemStack(MWItems.TOOL_PARTS.get("heated_silver_hoe_head").get(), 1),       new ItemStack(MWItems.HEATED_SILVER_INGOT.get(), 2), "", 1);
        anvilRecipe(recipeConsumer, new ItemStack(MWItems.TOOL_PARTS.get("heated_silver_pickaxe_head").get(), 1),   new ItemStack(MWItems.HEATED_SILVER_INGOT.get(), 3), "", 1);
        anvilRecipe(recipeConsumer, new ItemStack(MWItems.TOOL_PARTS.get("heated_silver_shovel_head").get(), 1),    new ItemStack(MWItems.HEATED_SILVER_INGOT.get(), 1), "", 1);
        anvilRecipe(recipeConsumer, new ItemStack(MWItems.TOOL_PARTS.get("heated_silver_sword_blade").get(), 1),    new ItemStack(MWItems.HEATED_SILVER_INGOT.get(), 2), "", 1);

        anvilRecipe(recipeConsumer, new ItemStack(MWItems.HEATED_SILVER_INGOT.get(), 1), new ItemStack(MWItems.TOOL_PARTS.get("heated_silver_armor_plate").get(), 1), "_from_armor_plate", 1);
        anvilRecipe(recipeConsumer, new ItemStack(MWItems.HEATED_SILVER_INGOT.get(), 3), new ItemStack(MWItems.TOOL_PARTS.get("heated_silver_axe_head").get(), 1), "_from_axe_head", 1);
        anvilRecipe(recipeConsumer, new ItemStack(MWItems.HEATED_SILVER_INGOT.get(), 2), new ItemStack(MWItems.TOOL_PARTS.get("heated_silver_hoe_head").get(), 1), "_from_hoe_head", 1);
        anvilRecipe(recipeConsumer, new ItemStack(MWItems.HEATED_SILVER_INGOT.get(), 3), new ItemStack(MWItems.TOOL_PARTS.get("heated_silver_pickaxe_head").get(), 1), "_from_pickaxe_head", 1);
        anvilRecipe(recipeConsumer, new ItemStack(MWItems.HEATED_SILVER_INGOT.get(), 1), new ItemStack(MWItems.TOOL_PARTS.get("heated_silver_shovel_head").get(), 1), "_from_shovel_head", 1);
        anvilRecipe(recipeConsumer, new ItemStack(MWItems.HEATED_SILVER_INGOT.get(), 2), new ItemStack(MWItems.TOOL_PARTS.get("heated_silver_sword_blade").get(), 1), "_from_sword_blade", 1);

        anvilRecipe(recipeConsumer, new ItemStack(MWItems.TOOL_PARTS.get("heated_steel_armor_plate").get(), 1),     new ItemStack(MWItems.HEATED_STEEL_INGOT.get(), 1), "", 1);
        anvilRecipe(recipeConsumer, new ItemStack(MWItems.TOOL_PARTS.get("heated_steel_axe_head").get(), 1),        new ItemStack(MWItems.HEATED_STEEL_INGOT.get(), 3), "", 1);
        anvilRecipe(recipeConsumer, new ItemStack(MWItems.TOOL_PARTS.get("heated_steel_hoe_head").get(), 1),        new ItemStack(MWItems.HEATED_STEEL_INGOT.get(), 2), "", 1);
        anvilRecipe(recipeConsumer, new ItemStack(MWItems.TOOL_PARTS.get("heated_steel_pickaxe_head").get(), 1),    new ItemStack(MWItems.HEATED_STEEL_INGOT.get(), 3), "", 1);
        anvilRecipe(recipeConsumer, new ItemStack(MWItems.TOOL_PARTS.get("heated_steel_shovel_head").get(), 1),     new ItemStack(MWItems.HEATED_STEEL_INGOT.get(), 1), "", 1);
        anvilRecipe(recipeConsumer, new ItemStack(MWItems.TOOL_PARTS.get("heated_steel_sword_blade").get(), 1),     new ItemStack(MWItems.HEATED_STEEL_INGOT.get(), 2), "", 1);

        anvilRecipe(recipeConsumer, new ItemStack(MWItems.HEATED_STEEL_INGOT.get(), 1), new ItemStack(MWItems.TOOL_PARTS.get("heated_steel_armor_plate").get(), 1), "_from_armor_plate", 1);
        anvilRecipe(recipeConsumer, new ItemStack(MWItems.HEATED_STEEL_INGOT.get(), 3), new ItemStack(MWItems.TOOL_PARTS.get("heated_steel_axe_head").get(), 1), "_from_axe_head", 1);
        anvilRecipe(recipeConsumer, new ItemStack(MWItems.HEATED_STEEL_INGOT.get(), 2), new ItemStack(MWItems.TOOL_PARTS.get("heated_steel_hoe_head").get(), 1), "_from_hoe_head", 1);
        anvilRecipe(recipeConsumer, new ItemStack(MWItems.HEATED_STEEL_INGOT.get(), 3), new ItemStack(MWItems.TOOL_PARTS.get("heated_steel_pickaxe_head").get(), 1), "_from_pickaxe_head", 1);
        anvilRecipe(recipeConsumer, new ItemStack(MWItems.HEATED_STEEL_INGOT.get(), 1), new ItemStack(MWItems.TOOL_PARTS.get("heated_steel_shovel_head").get(), 1), "_from_shovel_head", 1);
        anvilRecipe(recipeConsumer, new ItemStack(MWItems.HEATED_STEEL_INGOT.get(), 2), new ItemStack(MWItems.TOOL_PARTS.get("heated_steel_sword_blade").get(), 1), "_from_sword_blade", 1);
    }

    @SuppressWarnings("SameParameterValue")
    protected static void slackTubRecipe(Consumer<FinishedRecipe> recipeConsumer, Ingredient input, int inputCount, ItemLike output, int outputCount) {
        new SlackTubRecipeBuilder(new ItemStack(output, outputCount), input, inputCount)
                .unlockedBy(getHasName(output), has(output))
                .save(recipeConsumer, new ResourceLocation(MedievalWorlds.MOD_ID, getItemName(output) + "_quenching"));
    }

    protected static void daggerRecipe(Consumer<FinishedRecipe> recipeConsumer, ItemLike nugget, ItemLike dagger) {
        ShapedRecipeBuilder.shaped(dagger)
                .define('I', Items.STICK)
                .define('#', nugget)
                .pattern("#")
                .pattern("I")
                .unlockedBy(getItemName(nugget), has(nugget))
                .save(recipeConsumer, new ResourceLocation(MedievalWorlds.MOD_ID, getItemName(dagger)));
    }

    @SuppressWarnings("SameParameterValue")
    private static void forgeHammer(Consumer<FinishedRecipe> recipeConsumer, TagKey<Item> material, ItemLike hammer, String unlockedName) {
        ShapedRecipeBuilder.shaped(hammer)
                .define('X', material)
                .define('Y', Items.STICK)
                .pattern("XYX")
                .pattern(" Y ")
                .pattern(" Y ")
                .unlockedBy("has_" + unlockedName, has(material))
                .save(recipeConsumer, new ResourceLocation(MedievalWorlds.MOD_ID, getItemName(hammer)));
    }

    private static void forgeHammer(Consumer<FinishedRecipe> recipeConsumer, ItemLike material, ItemLike hammer) {
        ShapedRecipeBuilder.shaped(hammer)
                .define('X', material)
                .define('Y', Items.STICK)
                .pattern("XYX")
                .pattern(" Y ")
                .pattern(" Y ")
                .unlockedBy(getHasName(material), has(material))
                .save(recipeConsumer, new ResourceLocation(MedievalWorlds.MOD_ID, getItemName(hammer)));
    }

    protected static void heatingBlasting(@NotNull Consumer<FinishedRecipe> recipeConsumer, HeatedItem heatedItem) {
        oreBlasting(recipeConsumer, ImmutableList.of(heatedItem.getBaseItem()), heatedItem, 0.0f, 200, "");
    }

    private static void campfireCookingRecipe(Consumer<FinishedRecipe> recipeConsumer, ItemLike input, int duration, ItemLike output) {
        SimpleCookingRecipeBuilder.cooking(Ingredient.of(input), output, 0.0f, duration, RecipeSerializer.CAMPFIRE_COOKING_RECIPE)
                .unlockedBy(getHasName(input), has(input))
                .save(recipeConsumer, new ResourceLocation(MedievalWorlds.MOD_ID, getItemName(output) + "_from_campfire_cooking"));
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

    private static void anvilRecipe(Consumer<FinishedRecipe> recipeConsumer, ItemStack output, ItemStack input, String prefix, int minAnvilLevel) {
        new AnvilRecipeBuilder(output, input, minAnvilLevel)
                .unlockedBy("has_input", has(input.getItem()))
                .save(recipeConsumer, new ResourceLocation(MedievalWorlds.MOD_ID, Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(output.getItem())).getPath() + "_anvil" + prefix));
    }

    @SuppressWarnings("SameParameterValue")
    protected static void alloying(@NotNull Consumer<FinishedRecipe> recipeConsumer, ItemLike result, int resultCount, ItemLike input1, int input1Count, ItemLike input2, int input2Count, String prefix) {
        new AlloyRecipeBuilder(new ItemStack(result, resultCount), new ItemStack(input1, input1Count), new ItemStack(input2, input2Count))
                .unlockedBy(getItemName(input1), has(input1))
                .unlockedBy(getItemName(input2), has(input2))
                .save(recipeConsumer, new ResourceLocation(MedievalWorlds.MOD_ID, Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(result.asItem())).getPath() + "_alloying" + prefix));
    }

}
