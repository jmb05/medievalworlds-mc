package net.jmb19905.medievalworlds.common.datagen;

import com.google.common.collect.ImmutableList;
import net.jmb19905.medievalworlds.MedievalWorlds;
import net.jmb19905.medievalworlds.common.datagen.recipes.*;
import net.jmb19905.medievalworlds.common.item.heated.HeatedItem;
import net.jmb19905.medievalworlds.common.registries.MWBlocks;
import net.jmb19905.medievalworlds.common.registries.MWItems;
import net.jmb19905.medievalworlds.common.registries.MWTags;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.*;
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
import net.minecraftforge.common.Tags;
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
        buildBurnRecipes(recipeConsumer);
        buildStaffRecipes(recipeConsumer);
        buildStorageBlockRecipes(recipeConsumer);
        buildOreProcessingRecipes(recipeConsumer);
        buildAlloyingRecipes(recipeConsumer);
        buildCampfireRecipes(recipeConsumer);
        buildBlastHeatingRecipes(recipeConsumer);
        buildToolAndWeaponRecipes(recipeConsumer);
        buildShapedRecipes(recipeConsumer);
        buildAnvilRecipes(recipeConsumer);
        buildSmithingRecipes(recipeConsumer);
        buildSlackTubRecipes(recipeConsumer);
        buildBloomRecipes(recipeConsumer);
        removeSomeRecipes(recipeConsumer);
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

    protected void buildSlackTubRecipes(@NotNull Consumer<FinishedRecipe> recipeConsumer) {
        for(RegistryObject<Item> ro : MWItems.TOOL_PARTS.values()) {
            slackTubRecipeForRegOb(recipeConsumer, ro);
        }

        for(RegistryObject<Item> ro : MWItems.WEAPON_PARTS.values()) {
            slackTubRecipeForRegOb(recipeConsumer, ro);
        }

        for(RegistryObject<HeatedItem> regOb : MWItems.HEATED_INGOTS.values()) {
            slackTubRecipe(recipeConsumer, Ingredient.of(regOb.get()), 1, regOb.get().getBaseItem(), 1);
        }
    }

    private void slackTubRecipeForRegOb(@NotNull Consumer<FinishedRecipe> recipeConsumer, RegistryObject<Item> regOb) {
        if (regOb.get() instanceof HeatedItem item) {
            slackTubRecipe(recipeConsumer, Ingredient.of(item), 1, item.getBaseItem(), 1);
        }
    }

    protected void buildBloomRecipes(@NotNull Consumer<FinishedRecipe> recipeConsumer) {
        new BloomRecipeBuilder(
                Ingredient.of(Items.RAW_IRON, Items.IRON_ORE, Items.DEEPSLATE_IRON_ORE),
                new ItemStack(Items.IRON_NUGGET, 9),
                Items.IRON_INGOT, 9,
                3,
                new ItemStack(MWItems.STEEL_NUGGET.get(), 1),
                MWItems.STEEL_INGOT.get(), 9,
                1
        )
                .unlockedBy("has_iron_ore", has(Items.IRON_ORE))
                .unlockedBy("has_deepslate_iron_ore", has(Items.DEEPSLATE_IRON_ORE))
                .unlockedBy("has_raw_iron", has(Items.RAW_IRON))
                .save(recipeConsumer, new ResourceLocation(MedievalWorlds.MOD_ID, "iron_blooming"));
    }

    protected void buildBurnRecipes(@NotNull Consumer<FinishedRecipe> recipeConsumer) {
        burnRecipe(recipeConsumer, "medievalworlds:charcoal_log", "minecraft:logs_that_burn");
        burnRecipe(recipeConsumer, "medievalworlds:charcoal_planks", "minecraft:planks");
    }

    protected void buildShapedRecipes(@NotNull Consumer<FinishedRecipe> recipeConsumer) {
        helmetRecipe(recipeConsumer, MWItems.BRONZE_HELMET.get(), MWItems.TOOL_PARTS.get("bronze_armor_plate").get());
        chestplateRecipe(recipeConsumer, MWItems.BRONZE_CHESTPLATE.get(), MWItems.TOOL_PARTS.get("bronze_armor_plate").get());
        leggingsRecipe(recipeConsumer, MWItems.BRONZE_LEGGINGS.get(), MWItems.TOOL_PARTS.get("bronze_armor_plate").get());
        bootsRecipe(recipeConsumer, MWItems.BRONZE_BOOTS.get(), MWItems.TOOL_PARTS.get("bronze_armor_plate").get());

        helmetRecipe(recipeConsumer, MWItems.SILVER_HELMET.get(), MWItems.TOOL_PARTS.get("silver_armor_plate").get());
        chestplateRecipe(recipeConsumer, MWItems.SILVER_CHESTPLATE.get(), MWItems.TOOL_PARTS.get("silver_armor_plate").get());
        leggingsRecipe(recipeConsumer, MWItems.SILVER_LEGGINGS.get(), MWItems.TOOL_PARTS.get("silver_armor_plate").get());
        bootsRecipe(recipeConsumer, MWItems.SILVER_BOOTS.get(), MWItems.TOOL_PARTS.get("silver_armor_plate").get());

        helmetRecipe(recipeConsumer, MWItems.STEEL_HELMET.get(), MWItems.TOOL_PARTS.get("steel_armor_plate").get());
        chestplateRecipe(recipeConsumer, MWItems.STEEL_CHESTPLATE.get(), MWItems.TOOL_PARTS.get("steel_armor_plate").get());
        leggingsRecipe(recipeConsumer, MWItems.STEEL_LEGGINGS.get(), MWItems.TOOL_PARTS.get("steel_armor_plate").get());
        bootsRecipe(recipeConsumer, MWItems.STEEL_BOOTS.get(), MWItems.TOOL_PARTS.get("steel_armor_plate").get());

        helmetRecipe(recipeConsumer, Items.IRON_HELMET, MWItems.TOOL_PARTS.get("iron_armor_plate").get());
        chestplateRecipe(recipeConsumer, Items.IRON_CHESTPLATE, MWItems.TOOL_PARTS.get("iron_armor_plate").get());
        leggingsRecipe(recipeConsumer, Items.IRON_LEGGINGS, MWItems.TOOL_PARTS.get("iron_armor_plate").get());
        bootsRecipe(recipeConsumer, Items.IRON_BOOTS, MWItems.TOOL_PARTS.get("iron_armor_plate").get());

        helmetRecipe(recipeConsumer, Items.GOLDEN_HELMET, MWItems.TOOL_PARTS.get("gold_armor_plate").get());
        chestplateRecipe(recipeConsumer, Items.GOLDEN_CHESTPLATE, MWItems.TOOL_PARTS.get("gold_armor_plate").get());
        leggingsRecipe(recipeConsumer, Items.GOLDEN_LEGGINGS, MWItems.TOOL_PARTS.get("gold_armor_plate").get());
        bootsRecipe(recipeConsumer, Items.GOLDEN_BOOTS, MWItems.TOOL_PARTS.get("gold_armor_plate").get());

        cloakRecipe(recipeConsumer, Blocks.BROWN_WOOL, Items.RED_DYE, MWItems.CLOAK.get());
        cloakRecipe(recipeConsumer, Blocks.BLACK_WOOL, Items.BLUE_DYE, MWItems.DARK_CLOAK.get());
        cloakRecipe(recipeConsumer, Blocks.WHITE_WOOL, Items.LIME_DYE, MWItems.LIGHT_CLOAK.get());

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

        ShapedRecipeBuilder.shaped(MWBlocks.SIMPLE_BLOOMERY.get())
                .define('#', Blocks.CLAY)
                .pattern("# #")
                .pattern("# #")
                .pattern("###")
                .unlockedBy("has_clay", has(Blocks.CLAY))
                .save(recipeConsumer, new ResourceLocation(MedievalWorlds.MOD_ID, "bloomery"));

        ShapedRecipeBuilder.shaped(Blocks.SMITHING_TABLE)
                .define('#', ItemTags.PLANKS)
                .define('I', MWItems.BRONZE_INGOT.get())
                .pattern("II")
                .pattern("##")
                .pattern("##")
                .unlockedBy("has_planks", has(ItemTags.PLANKS))
                .unlockedBy("has_bronze", has(MWItems.BRONZE_INGOT.get()))
                .save(recipeConsumer, new ResourceLocation(MedievalWorlds.MOD_ID, "smithing_table"));

        ShapedRecipeBuilder.shaped(MWItems.GAMBESON.get())
                .define('W', ItemTags.WOOL)
                .define('L', Items.LEATHER)
                .pattern("L L")
                .pattern("WLW")
                .pattern("WLW")
                .unlockedBy(getItemName(Items.LEATHER), has(Items.LEATHER))
                .unlockedBy("wool", has(ItemTags.WOOL))
                .save(recipeConsumer, new ResourceLocation(MedievalWorlds.MOD_ID, "gambeson"));

        ShapedRecipeBuilder.shaped(MWItems.COIF.get())
                .define('W', ItemTags.WOOL)
                .define('L', Items.LEATHER)
                .pattern("LWL")
                .pattern("W W")
                .unlockedBy(getItemName(Items.LEATHER), has(Items.LEATHER))
                .unlockedBy("wool", has(ItemTags.WOOL))
                .save(recipeConsumer, new ResourceLocation(MedievalWorlds.MOD_ID, "coif"));

        ShapedRecipeBuilder.shaped(MWItems.ALLOY_FURNACE.get())
                .define('B', Items.BRICKS)
                .pattern("BBB")
                .pattern("B B")
                .pattern("BBB")
                .unlockedBy("bricks", has(Items.BRICKS))
                .save(recipeConsumer, new ResourceLocation(MedievalWorlds.MOD_ID, "alloy_furnace"));

        ShapedRecipeBuilder.shaped(MWItems.SLACK_TUB.get())
                .define('W', ItemTags.WOODEN_SLABS)
                .pattern("W W")
                .pattern("WWW")
                .unlockedBy("wooden_slab", has(ItemTags.WOODEN_SLABS))
                .save(recipeConsumer, new ResourceLocation(MedievalWorlds.MOD_ID, "slack_tub"));

        ShapedRecipeBuilder.shaped(MWItems.STEEL_ANVIL.get())
                .define('B', MWTags.Items.STORAGE_BLOCKS_STEEL)
                .define('I', MWTags.Items.INGOTS_STEEL)
                .pattern("BBB")
                .pattern(" I ")
                .pattern("III")
                .unlockedBy("steel_block", has(MWTags.Items.STORAGE_BLOCKS_STEEL))
                .save(recipeConsumer, new ResourceLocation(MedievalWorlds.MOD_ID, "steel_anvil"));

        ShapedRecipeBuilder.shaped(MWItems.QUIVER.get())
                .define('#', Tags.Items.LEATHER)
                .define('S', Tags.Items.STRING)
                .pattern(" S#")
                .pattern("S #")
                .pattern(" S#")
                .unlockedBy("leather_and_string", has(Tags.Items.LEATHER, Tags.Items.STRING))
                .save(recipeConsumer, new ResourceLocation(MedievalWorlds.MOD_ID, "quiver"));

        arrowCrafting(recipeConsumer, MWItems.TOOL_PARTS.get("gold_arrow_head").get(), 4);
        arrowCrafting(recipeConsumer, MWItems.TOOL_PARTS.get("silver_arrow_head").get(), 4);
        arrowCrafting(recipeConsumer, MWItems.TOOL_PARTS.get("bronze_arrow_head").get(), 6);
        arrowCrafting(recipeConsumer, MWItems.TOOL_PARTS.get("iron_arrow_head").get(), 8);
        arrowCrafting(recipeConsumer, MWItems.TOOL_PARTS.get("steel_arrow_head").get(), 10);
        arrowCrafting(recipeConsumer, MWItems.TOOL_PARTS.get("netherite_arrow_head").get(), 12);
    }

    @SuppressWarnings("SameParameterValue")
    protected static InventoryChangeTrigger.TriggerInstance has(@NotNull TagKey<Item> tag1, @NotNull TagKey<Item> tag2) {
        return inventoryTrigger(ItemPredicate.Builder.item().of(tag1).build(), ItemPredicate.Builder.item().of(tag2).build());
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

        anvilRecipe(recipeConsumer, new ItemStack(MWItems.WEAPON_PARTS.get("heated_iron_hammer_head_raw").get(), 1), new ItemStack(MWItems.HEATED_IRON_INGOT.get(), 10),   "", 2);
        anvilRecipe(recipeConsumer, new ItemStack(MWItems.WEAPON_PARTS.get("heated_iron_long_axe_head").get(), 1),   new ItemStack(MWItems.HEATED_IRON_INGOT.get(), 10),   "", 2);
        anvilRecipe(recipeConsumer, new ItemStack(MWItems.WEAPON_PARTS.get("heated_iron_longsword_blade").get(), 1), new ItemStack(MWItems.HEATED_IRON_INGOT.get(), 10),   "", 2);

        anvilRecipe(recipeConsumer, new ItemStack(MWItems.WEAPON_PARTS.get("heated_silver_hammer_head_raw").get(), 1), new ItemStack(MWItems.HEATED_SILVER_INGOT.get(), 10), "", 2);
        anvilRecipe(recipeConsumer, new ItemStack(MWItems.WEAPON_PARTS.get("heated_silver_long_axe_head").get(), 1),   new ItemStack(MWItems.HEATED_SILVER_INGOT.get(), 10), "", 2);
        anvilRecipe(recipeConsumer, new ItemStack(MWItems.WEAPON_PARTS.get("heated_silver_longsword_blade").get(), 1), new ItemStack(MWItems.HEATED_SILVER_INGOT.get(), 10), "", 2);

        anvilRecipe(recipeConsumer, new ItemStack(MWItems.WEAPON_PARTS.get("heated_steel_hammer_head_raw").get(), 1), new ItemStack(MWItems.HEATED_STEEL_INGOT.get(), 10), "", 2);
        anvilRecipe(recipeConsumer, new ItemStack(MWItems.WEAPON_PARTS.get("heated_steel_long_axe_head").get(), 1),   new ItemStack(MWItems.HEATED_STEEL_INGOT.get(), 10), "", 2);
        anvilRecipe(recipeConsumer, new ItemStack(MWItems.WEAPON_PARTS.get("heated_steel_longsword_blade").get(), 1), new ItemStack(MWItems.HEATED_STEEL_INGOT.get(), 10), "", 2);

        anvilRecipe(recipeConsumer, new ItemStack(MWItems.HEATED_IRON_INGOT.get(), 10), new ItemStack(MWItems.WEAPON_PARTS.get("heated_iron_hammer_head_raw").get(), 1), "_from_iron_hammer_head_raw", 2);
        anvilRecipe(recipeConsumer, new ItemStack(MWItems.HEATED_IRON_INGOT.get(), 10), new ItemStack(MWItems.WEAPON_PARTS.get("heated_iron_long_axe_head").get(), 1),   "_from_iron_long_axe_head", 2);
        anvilRecipe(recipeConsumer, new ItemStack(MWItems.HEATED_IRON_INGOT.get(), 10), new ItemStack(MWItems.WEAPON_PARTS.get("heated_iron_longsword_blade").get(), 1), "_from_iron_longsword_blade", 2);

        anvilRecipe(recipeConsumer, new ItemStack(MWItems.HEATED_SILVER_INGOT.get(), 10), new ItemStack(MWItems.WEAPON_PARTS.get("heated_silver_hammer_head_raw").get(), 1), "_from_silver_hammer_head_raw", 2);
        anvilRecipe(recipeConsumer, new ItemStack(MWItems.HEATED_SILVER_INGOT.get(), 10), new ItemStack(MWItems.WEAPON_PARTS.get("heated_silver_long_axe_head").get(), 1),   "_from_silver_long_axe_head", 2);
        anvilRecipe(recipeConsumer, new ItemStack(MWItems.HEATED_SILVER_INGOT.get(), 10), new ItemStack(MWItems.WEAPON_PARTS.get("heated_silver_longsword_blade").get(), 1), "_from_silver_longsword_blade", 2);

        anvilRecipe(recipeConsumer, new ItemStack(MWItems.HEATED_STEEL_INGOT.get(), 10), new ItemStack(MWItems.WEAPON_PARTS.get("heated_steel_hammer_head_raw").get(), 1), "_from_steel_hammer_head_raw", 2);
        anvilRecipe(recipeConsumer, new ItemStack(MWItems.HEATED_STEEL_INGOT.get(), 10), new ItemStack(MWItems.WEAPON_PARTS.get("heated_steel_long_axe_head").get(), 1),   "_from_steel_long_axe_head", 2);
        anvilRecipe(recipeConsumer, new ItemStack(MWItems.HEATED_STEEL_INGOT.get(), 10), new ItemStack(MWItems.WEAPON_PARTS.get("heated_steel_longsword_blade").get(), 1), "_from_steel_longsword_blade", 2);

        anvilRecipe(recipeConsumer, new ItemStack(MWItems.TOOL_PARTS.get("heated_bronze_arrow_head").get(), 8), new ItemStack(MWItems.HEATED_BRONZE_INGOT.get(), 1), "", 0);
        anvilRecipe(recipeConsumer, new ItemStack(MWItems.TOOL_PARTS.get("heated_iron_arrow_head").get(), 8), new ItemStack(MWItems.HEATED_IRON_INGOT.get(), 1), "", 0);
        anvilRecipe(recipeConsumer, new ItemStack(MWItems.TOOL_PARTS.get("heated_gold_arrow_head").get(), 8), new ItemStack(MWItems.HEATED_GOLD_INGOT.get(), 1), "", 1);
        anvilRecipe(recipeConsumer, new ItemStack(MWItems.TOOL_PARTS.get("heated_silver_arrow_head").get(), 8), new ItemStack(MWItems.HEATED_SILVER_INGOT.get(), 1), "", 1);
        anvilRecipe(recipeConsumer, new ItemStack(MWItems.TOOL_PARTS.get("heated_steel_arrow_head").get(), 8), new ItemStack(MWItems.HEATED_STEEL_INGOT.get(), 1), "", 1);

        anvilRecipe(recipeConsumer, new ItemStack(MWItems.HEATED_BRONZE_INGOT.get(), 1), new ItemStack(MWItems.TOOL_PARTS.get("heated_bronze_arrow_head").get(), 8), "", 0);
        anvilRecipe(recipeConsumer, new ItemStack(MWItems.HEATED_IRON_INGOT.get(), 1), new ItemStack(MWItems.TOOL_PARTS.get("heated_iron_arrow_head").get(), 8), "", 0);
        anvilRecipe(recipeConsumer, new ItemStack(MWItems.HEATED_GOLD_INGOT.get(), 1), new ItemStack(MWItems.TOOL_PARTS.get("heated_gold_arrow_head").get(), 8), "", 1);
        anvilRecipe(recipeConsumer, new ItemStack(MWItems.HEATED_SILVER_INGOT.get(), 1), new ItemStack(MWItems.TOOL_PARTS.get("heated_silver_arrow_head").get(), 8), "", 1);
        anvilRecipe(recipeConsumer, new ItemStack(MWItems.HEATED_STEEL_INGOT.get(), 1), new ItemStack(MWItems.TOOL_PARTS.get("heated_steel_arrow_head").get(), 8), "", 0);
    }

    protected void buildSmithingRecipes(@NotNull Consumer<FinishedRecipe> recipeConsumer) {
        bigToolSmithing(recipeConsumer, MWItems.WEAPON_PARTS.get("iron_hammer_head").get(), MWItems.IRON_HAMMER.get());
        bigToolSmithing(recipeConsumer, MWItems.WEAPON_PARTS.get("iron_long_axe_head").get(), MWItems.IRON_LONG_AXE.get());
        bigToolSmithing(recipeConsumer, MWItems.WEAPON_PARTS.get("iron_longsword_blade").get(), MWItems.IRON_LONGSWORD.get());
        smithingRecipe(recipeConsumer, new ItemStack(Items.IRON_BOOTS, 1), new ItemStack(MWItems.TOOL_PARTS.get("heated_iron_armor_plate").get(), 8), new ItemStack(MWItems.IRON_KNIGHT_BOOTS.get(), 1), "");
        smithingRecipe(recipeConsumer, new ItemStack(Items.IRON_LEGGINGS, 1), new ItemStack(MWItems.TOOL_PARTS.get("heated_iron_armor_plate").get(), 8), new ItemStack(MWItems.IRON_KNIGHT_LEGGINGS.get(), 1), "");
        smithingRecipe(recipeConsumer, new ItemStack(Items.IRON_CHESTPLATE, 1), new ItemStack(MWItems.TOOL_PARTS.get("heated_iron_armor_plate").get(), 8), new ItemStack(MWItems.IRON_KNIGHT_CHESTPLATE.get(), 1), "");
        smithingRecipe(recipeConsumer, new ItemStack(Items.IRON_HELMET, 1), new ItemStack(MWItems.TOOL_PARTS.get("heated_iron_armor_plate").get(), 8), new ItemStack(MWItems.IRON_KNIGHT_HELMET.get(), 1), "");

        bigToolSmithing(recipeConsumer, MWItems.WEAPON_PARTS.get("netherite_hammer_head").get(), MWItems.NETHERITE_HAMMER.get());
        bigToolSmithing(recipeConsumer, MWItems.WEAPON_PARTS.get("netherite_long_axe_head").get(), MWItems.NETHERITE_LONG_AXE.get());
        bigToolSmithing(recipeConsumer, MWItems.WEAPON_PARTS.get("netherite_longsword_blade").get(), MWItems.NETHERITE_LONGSWORD.get());

        bigToolSmithing(recipeConsumer, MWItems.WEAPON_PARTS.get("silver_hammer_head").get(), MWItems.SILVER_HAMMER.get());
        bigToolSmithing(recipeConsumer, MWItems.WEAPON_PARTS.get("silver_long_axe_head").get(), MWItems.SILVER_LONG_AXE.get());
        bigToolSmithing(recipeConsumer, MWItems.WEAPON_PARTS.get("silver_longsword_blade").get(), MWItems.SILVER_LONGSWORD.get());
        smithingRecipe(recipeConsumer, new ItemStack(MWItems.SILVER_BOOTS.get(), 1), new ItemStack(MWItems.TOOL_PARTS.get("heated_silver_armor_plate").get(), 8), new ItemStack(MWItems.SILVER_KNIGHT_BOOTS.get(), 1), "");
        smithingRecipe(recipeConsumer, new ItemStack(MWItems.SILVER_LEGGINGS.get(), 1), new ItemStack(MWItems.TOOL_PARTS.get("heated_silver_armor_plate").get(), 8), new ItemStack(MWItems.SILVER_KNIGHT_LEGGINGS.get(), 1), "");
        smithingRecipe(recipeConsumer, new ItemStack(MWItems.SILVER_CHESTPLATE.get(), 1), new ItemStack(MWItems.TOOL_PARTS.get("heated_silver_armor_plate").get(), 8), new ItemStack(MWItems.SILVER_KNIGHT_CHESTPLATE.get(), 1), "");
        smithingRecipe(recipeConsumer, new ItemStack(MWItems.SILVER_HELMET.get(), 1), new ItemStack(MWItems.TOOL_PARTS.get("heated_silver_armor_plate").get(), 8), new ItemStack(MWItems.SILVER_KNIGHT_HELMET.get(), 1), "");

        bigToolSmithing(recipeConsumer, MWItems.WEAPON_PARTS.get("steel_hammer_head").get(), MWItems.STEEL_HAMMER.get());
        bigToolSmithing(recipeConsumer, MWItems.WEAPON_PARTS.get("steel_long_axe_head").get(), MWItems.STEEL_LONG_AXE.get());
        bigToolSmithing(recipeConsumer, MWItems.WEAPON_PARTS.get("steel_longsword_blade").get(), MWItems.STEEL_LONGSWORD.get());
        smithingRecipe(recipeConsumer, new ItemStack(MWItems.STEEL_BOOTS.get(), 1), new ItemStack(MWItems.TOOL_PARTS.get("heated_steel_armor_plate").get(), 8), new ItemStack(MWItems.STEEL_KNIGHT_BOOTS.get(), 1), "");
        smithingRecipe(recipeConsumer, new ItemStack(MWItems.STEEL_LEGGINGS.get(), 1), new ItemStack(MWItems.TOOL_PARTS.get("heated_steel_armor_plate").get(), 8), new ItemStack(MWItems.STEEL_KNIGHT_LEGGINGS.get(), 1), "");
        smithingRecipe(recipeConsumer, new ItemStack(MWItems.STEEL_CHESTPLATE.get(), 1), new ItemStack(MWItems.TOOL_PARTS.get("heated_steel_armor_plate").get(), 8), new ItemStack(MWItems.STEEL_KNIGHT_CHESTPLATE.get(), 1), "");
        smithingRecipe(recipeConsumer, new ItemStack(MWItems.STEEL_HELMET.get(), 1), new ItemStack(MWItems.TOOL_PARTS.get("heated_steel_armor_plate").get(), 8), new ItemStack(MWItems.STEEL_KNIGHT_HELMET.get(), 1), "");

        toolSmithing(recipeConsumer, MWItems.TOOL_PARTS.get("bronze_axe_head").get(), MWItems.BRONZE_AXE.get());
        toolSmithing(recipeConsumer, MWItems.TOOL_PARTS.get("bronze_hoe_head").get(), MWItems.BRONZE_HOE.get());
        toolSmithing(recipeConsumer, MWItems.TOOL_PARTS.get("bronze_pickaxe_head").get(), MWItems.BRONZE_PICKAXE.get());
        toolSmithing(recipeConsumer, MWItems.TOOL_PARTS.get("bronze_shovel_head").get(), MWItems.BRONZE_SHOVEL.get());
        toolSmithing(recipeConsumer, MWItems.TOOL_PARTS.get("bronze_sword_blade").get(), MWItems.BRONZE_SWORD.get());

        toolSmithing(recipeConsumer, MWItems.TOOL_PARTS.get("gold_axe_head").get(), Items.GOLDEN_AXE);
        toolSmithing(recipeConsumer, MWItems.TOOL_PARTS.get("gold_hoe_head").get(), Items.GOLDEN_HOE);
        toolSmithing(recipeConsumer, MWItems.TOOL_PARTS.get("gold_pickaxe_head").get(), Items.GOLDEN_PICKAXE);
        toolSmithing(recipeConsumer, MWItems.TOOL_PARTS.get("gold_shovel_head").get(), Items.GOLDEN_SHOVEL);
        toolSmithing(recipeConsumer, MWItems.TOOL_PARTS.get("gold_sword_blade").get(), Items.GOLDEN_SWORD);

        toolSmithing(recipeConsumer, MWItems.TOOL_PARTS.get("iron_axe_head").get(), Items.IRON_AXE);
        toolSmithing(recipeConsumer, MWItems.TOOL_PARTS.get("iron_hoe_head").get(), Items.IRON_HOE);
        toolSmithing(recipeConsumer, MWItems.TOOL_PARTS.get("iron_pickaxe_head").get(), Items.IRON_PICKAXE);
        toolSmithing(recipeConsumer, MWItems.TOOL_PARTS.get("iron_shovel_head").get(), Items.IRON_SHOVEL);
        toolSmithing(recipeConsumer, MWItems.TOOL_PARTS.get("iron_sword_blade").get(), Items.IRON_SWORD);
        partSmithing(recipeConsumer, MWItems.WEAPON_PARTS.get("iron_hammer_head_raw").get(), MWItems.HEATED_GOLD_INGOT.get(), MWItems.WEAPON_PARTS.get("iron_hammer_head").get());

        toolSmithing(recipeConsumer, MWItems.TOOL_PARTS.get("silver_axe_head").get(), MWItems.SILVER_AXE.get());
        toolSmithing(recipeConsumer, MWItems.TOOL_PARTS.get("silver_hoe_head").get(), MWItems.SILVER_HOE.get());
        toolSmithing(recipeConsumer, MWItems.TOOL_PARTS.get("silver_pickaxe_head").get(), MWItems.SILVER_PICKAXE.get());
        toolSmithing(recipeConsumer, MWItems.TOOL_PARTS.get("silver_shovel_head").get(), MWItems.SILVER_SHOVEL.get());
        toolSmithing(recipeConsumer, MWItems.TOOL_PARTS.get("silver_sword_blade").get(), MWItems.SILVER_SWORD.get());
        partSmithing(recipeConsumer, MWItems.WEAPON_PARTS.get("silver_hammer_head_raw").get(), MWItems.HEATED_GOLD_INGOT.get(), MWItems.WEAPON_PARTS.get("silver_hammer_head").get());

        toolSmithing(recipeConsumer, MWItems.TOOL_PARTS.get("steel_axe_head").get(), MWItems.STEEL_AXE.get());
        toolSmithing(recipeConsumer, MWItems.TOOL_PARTS.get("steel_hoe_head").get(), MWItems.STEEL_HOE.get());
        toolSmithing(recipeConsumer, MWItems.TOOL_PARTS.get("steel_pickaxe_head").get(), MWItems.STEEL_PICKAXE.get());
        toolSmithing(recipeConsumer, MWItems.TOOL_PARTS.get("steel_shovel_head").get(), MWItems.STEEL_SHOVEL.get());
        toolSmithing(recipeConsumer, MWItems.TOOL_PARTS.get("steel_sword_blade").get(), MWItems.STEEL_SWORD.get());
        partSmithing(recipeConsumer, MWItems.WEAPON_PARTS.get("steel_hammer_head_raw").get(), MWItems.HEATED_GOLD_INGOT.get(), MWItems.WEAPON_PARTS.get("steel_hammer_head").get());

        toolSmithing(recipeConsumer, MWItems.TOOL_PARTS.get("netherite_axe_head").get(), Items.NETHERITE_AXE);
        toolSmithing(recipeConsumer, MWItems.TOOL_PARTS.get("netherite_hoe_head").get(), Items.NETHERITE_HOE);
        toolSmithing(recipeConsumer, MWItems.TOOL_PARTS.get("netherite_pickaxe_head").get(), Items.NETHERITE_PICKAXE);
        toolSmithing(recipeConsumer, MWItems.TOOL_PARTS.get("netherite_shovel_head").get(), Items.NETHERITE_SHOVEL);
        toolSmithing(recipeConsumer, MWItems.TOOL_PARTS.get("netherite_sword_blade").get(), Items.NETHERITE_SWORD);
        partSmithing(recipeConsumer, MWItems.WEAPON_PARTS.get("netherite_hammer_head_raw").get(), MWItems.HEATED_GOLD_INGOT.get(), MWItems.WEAPON_PARTS.get("netherite_hammer_head").get());

        netheriteSmithing(recipeConsumer, MWItems.TOOL_PARTS.get("steel_axe_head").get(), MWItems.TOOL_PARTS.get("netherite_axe_head").get());
        netheriteSmithing(recipeConsumer, MWItems.TOOL_PARTS.get("steel_hoe_head").get(), MWItems.TOOL_PARTS.get("netherite_hoe_head").get());
        netheriteSmithing(recipeConsumer, MWItems.TOOL_PARTS.get("steel_pickaxe_head").get(), MWItems.TOOL_PARTS.get("netherite_pickaxe_head").get());
        netheriteSmithing(recipeConsumer, MWItems.TOOL_PARTS.get("steel_shovel_head").get(), MWItems.TOOL_PARTS.get("netherite_shovel_head").get());
        netheriteSmithing(recipeConsumer, MWItems.TOOL_PARTS.get("steel_sword_blade").get(), MWItems.TOOL_PARTS.get("netherite_sword_blade").get());
        netheriteBigSmithing(recipeConsumer, MWItems.WEAPON_PARTS.get("steel_long_axe_head").get(), MWItems.WEAPON_PARTS.get("netherite_long_axe_head").get());
        netheriteBigSmithing(recipeConsumer, MWItems.WEAPON_PARTS.get("steel_longsword_blade").get(), MWItems.WEAPON_PARTS.get("netherite_longsword_blade").get());
        netheriteBigSmithing(recipeConsumer, MWItems.WEAPON_PARTS.get("steel_hammer_head").get(), MWItems.WEAPON_PARTS.get("netherite_hammer_head").get());
        netheriteBigSmithing(recipeConsumer, MWItems.WEAPON_PARTS.get("steel_hammer_head_raw").get(), MWItems.WEAPON_PARTS.get("netherite_hammer_head_raw").get());

        netheriteSmithing(recipeConsumer, MWItems.STEEL_HORSE_ARMOR.get(), MWItems.NETHERITE_HORSE_ARMOR.get());
        netheriteSmithing(recipeConsumer, MWItems.STEEL_FORGE_HAMMER.get(), MWItems.NETHERITE_FORGE_HAMMER.get());
        netheriteSmithing(recipeConsumer, MWItems.STEEL_KNIGHT_BOOTS.get(), MWItems.NETHERITE_KNIGHT_BOOTS.get());
        netheriteSmithing(recipeConsumer, MWItems.STEEL_KNIGHT_LEGGINGS.get(), MWItems.NETHERITE_KNIGHT_LEGGINGS.get());
        netheriteSmithing(recipeConsumer, MWItems.STEEL_KNIGHT_CHESTPLATE.get(), MWItems.NETHERITE_KNIGHT_CHESTPLATE.get());
        netheriteSmithing(recipeConsumer, MWItems.STEEL_KNIGHT_HELMET.get(), MWItems.NETHERITE_KNIGHT_HELMET.get());
        netheriteSmithing(recipeConsumer, MWItems.STEEL_AXE.get(), Items.NETHERITE_AXE);
        netheriteSmithing(recipeConsumer, MWItems.STEEL_HOE.get(), Items.NETHERITE_HOE);
        netheriteSmithing(recipeConsumer, MWItems.STEEL_PICKAXE.get(), Items.NETHERITE_PICKAXE);
        netheriteSmithing(recipeConsumer, MWItems.STEEL_SHOVEL.get(), Items.NETHERITE_SHOVEL);
        netheriteSmithing(recipeConsumer, MWItems.STEEL_SWORD.get(), Items.NETHERITE_SWORD);
        netheriteBigSmithing(recipeConsumer, MWItems.STEEL_LONGSWORD.get(), MWItems.NETHERITE_LONGSWORD.get());
        netheriteBigSmithing(recipeConsumer, MWItems.STEEL_LONG_AXE.get(), MWItems.NETHERITE_LONG_AXE.get());
        netheriteBigSmithing(recipeConsumer, MWItems.STEEL_HAMMER.get(), MWItems.NETHERITE_HAMMER.get());
        //netheriteSmithing(recipeConsumer, MWItems.STEEL_LANCE.get(), MWItems.NETHERITE_LANCE.get());

        smithingRecipe(recipeConsumer, new ItemStack(MWItems.TOOL_PARTS.get("steel_arrow_head").get(), 16), new ItemStack(Items.NETHERITE_INGOT, 1), new ItemStack(MWItems.TOOL_PARTS.get("heated_netherite_arrow_head").get(), 16), "_from_smithing");
    }

    protected void removeSomeRecipes(@NotNull Consumer<FinishedRecipe> recipeConsumer) {
        removeRecipe(recipeConsumer, new ResourceLocation("golden_axe"), Items.GOLDEN_AXE);
        removeRecipe(recipeConsumer, new ResourceLocation("golden_hoe"), Items.GOLDEN_HOE);
        removeRecipe(recipeConsumer, new ResourceLocation("golden_pickaxe"), Items.GOLDEN_PICKAXE);
        removeRecipe(recipeConsumer, new ResourceLocation("golden_shovel"), Items.GOLDEN_SHOVEL);
        removeRecipe(recipeConsumer, new ResourceLocation("golden_sword"), Items.GOLDEN_SWORD);
        removeRecipe(recipeConsumer, new ResourceLocation("golden_boots"), Items.GOLDEN_BOOTS);
        removeRecipe(recipeConsumer, new ResourceLocation("golden_chestplate"), Items.GOLDEN_CHESTPLATE);
        removeRecipe(recipeConsumer, new ResourceLocation("golden_leggings"), Items.GOLDEN_LEGGINGS);
        removeRecipe(recipeConsumer, new ResourceLocation("golden_helmet"), Items.GOLDEN_HELMET);

        removeRecipe(recipeConsumer, new ResourceLocation("iron_axe"), Items.IRON_AXE);
        removeRecipe(recipeConsumer, new ResourceLocation("iron_hoe"), Items.IRON_HOE);
        removeRecipe(recipeConsumer, new ResourceLocation("iron_pickaxe"), Items.IRON_PICKAXE);
        removeRecipe(recipeConsumer, new ResourceLocation("iron_shovel"), Items.IRON_SHOVEL);
        removeRecipe(recipeConsumer, new ResourceLocation("iron_sword"), Items.IRON_SWORD);
        removeRecipe(recipeConsumer, new ResourceLocation("iron_boots"), Items.IRON_BOOTS);
        removeRecipe(recipeConsumer, new ResourceLocation("iron_chestplate"), Items.IRON_CHESTPLATE);
        removeRecipe(recipeConsumer, new ResourceLocation("iron_leggings"), Items.IRON_LEGGINGS);
        removeRecipe(recipeConsumer, new ResourceLocation("iron_helmet"), Items.IRON_HELMET);

        removeRecipe(recipeConsumer, new ResourceLocation("iron_ingot_from_smelting_deepslate_iron_ore"), Items.IRON_INGOT);
        removeRecipe(recipeConsumer, new ResourceLocation("iron_ingot_from_smelting_iron_ore"), Items.IRON_INGOT);
        removeRecipe(recipeConsumer, new ResourceLocation("iron_ingot_from_smelting_raw_iron"), Items.IRON_INGOT);

        removeRecipe(recipeConsumer, new ResourceLocation("netherite_axe_smithing"), Items.NETHERITE_AXE);
        removeRecipe(recipeConsumer, new ResourceLocation("netherite_hoe_smithing"), Items.NETHERITE_HOE);
        removeRecipe(recipeConsumer, new ResourceLocation("netherite_pickaxe_smithing"), Items.NETHERITE_PICKAXE);
        removeRecipe(recipeConsumer, new ResourceLocation("netherite_shovel_smithing"), Items.NETHERITE_SHOVEL);
        removeRecipe(recipeConsumer, new ResourceLocation("netherite_sword_smithing"), Items.NETHERITE_SWORD);
        removeRecipe(recipeConsumer, new ResourceLocation("netherite_boots_smithing"), Items.NETHERITE_BOOTS);
        removeRecipe(recipeConsumer, new ResourceLocation("netherite_chestplate_smithing"), Items.NETHERITE_CHESTPLATE);
        removeRecipe(recipeConsumer, new ResourceLocation("netherite_leggings_smithing"), Items.NETHERITE_LEGGINGS);
        removeRecipe(recipeConsumer, new ResourceLocation("netherite_helmet_smithing"), Items.NETHERITE_HELMET);
    }

    protected static void arrowCrafting(@NotNull Consumer<FinishedRecipe> recipeConsumer, @NotNull Item arrowHead, int yield) {
        ShapedRecipeBuilder.shaped(Items.ARROW, yield)
                .define('A', Ingredient.of(arrowHead))
                .define('I', Ingredient.of(Tags.Items.RODS_WOODEN))
                .define('F', Ingredient.of(Tags.Items.FEATHERS))
                .pattern("A")
                .pattern("I")
                .pattern("F")
                .unlockedBy("has_arrow_head", has(arrowHead))
                .save(recipeConsumer, "arrow_from_" + getItemName(arrowHead));
    }

    protected static void netheriteSmithing(@NotNull Consumer<FinishedRecipe> recipeConsumer, @NotNull Item base, Item result) {
        UpgradeRecipeBuilder.smithing(Ingredient.of(base), Ingredient.of(MWItems.HEATED_NETHERITE_INGOT.get()), result).unlocks("has_netherite_ingot", has(Items.NETHERITE_INGOT)).save(recipeConsumer, MedievalWorlds.MOD_ID + ":" + getItemName(result) + "_smithing_ingot");
    }

    protected static void netheriteBigSmithing(@NotNull Consumer<FinishedRecipe> recipeConsumer, @NotNull Item base, Item result) {
        smithingRecipe(recipeConsumer, new ItemStack(base, 1), new ItemStack(MWItems.HEATED_NETHERITE_INGOT.get(), 4), new ItemStack(result, 1), "_netherite");
    }

    @SuppressWarnings("SameParameterValue")
    private static void partSmithing(Consumer<FinishedRecipe> recipeConsumer, ItemLike base, ItemLike addition, Item result) {
        UpgradeRecipeBuilder.smithing(Ingredient.of(base), Ingredient.of(addition), result).unlocks("has_base_part", has(base)).save(recipeConsumer, MedievalWorlds.MOD_ID + ":" + getItemName(result) + "_smithing");
    }

    private static void toolSmithing(Consumer<FinishedRecipe> recipeConsumer, ItemLike base, Item result) {
        UpgradeRecipeBuilder.smithing(Ingredient.of(base), Ingredient.of(Items.STICK), result).unlocks("has_base_part", has(base)).save(recipeConsumer, MedievalWorlds.MOD_ID + ":" + getItemName(result) + "_smithing");
    }

    private static void bigToolSmithing(Consumer<FinishedRecipe> recipeConsumer, ItemLike base, Item result) {
        smithingRecipe(recipeConsumer, new ItemStack(base, 1), new ItemStack(Items.STICK, 2), new ItemStack(result, 1), "");
    }

    @SuppressWarnings("SameParameterValue")
    private static void smithingRecipe(Consumer<FinishedRecipe> recipeConsumer, ItemStack base, ItemStack addition, ItemStack result, String suffix) {
        new SmithingRecipeBuilder(result, base, addition, 0)
                .unlockedBy(getItemName(base.getItem()), has(base.getItem()))
                .unlockedBy(getItemName(addition.getItem()), has(addition.getItem()))
                .save(recipeConsumer, new ResourceLocation(MedievalWorlds.MOD_ID, getItemName(result.getItem()) + "_smithing" + suffix));
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
                .shaped(staff, 1)
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
                .unlockedBy("has_fire_starter", has(MWItems.FIRE_STARTER.get()))
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
    private static void helmetRecipe(Consumer<FinishedRecipe> recipeConsumer, ItemLike helmet, ItemLike armorPlate) {
        ShapedRecipeBuilder.shaped(helmet)
                .define('X', armorPlate)
                .pattern("XXX")
                .pattern("X X")
                .unlockedBy(getItemName(armorPlate), has(armorPlate))
                .save(recipeConsumer, new ResourceLocation(MedievalWorlds.MOD_ID, Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(helmet.asItem())).getPath()));
    }

    private static void chestplateRecipe(Consumer<FinishedRecipe> recipeConsumer, ItemLike chestplate, ItemLike armorPlate) {
        ShapedRecipeBuilder.shaped(chestplate)
                .define('X', armorPlate)
                .pattern("X X")
                .pattern("XXX")
                .pattern("XXX")
                .unlockedBy(getItemName(armorPlate), has(armorPlate))
                .save(recipeConsumer, new ResourceLocation(MedievalWorlds.MOD_ID, Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(chestplate.asItem())).getPath()));
    }

    private static void leggingsRecipe(Consumer<FinishedRecipe> recipeConsumer, ItemLike leggings, ItemLike armorPlate) {
        ShapedRecipeBuilder.shaped(leggings)
                .define('X', armorPlate)
                .pattern("XXX")
                .pattern("X X")
                .pattern("X X")
                .unlockedBy(getItemName(armorPlate), has(armorPlate))
                .save(recipeConsumer, new ResourceLocation(MedievalWorlds.MOD_ID, Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(leggings.asItem())).getPath()));
    }

    private static void bootsRecipe(Consumer<FinishedRecipe> recipeConsumer, ItemLike boots, ItemLike armorPlate) {
        ShapedRecipeBuilder.shaped(boots)
                .define('X', armorPlate)
                .pattern("X X")
                .pattern("X X")
                .unlockedBy(getItemName(armorPlate), has(armorPlate))
                .save(recipeConsumer, new ResourceLocation(MedievalWorlds.MOD_ID, Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(boots.asItem())).getPath()));
    }

    protected static void cloakRecipe(Consumer<FinishedRecipe> recipeConsumer, ItemLike wool, ItemLike dye, ItemLike cloak) {
        ShapedRecipeBuilder.shaped(cloak)
                .define('W', wool)
                .define('R', dye)
                .pattern(" W ")
                .pattern("WRW")
                .pattern("WWW")
                .unlockedBy("has_wool", has(ItemTags.WOOL))
                .save(recipeConsumer, new ResourceLocation(MedievalWorlds.MOD_ID, getItemName(cloak)));
    }

    private static void removeRecipe(Consumer<FinishedRecipe> recipeConsumer, ResourceLocation id, ItemLike removedResult) {
        new RemoveRecipeBuilder(removedResult).save(recipeConsumer, id);
    }

}