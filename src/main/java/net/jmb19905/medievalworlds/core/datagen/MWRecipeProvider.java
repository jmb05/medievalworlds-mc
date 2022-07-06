package net.jmb19905.medievalworlds.core.datagen;

import com.google.common.collect.ImmutableList;
import net.jmb19905.medievalworlds.MedievalWorlds;
import net.jmb19905.medievalworlds.common.item.heated.AbstractHeatedItem;
import net.jmb19905.medievalworlds.core.datagen.custom.*;
import net.jmb19905.medievalworlds.core.registries.MWBlocks;
import net.jmb19905.medievalworlds.core.registries.MWItems;
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
import net.minecraft.world.item.crafting.SimpleCookingSerializer;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

@SuppressWarnings("SameParameterValue")
public class MWRecipeProvider extends RecipeProvider implements IConditionBuilder {
    public MWRecipeProvider(DataGenerator dataGenerator) {
        super(dataGenerator);
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    protected void buildCraftingRecipes(@NotNull Consumer<FinishedRecipe> recipeConsumer) {
        nineBlockStorageRecipes(recipeConsumer, MWItems.RUBY.get(), MWBlocks.RUBY_BLOCK.get());
        nineBlockStorageRecipes(recipeConsumer, MWItems.TIN_INGOT.get(), MWBlocks.TIN_BLOCK.get());
        nineBlockStorageRecipes(recipeConsumer, MWItems.TIN_NUGGET.get(), MWItems.TIN_INGOT.get());
        nineBlockStorageRecipes(recipeConsumer, MWItems.BRONZE_INGOT.get(), MWBlocks.BRONZE_BLOCK.get());
        nineBlockStorageRecipes(recipeConsumer, MWItems.BRONZE_NUGGET.get(), MWItems.BRONZE_INGOT.get());
        nineBlockStorageRecipes(recipeConsumer, MWItems.STEEL_INGOT.get(), MWBlocks.STEEL_BLOCK.get());
        nineBlockStorageRecipes(recipeConsumer, MWItems.STEEL_NUGGET.get(), MWItems.STEEL_INGOT.get());
        nineBlockStorageRecipes(recipeConsumer, MWItems.SILVER_INGOT.get(), MWBlocks.SILVER_BLOCK.get());
        nineBlockStorageRecipes(recipeConsumer, MWItems.SILVER_NUGGET.get(), MWItems.SILVER_INGOT.get());
        nineBlockStorageRecipes(recipeConsumer, MWItems.RAW_TIN.get(), MWBlocks.RAW_TIN_BLOCK.get());
        nineBlockStorageRecipes(recipeConsumer, MWItems.RAW_SILVER.get(), MWBlocks.RAW_SILVER_BLOCK.get());

        alloying(recipeConsumer, MWItems.BRONZE_INGOT.get(), 4, Items.COPPER_INGOT, 3, MWItems.TIN_INGOT.get(), 1, "_from_ingot");
        alloying(recipeConsumer, MWItems.BRONZE_INGOT.get(), 4, Items.RAW_COPPER, 3, MWItems.RAW_TIN.get(), 1, "_from_raw");
        alloying(recipeConsumer, Items.NETHERITE_SCRAP, 2, Items.GOLD_INGOT, 2, Items.NETHERITE_INGOT, 1, "");

        oreSmelting(recipeConsumer, ImmutableList.of(MWBlocks.TIN_ORE_BLOCK.get(), MWBlocks.DEEPSLATE_TIN_ORE_BLOCK.get(), MWItems.RAW_TIN.get()), MWItems.TIN_INGOT.get(), 0.3f, 200, "tin_ingot");
        oreSmelting(recipeConsumer, ImmutableList.of(MWBlocks.SILVER_ORE_BLOCK.get(), MWBlocks.DEEPSLATE_SILVER_ORE_BLOCK.get(), MWItems.RAW_SILVER.get()), MWItems.SILVER_INGOT.get(), 0.8f, 200, "silver_ingot");
        oreSmelting(recipeConsumer, ImmutableList.of(MWBlocks.RUBY_ORE_BLOCK.get(), MWBlocks.DEEPSLATE_RUBY_ORE_BLOCK.get()), MWItems.RUBY.get(), 0.3f, 200, "ruby");

        oreBlasting(recipeConsumer, ImmutableList.of(MWBlocks.TIN_ORE_BLOCK.get(), MWBlocks.DEEPSLATE_TIN_ORE_BLOCK.get(), MWItems.RAW_TIN.get()), MWItems.TIN_INGOT.get(), 0.3f, 200, "tin_ingot");
        oreBlasting(recipeConsumer, ImmutableList.of(MWBlocks.SILVER_ORE_BLOCK.get(), MWBlocks.DEEPSLATE_SILVER_ORE_BLOCK.get(), MWItems.RAW_SILVER.get()), MWItems.SILVER_INGOT.get(), 0.8f, 200, "silver_ingot");
        oreBlasting(recipeConsumer, ImmutableList.of(MWBlocks.RUBY_ORE_BLOCK.get(), MWBlocks.DEEPSLATE_RUBY_ORE_BLOCK.get()), MWItems.RUBY.get(), 0.3f, 200, "ruby");

        heatingBlasting(recipeConsumer, MWItems.HEATED_COPPER_INGOT.get());
        heatingBlasting(recipeConsumer, MWItems.HEATED_TIN_INGOT.get());
        heatingBlasting(recipeConsumer, MWItems.HEATED_BRONZE_INGOT.get());
        heatingBlasting(recipeConsumer, MWItems.HEATED_GOLD_INGOT.get());
        heatingBlasting(recipeConsumer, MWItems.HEATED_IRON_INGOT.get());
        heatingBlasting(recipeConsumer, MWItems.HEATED_SILVER_INGOT.get());
        heatingBlasting(recipeConsumer, MWItems.HEATED_STEEL_INGOT.get());
        heatingBlasting(recipeConsumer, MWItems.HEATED_NETHERITE_INGOT.get());

        forgeHammer(recipeConsumer, ItemTags.STONE_TOOL_MATERIALS, MWItems.STONE_FORGE_HAMMER.get(), "stone_tag");
        forgeHammer(recipeConsumer, Blocks.IRON_BLOCK, MWItems.IRON_FORGE_HAMMER.get());
        forgeHammer(recipeConsumer, MWBlocks.STEEL_BLOCK.get(), MWItems.STEEL_FORGE_HAMMER.get());

        anvilRecipe(recipeConsumer, MWItems.TOOL_PARTS.get("heated_bronze_armor_plate").get(), 1, MWItems.HEATED_BRONZE_INGOT.get(), 1, "");
        anvilRecipe(recipeConsumer, MWItems.TOOL_PARTS.get("heated_bronze_axe_head").get(), 1, MWItems.HEATED_BRONZE_INGOT.get(), 3, "");
        anvilRecipe(recipeConsumer, MWItems.TOOL_PARTS.get("heated_bronze_hoe_head").get(), 1, MWItems.HEATED_BRONZE_INGOT.get(), 2, "");
        anvilRecipe(recipeConsumer, MWItems.TOOL_PARTS.get("heated_bronze_pickaxe_head").get(), 1, MWItems.HEATED_BRONZE_INGOT.get(), 3, "");
        anvilRecipe(recipeConsumer, MWItems.TOOL_PARTS.get("heated_bronze_shovel_head").get(), 1, MWItems.HEATED_BRONZE_INGOT.get(), 1, "");
        anvilRecipe(recipeConsumer, MWItems.TOOL_PARTS.get("heated_bronze_sword_blade").get(), 1, MWItems.HEATED_BRONZE_INGOT.get(), 2, "");

        anvilRecipe(recipeConsumer, MWItems.HEATED_BRONZE_INGOT.get(), 1, MWItems.TOOL_PARTS.get("heated_bronze_armor_plate").get(), 1, "_from_armor_plate");
        anvilRecipe(recipeConsumer, MWItems.HEATED_BRONZE_INGOT.get(), 3, MWItems.TOOL_PARTS.get("heated_bronze_axe_head").get(), 1, "_from_axe_head");
        anvilRecipe(recipeConsumer, MWItems.HEATED_BRONZE_INGOT.get(), 2, MWItems.TOOL_PARTS.get("heated_bronze_hoe_head").get(), 1, "_from_hoe_head");
        anvilRecipe(recipeConsumer, MWItems.HEATED_BRONZE_INGOT.get(), 3, MWItems.TOOL_PARTS.get("heated_bronze_pickaxe_head").get(), 1, "_from_pickaxe_head");
        anvilRecipe(recipeConsumer, MWItems.HEATED_BRONZE_INGOT.get(), 1, MWItems.TOOL_PARTS.get("heated_bronze_shovel_head").get(), 1, "_from_shovel_head");
        anvilRecipe(recipeConsumer, MWItems.HEATED_BRONZE_INGOT.get(), 2, MWItems.TOOL_PARTS.get("heated_bronze_sword_blade").get(), 1, "_from_sword_blade");

        anvilRecipe(recipeConsumer, MWItems.TOOL_PARTS.get("heated_gold_armor_plate").get(), 1, MWItems.HEATED_GOLD_INGOT.get(), 1, "");
        anvilRecipe(recipeConsumer, MWItems.TOOL_PARTS.get("heated_gold_axe_head").get(), 1, MWItems.HEATED_GOLD_INGOT.get(), 3, "");
        anvilRecipe(recipeConsumer, MWItems.TOOL_PARTS.get("heated_gold_hoe_head").get(), 1, MWItems.HEATED_GOLD_INGOT.get(), 2, "");
        anvilRecipe(recipeConsumer, MWItems.TOOL_PARTS.get("heated_gold_pickaxe_head").get(), 1, MWItems.HEATED_GOLD_INGOT.get(), 3, "");
        anvilRecipe(recipeConsumer, MWItems.TOOL_PARTS.get("heated_gold_shovel_head").get(), 1, MWItems.HEATED_GOLD_INGOT.get(), 1, "");
        anvilRecipe(recipeConsumer, MWItems.TOOL_PARTS.get("heated_gold_sword_blade").get(), 1, MWItems.HEATED_GOLD_INGOT.get(), 2, "");

        anvilRecipe(recipeConsumer, MWItems.HEATED_GOLD_INGOT.get(), 1, MWItems.TOOL_PARTS.get("heated_gold_armor_plate").get(), 1, "_from_armor_plate");
        anvilRecipe(recipeConsumer, MWItems.HEATED_GOLD_INGOT.get(), 3, MWItems.TOOL_PARTS.get("heated_gold_axe_head").get(), 1, "_from_axe_head");
        anvilRecipe(recipeConsumer, MWItems.HEATED_GOLD_INGOT.get(), 2, MWItems.TOOL_PARTS.get("heated_gold_hoe_head").get(), 1, "_from_hoe_head");
        anvilRecipe(recipeConsumer, MWItems.HEATED_GOLD_INGOT.get(), 3, MWItems.TOOL_PARTS.get("heated_gold_pickaxe_head").get(), 1, "_from_pickaxe_head");
        anvilRecipe(recipeConsumer, MWItems.HEATED_GOLD_INGOT.get(), 1, MWItems.TOOL_PARTS.get("heated_gold_shovel_head").get(), 1, "_from_shovel_head");
        anvilRecipe(recipeConsumer, MWItems.HEATED_GOLD_INGOT.get(), 2, MWItems.TOOL_PARTS.get("heated_gold_sword_blade").get(), 1, "_from_sword_blade");

        anvilRecipe(recipeConsumer, MWItems.TOOL_PARTS.get("heated_iron_armor_plate").get(), 1, MWItems.HEATED_IRON_INGOT.get(), 1, "");
        anvilRecipe(recipeConsumer, MWItems.TOOL_PARTS.get("heated_iron_axe_head").get(), 1, MWItems.HEATED_IRON_INGOT.get(), 3, "");
        anvilRecipe(recipeConsumer, MWItems.TOOL_PARTS.get("heated_iron_hoe_head").get(), 1, MWItems.HEATED_IRON_INGOT.get(), 2, "");
        anvilRecipe(recipeConsumer, MWItems.TOOL_PARTS.get("heated_iron_pickaxe_head").get(), 1, MWItems.HEATED_IRON_INGOT.get(), 3, "");
        anvilRecipe(recipeConsumer, MWItems.TOOL_PARTS.get("heated_iron_shovel_head").get(), 1, MWItems.HEATED_IRON_INGOT.get(), 1, "");
        anvilRecipe(recipeConsumer, MWItems.TOOL_PARTS.get("heated_iron_sword_blade").get(), 1, MWItems.HEATED_IRON_INGOT.get(), 2, "");

        anvilRecipe(recipeConsumer, MWItems.HEATED_IRON_INGOT.get(), 1, MWItems.TOOL_PARTS.get("heated_iron_armor_plate").get(), 1, "_from_armor_plate");
        anvilRecipe(recipeConsumer, MWItems.HEATED_IRON_INGOT.get(), 3, MWItems.TOOL_PARTS.get("heated_iron_axe_head").get(), 1, "_from_axe_head");
        anvilRecipe(recipeConsumer, MWItems.HEATED_IRON_INGOT.get(), 2, MWItems.TOOL_PARTS.get("heated_iron_hoe_head").get(), 1, "_from_hoe_head");
        anvilRecipe(recipeConsumer, MWItems.HEATED_IRON_INGOT.get(), 3, MWItems.TOOL_PARTS.get("heated_iron_pickaxe_head").get(), 1, "_from_pickaxe_head");
        anvilRecipe(recipeConsumer, MWItems.HEATED_IRON_INGOT.get(), 1, MWItems.TOOL_PARTS.get("heated_iron_shovel_head").get(), 1, "_from_shovel_head");
        anvilRecipe(recipeConsumer, MWItems.HEATED_IRON_INGOT.get(), 2, MWItems.TOOL_PARTS.get("heated_iron_sword_blade").get(), 1, "_from_sword_blade");

        anvilRecipe(recipeConsumer, MWItems.TOOL_PARTS.get("heated_silver_armor_plate").get(), 1, MWItems.HEATED_SILVER_INGOT.get(), 1, "");
        anvilRecipe(recipeConsumer, MWItems.TOOL_PARTS.get("heated_silver_axe_head").get(), 1, MWItems.HEATED_SILVER_INGOT.get(), 3, "");
        anvilRecipe(recipeConsumer, MWItems.TOOL_PARTS.get("heated_silver_hoe_head").get(), 1, MWItems.HEATED_SILVER_INGOT.get(), 2, "");
        anvilRecipe(recipeConsumer, MWItems.TOOL_PARTS.get("heated_silver_pickaxe_head").get(), 1, MWItems.HEATED_SILVER_INGOT.get(), 3, "");
        anvilRecipe(recipeConsumer, MWItems.TOOL_PARTS.get("heated_silver_shovel_head").get(), 1, MWItems.HEATED_SILVER_INGOT.get(), 1, "");
        anvilRecipe(recipeConsumer, MWItems.TOOL_PARTS.get("heated_silver_sword_blade").get(), 1, MWItems.HEATED_SILVER_INGOT.get(), 2, "");

        anvilRecipe(recipeConsumer, MWItems.HEATED_SILVER_INGOT.get(), 1, MWItems.TOOL_PARTS.get("heated_silver_armor_plate").get(), 1, "_from_armor_plate");
        anvilRecipe(recipeConsumer, MWItems.HEATED_SILVER_INGOT.get(), 3, MWItems.TOOL_PARTS.get("heated_silver_axe_head").get(), 1, "_from_axe_head");
        anvilRecipe(recipeConsumer, MWItems.HEATED_SILVER_INGOT.get(), 2, MWItems.TOOL_PARTS.get("heated_silver_hoe_head").get(), 1, "_from_hoe_head");
        anvilRecipe(recipeConsumer, MWItems.HEATED_SILVER_INGOT.get(), 3, MWItems.TOOL_PARTS.get("heated_silver_pickaxe_head").get(), 1, "_from_pickaxe_head");
        anvilRecipe(recipeConsumer, MWItems.HEATED_SILVER_INGOT.get(), 1, MWItems.TOOL_PARTS.get("heated_silver_shovel_head").get(), 1, "_from_shovel_head");
        anvilRecipe(recipeConsumer, MWItems.HEATED_SILVER_INGOT.get(), 2, MWItems.TOOL_PARTS.get("heated_silver_sword_blade").get(), 1, "_from_sword_blade");

        anvilRecipe(recipeConsumer, MWItems.TOOL_PARTS.get("heated_steel_armor_plate").get(), 1, MWItems.HEATED_STEEL_INGOT.get(), 1, "");
        anvilRecipe(recipeConsumer, MWItems.TOOL_PARTS.get("heated_steel_axe_head").get(), 1, MWItems.HEATED_STEEL_INGOT.get(), 3, "");
        anvilRecipe(recipeConsumer, MWItems.TOOL_PARTS.get("heated_steel_hoe_head").get(), 1, MWItems.HEATED_STEEL_INGOT.get(), 2, "");
        anvilRecipe(recipeConsumer, MWItems.TOOL_PARTS.get("heated_steel_pickaxe_head").get(), 1, MWItems.HEATED_STEEL_INGOT.get(), 3, "");
        anvilRecipe(recipeConsumer, MWItems.TOOL_PARTS.get("heated_steel_shovel_head").get(), 1, MWItems.HEATED_STEEL_INGOT.get(), 1, "");
        anvilRecipe(recipeConsumer, MWItems.TOOL_PARTS.get("heated_steel_sword_blade").get(), 1, MWItems.HEATED_STEEL_INGOT.get(), 2, "");

        anvilRecipe(recipeConsumer, MWItems.HEATED_STEEL_INGOT.get(), 1, MWItems.TOOL_PARTS.get("heated_steel_armor_plate").get(), 1, "_from_armor_plate");
        anvilRecipe(recipeConsumer, MWItems.HEATED_STEEL_INGOT.get(), 3, MWItems.TOOL_PARTS.get("heated_steel_axe_head").get(), 1, "_from_axe_head");
        anvilRecipe(recipeConsumer, MWItems.HEATED_STEEL_INGOT.get(), 2, MWItems.TOOL_PARTS.get("heated_steel_hoe_head").get(), 1, "_from_hoe_head");
        anvilRecipe(recipeConsumer, MWItems.HEATED_STEEL_INGOT.get(), 3, MWItems.TOOL_PARTS.get("heated_steel_pickaxe_head").get(), 1, "_from_pickaxe_head");
        anvilRecipe(recipeConsumer, MWItems.HEATED_STEEL_INGOT.get(), 1, MWItems.TOOL_PARTS.get("heated_steel_shovel_head").get(), 1, "_from_shovel_head");
        anvilRecipe(recipeConsumer, MWItems.HEATED_STEEL_INGOT.get(), 2, MWItems.TOOL_PARTS.get("heated_steel_sword_blade").get(), 1, "_from_sword_blade");

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

        ShapedRecipeBuilder.shaped(MWItems.STEEL_KETTLE_HELMET.get())
                .define('S', MWItems.TOOL_PARTS.get("steel_armor_plate").get())
                .pattern(" S ")
                .pattern("SSS")
                .pattern(" S ")
                .unlockedBy(getHasName(MWItems.TOOL_PARTS.get("steel_armor_plate").get()), has(MWItems.TOOL_PARTS.get("steel_armor_plate").get()))
                .save(recipeConsumer, new ResourceLocation(MedievalWorlds.MOD_ID, "steel_kettle_helmet"));

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

        ShapedRecipeBuilder.shaped(MWBlocks.ALLOY_FURNACE_BLOCK.get())
                .define('#', Blocks.BRICKS)
                .pattern("###")
                .pattern("# #")
                .pattern("###")
                .unlockedBy("has_bricks", has(Blocks.BRICKS))
                .save(recipeConsumer, new ResourceLocation(MedievalWorlds.MOD_ID, "alloy_furnace"));

        ShapedRecipeBuilder.shaped(MWBlocks.BELLOWS.get())
                .define('#', ItemTags.PLANKS)
                .define('L', Items.LEATHER)
                .pattern(" ##")
                .pattern("#LL")
                .pattern("###")
                .unlockedBy("has_planks", has(ItemTags.PLANKS))
                .unlockedBy("has_leather", has(Items.LEATHER))
                .save(recipeConsumer, new ResourceLocation(MedievalWorlds.MOD_ID, "bellows"));

        ShapedRecipeBuilder.shaped(MWBlocks.BLOOMERY.get())
                .define('#', Blocks.CLAY)
                .pattern("# #")
                .pattern("# #")
                .pattern("###")
                .unlockedBy("has_clay", has(Blocks.CLAY))
                .save(recipeConsumer, new ResourceLocation(MedievalWorlds.MOD_ID, "bloomery"));

        ShapedRecipeBuilder.shaped(MWBlocks.BRICK_CHIMNEY.get())
                .define('#', Blocks.BRICKS)
                .pattern("# #")
                .pattern("# #")
                .pattern("# #")
                .unlockedBy("has_bricks", has(Blocks.BRICKS))
                .save(recipeConsumer, new ResourceLocation(MedievalWorlds.MOD_ID, "brick_chimney"));

        ShapedRecipeBuilder.shaped(MWBlocks.SLACK_TUB_BLOCK.get())
                .define('#', ItemTags.PLANKS)
                .pattern("# #")
                .pattern("# #")
                .pattern("###")
                .unlockedBy("has_planks", has(ItemTags.PLANKS))
                .save(recipeConsumer, new ResourceLocation(MedievalWorlds.MOD_ID, "slack_tub"));

        ShapedRecipeBuilder.shaped(Blocks.SMITHING_TABLE)
                .define('#', ItemTags.PLANKS)
                .define('I', MWItems.BRONZE_INGOT.get())
                .pattern("II")
                .pattern("##")
                .pattern("##")
                .unlockedBy("has_planks", has(ItemTags.PLANKS))
                .unlockedBy("has_bronze", has(MWItems.BRONZE_INGOT.get()))
                .save(recipeConsumer, new ResourceLocation(MedievalWorlds.MOD_ID, "smithing_table"));

        new BloomRecipeBuilder(Ingredient.of(Items.RAW_IRON, Items.IRON_ORE, Items.DEEPSLATE_IRON_ORE), Items.IRON_NUGGET, 9, Items.IRON_INGOT, 9, 3, MWItems.STEEL_NUGGET.get(), 1, MWItems.STEEL_INGOT.get(), 9, 1)
                .unlockedBy("has_iron_ore", has(Items.IRON_ORE))
                .unlockedBy("has_deepslate_iron_ore", has(Items.DEEPSLATE_IRON_ORE))
                .unlockedBy("has_raw_iron", has(Items.RAW_IRON))
                .save(recipeConsumer, new ResourceLocation(MedievalWorlds.MOD_ID, "iron_blooming"));

        burnRecipe(recipeConsumer, "medievalworlds:charcoal_log", "minecraft:logs_that_burn");
        burnRecipe(recipeConsumer, "medievalworlds:charcoal_planks", "minecraft:planks");

        campfireCookingRecipe(recipeConsumer, Items.CLAY_BALL, 1000, Items.BRICK);
        campfireCookingRecipe(recipeConsumer, MWItems.BRONZE_INGOT.get(), 1800, MWItems.HEATED_BRONZE_INGOT.get());
        campfireCookingRecipe(recipeConsumer, MWItems.TIN_INGOT.get(), 1800, MWItems.HEATED_TIN_INGOT.get());
        campfireCookingRecipe(recipeConsumer, Items.COPPER_INGOT, 1800, MWItems.HEATED_COPPER_INGOT.get());
        campfireCookingRecipe(recipeConsumer, Items.IRON_INGOT, 1800, MWItems.HEATED_IRON_INGOT.get());

        new FletchingRecipeBuilder(new ItemStack(Items.ARROW, 16), new ItemStack(Items.FLINT), new ItemStack(Items.STICK), new ItemStack(Items.FEATHER))
                .unlockedBy("has_flint", has(Items.FLINT))
                .unlockedBy("has_stick", has(Items.STICK))
                .unlockedBy("has_feather", has(Items.FEATHER))
                .save(recipeConsumer, new ResourceLocation(MedievalWorlds.MOD_ID, "arrow_fletching"));

        bigToolSmithing(recipeConsumer, MWItems.WEAPON_PARTS.get("iron_hammer_head").get(), MWItems.IRON_HAMMER.get());
        bigToolSmithing(recipeConsumer, MWItems.WEAPON_PARTS.get("iron_long_axe_head").get(), MWItems.IRON_LONG_AXE.get());
        bigToolSmithing(recipeConsumer, MWItems.WEAPON_PARTS.get("iron_longsword_blade").get(), MWItems.IRON_LONGSWORD.get());
        smithingRecipe(recipeConsumer, Items.IRON_BOOTS, 1, MWItems.TOOL_PARTS.get("iron_armor_plate").get(), 8, MWItems.IRON_KNIGHT_BOOTS.get(), 1, 10);
        smithingRecipe(recipeConsumer, Items.IRON_LEGGINGS, 1, MWItems.TOOL_PARTS.get("iron_armor_plate").get(), 8, MWItems.IRON_KNIGHT_LEGGINGS.get(), 1, 10);
        smithingRecipe(recipeConsumer, Items.IRON_CHESTPLATE, 1, MWItems.TOOL_PARTS.get("iron_armor_plate").get(), 8, MWItems.IRON_KNIGHT_CHESTPLATE.get(), 1, 10);
        smithingRecipe(recipeConsumer, Items.IRON_HELMET, 1, MWItems.TOOL_PARTS.get("iron_armor_plate").get(), 8, MWItems.IRON_KNIGHT_HELMET.get(), 1, 10);

        bigToolSmithing(recipeConsumer, MWItems.WEAPON_PARTS.get("netherite_hammer_head").get(), MWItems.NETHERITE_HAMMER.get());
        bigToolSmithing(recipeConsumer, MWItems.WEAPON_PARTS.get("netherite_long_axe_head").get(), MWItems.NETHERITE_LONG_AXE.get());
        bigToolSmithing(recipeConsumer, MWItems.WEAPON_PARTS.get("netherite_longsword_blade").get(), MWItems.NETHERITE_LONGSWORD.get());

        bigToolSmithing(recipeConsumer, MWItems.WEAPON_PARTS.get("silver_hammer_head").get(), MWItems.SILVER_HAMMER.get());
        bigToolSmithing(recipeConsumer, MWItems.WEAPON_PARTS.get("silver_long_axe_head").get(), MWItems.SILVER_LONG_AXE.get());
        bigToolSmithing(recipeConsumer, MWItems.WEAPON_PARTS.get("silver_longsword_blade").get(), MWItems.SILVER_LONGSWORD.get());
        smithingRecipe(recipeConsumer, MWItems.SILVER_BOOTS.get(), 1, MWItems.TOOL_PARTS.get("silver_armor_plate").get(), 8, MWItems.SILVER_KNIGHT_BOOTS.get(), 1, 18);
        smithingRecipe(recipeConsumer, MWItems.SILVER_LEGGINGS.get(), 1, MWItems.TOOL_PARTS.get("silver_armor_plate").get(), 8, MWItems.SILVER_KNIGHT_LEGGINGS.get(), 1, 18);
        smithingRecipe(recipeConsumer, MWItems.SILVER_CHESTPLATE.get(), 1, MWItems.TOOL_PARTS.get("silver_armor_plate").get(), 8, MWItems.SILVER_KNIGHT_CHESTPLATE.get(), 1, 18);
        smithingRecipe(recipeConsumer, MWItems.SILVER_HELMET.get(), 1, MWItems.TOOL_PARTS.get("silver_armor_plate").get(), 8, MWItems.SILVER_KNIGHT_HELMET.get(), 1, 18);

        bigToolSmithing(recipeConsumer, MWItems.WEAPON_PARTS.get("steel_hammer_head").get(), MWItems.STEEL_HAMMER.get());
        bigToolSmithing(recipeConsumer, MWItems.WEAPON_PARTS.get("steel_long_axe_head").get(), MWItems.STEEL_LONG_AXE.get());
        bigToolSmithing(recipeConsumer, MWItems.WEAPON_PARTS.get("steel_longsword_blade").get(), MWItems.STEEL_LONGSWORD.get());
        smithingRecipe(recipeConsumer, MWItems.STEEL_BOOTS.get(), 1, MWItems.TOOL_PARTS.get("steel_armor_plate").get(), 8, MWItems.STEEL_KNIGHT_BOOTS.get(), 1, 18);
        smithingRecipe(recipeConsumer, MWItems.STEEL_LEGGINGS.get(), 1, MWItems.TOOL_PARTS.get("steel_armor_plate").get(), 8, MWItems.STEEL_KNIGHT_LEGGINGS.get(), 1, 18);
        smithingRecipe(recipeConsumer, MWItems.STEEL_CHESTPLATE.get(), 1, MWItems.TOOL_PARTS.get("steel_armor_plate").get(), 8, MWItems.STEEL_KNIGHT_CHESTPLATE.get(), 1, 18);
        smithingRecipe(recipeConsumer, MWItems.STEEL_HELMET.get(), 1, MWItems.TOOL_PARTS.get("steel_armor_plate").get(), 8, MWItems.STEEL_KNIGHT_HELMET.get(), 1, 18);

        smithingRecipe(recipeConsumer, Items.DIAMOND_BOOTS, 1, MWItems.TOOL_PARTS.get("steel_armor_plate").get(), 8, MWItems.WARRIOR_BOOTS.get(), 1,19);
        smithingRecipe(recipeConsumer, Items.DIAMOND_LEGGINGS, 1, MWItems.TOOL_PARTS.get("steel_armor_plate").get(), 8, MWItems.WARRIOR_LEGGINGS.get(), 1,19);
        smithingRecipe(recipeConsumer, Items.DIAMOND_CHESTPLATE, 1, MWItems.TOOL_PARTS.get("steel_armor_plate").get(), 8, MWItems.WARRIOR_CHESTPLATE.get(), 1,19);
        smithingRecipe(recipeConsumer, Items.DIAMOND_HELMET, 1, MWItems.TOOL_PARTS.get("steel_armor_plate").get(), 8, MWItems.WARRIOR_HELMET.get(), 1,19);

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
        partSmithing(recipeConsumer, MWItems.WEAPON_PARTS.get("iron_hammer_head_raw").get(), Items.GOLD_INGOT, MWItems.WEAPON_PARTS.get("iron_hammer_head").get());

        toolSmithing(recipeConsumer, MWItems.TOOL_PARTS.get("silver_axe_head").get(), MWItems.SILVER_AXE.get());
        toolSmithing(recipeConsumer, MWItems.TOOL_PARTS.get("silver_hoe_head").get(), MWItems.SILVER_HOE.get());
        toolSmithing(recipeConsumer, MWItems.TOOL_PARTS.get("silver_pickaxe_head").get(), MWItems.SILVER_PICKAXE.get());
        toolSmithing(recipeConsumer, MWItems.TOOL_PARTS.get("silver_shovel_head").get(), MWItems.SILVER_SHOVEL.get());
        toolSmithing(recipeConsumer, MWItems.TOOL_PARTS.get("silver_sword_blade").get(), MWItems.SILVER_SWORD.get());
        partSmithing(recipeConsumer, MWItems.WEAPON_PARTS.get("silver_hammer_head_raw").get(), Items.GOLD_INGOT, MWItems.WEAPON_PARTS.get("silver_hammer_head").get());

        toolSmithing(recipeConsumer, MWItems.TOOL_PARTS.get("steel_axe_head").get(), MWItems.STEEL_AXE.get());
        toolSmithing(recipeConsumer, MWItems.TOOL_PARTS.get("steel_hoe_head").get(), MWItems.STEEL_HOE.get());
        toolSmithing(recipeConsumer, MWItems.TOOL_PARTS.get("steel_pickaxe_head").get(), MWItems.STEEL_PICKAXE.get());
        toolSmithing(recipeConsumer, MWItems.TOOL_PARTS.get("steel_shovel_head").get(), MWItems.STEEL_SHOVEL.get());
        toolSmithing(recipeConsumer, MWItems.TOOL_PARTS.get("steel_sword_blade").get(), MWItems.STEEL_SWORD.get());
        partSmithing(recipeConsumer, MWItems.WEAPON_PARTS.get("steel_hammer_head_raw").get(), Items.GOLD_INGOT, MWItems.WEAPON_PARTS.get("steel_hammer_head").get());

        toolSmithing(recipeConsumer, MWItems.TOOL_PARTS.get("netherite_axe_head").get(), Items.NETHERITE_AXE);
        toolSmithing(recipeConsumer, MWItems.TOOL_PARTS.get("netherite_hoe_head").get(), Items.NETHERITE_HOE);
        toolSmithing(recipeConsumer, MWItems.TOOL_PARTS.get("netherite_pickaxe_head").get(), Items.NETHERITE_PICKAXE);
        toolSmithing(recipeConsumer, MWItems.TOOL_PARTS.get("netherite_shovel_head").get(), Items.NETHERITE_SHOVEL);
        toolSmithing(recipeConsumer, MWItems.TOOL_PARTS.get("netherite_sword_blade").get(), Items.NETHERITE_SWORD);
        partSmithing(recipeConsumer, MWItems.WEAPON_PARTS.get("netherite_hammer_head_raw").get(), Items.GOLD_INGOT, MWItems.WEAPON_PARTS.get("netherite_hammer_head").get());

        netheriteSmithing(recipeConsumer, MWItems.TOOL_PARTS.get("steel_axe_head").get(), MWItems.TOOL_PARTS.get("netherite_axe_head").get());
        netheriteSmithing(recipeConsumer, MWItems.TOOL_PARTS.get("steel_hoe_head").get(), MWItems.TOOL_PARTS.get("netherite_hoe_head").get());
        netheriteSmithing(recipeConsumer, MWItems.TOOL_PARTS.get("steel_pickaxe_head").get(), MWItems.TOOL_PARTS.get("netherite_pickaxe_head").get());
        netheriteSmithing(recipeConsumer, MWItems.TOOL_PARTS.get("steel_shovel_head").get(), MWItems.TOOL_PARTS.get("netherite_shovel_head").get());
        netheriteSmithing(recipeConsumer, MWItems.TOOL_PARTS.get("steel_sword_blade").get(), MWItems.TOOL_PARTS.get("netherite_sword_blade").get());
        netheriteSmithing(recipeConsumer, MWItems.WEAPON_PARTS.get("steel_long_axe_head").get(), MWItems.WEAPON_PARTS.get("netherite_long_axe_head").get());
        netheriteSmithing(recipeConsumer, MWItems.WEAPON_PARTS.get("steel_longsword_blade").get(), MWItems.WEAPON_PARTS.get("netherite_longsword_blade").get());
        netheriteBlockSmithing(recipeConsumer, MWItems.WEAPON_PARTS.get("steel_hammer_head").get(), MWItems.WEAPON_PARTS.get("netherite_hammer_head").get());
        netheriteBlockSmithing(recipeConsumer, MWItems.WEAPON_PARTS.get("steel_hammer_head_raw").get(), MWItems.WEAPON_PARTS.get("netherite_hammer_head_raw").get());

        for(RegistryObject<Item> ro : MWItems.TOOL_PARTS.values()) {
            if(ro.get() instanceof AbstractHeatedItem item) {
                slackTubRecipe(recipeConsumer, Ingredient.of(item), 1, item.getBaseItem(), 1);
            }
        }

        for(RegistryObject<Item> ro : MWItems.WEAPON_PARTS.values()) {
            if(ro.get() instanceof AbstractHeatedItem item) {
                slackTubRecipe(recipeConsumer, Ingredient.of(item), 1, item.getBaseItem(), 1);
            }
        }

        slackTubRecipe(recipeConsumer, Ingredient.of(MWItems.HEATED_BRONZE_INGOT.get()), 1, MWItems.BRONZE_INGOT.get(), 1);
        slackTubRecipe(recipeConsumer, Ingredient.of(MWItems.HEATED_TIN_INGOT.get()), 1, MWItems.TIN_INGOT.get(), 1);
        slackTubRecipe(recipeConsumer, Ingredient.of(MWItems.HEATED_COPPER_INGOT.get()), 1, Items.COPPER_INGOT, 1);
        slackTubRecipe(recipeConsumer, Ingredient.of(MWItems.HEATED_IRON_INGOT.get()), 1, Items.IRON_INGOT, 1);
        slackTubRecipe(recipeConsumer, Ingredient.of(MWItems.HEATED_GOLD_INGOT.get()), 1, Items.GOLD_INGOT, 1);
        slackTubRecipe(recipeConsumer, Ingredient.of(MWItems.HEATED_STEEL_INGOT.get()), 1, MWItems.STEEL_INGOT.get(), 1);
        slackTubRecipe(recipeConsumer, Ingredient.of(MWItems.HEATED_SILVER_INGOT.get()), 1, MWItems.SILVER_INGOT.get(), 1);
        slackTubRecipe(recipeConsumer, Ingredient.of(MWItems.HEATED_NETHERITE_INGOT.get()), 1, Items.NETHERITE_INGOT, 1);

        netheriteSmithing(recipeConsumer, MWItems.STEEL_HORSE_ARMOR.get(), MWItems.NETHERITE_HORSE_ARMOR.get());
        netheriteSmithing(recipeConsumer, MWItems.STEEL_FORGE_HAMMER.get(), MWItems.NETHERITE_FORGE_HAMMER.get());
        netheriteSmithing(recipeConsumer, MWItems.STEEL_KNIGHT_BOOTS.get(), MWItems.NETHERITE_KNIGHT_BOOTS.get());
        netheriteSmithing(recipeConsumer, MWItems.STEEL_KNIGHT_LEGGINGS.get(), MWItems.NETHERITE_KNIGHT_LEGGINGS.get());
        netheriteSmithing(recipeConsumer, MWItems.STEEL_KNIGHT_CHESTPLATE.get(), MWItems.NETHERITE_KNIGHT_CHESTPLATE.get());
        netheriteSmithing(recipeConsumer, MWItems.STEEL_KNIGHT_HELMET.get(), MWItems.NETHERITE_KNIGHT_HELMET.get());
        netheriteBlockSmithing(recipeConsumer, MWItems.STEEL_HAMMER.get(), MWItems.NETHERITE_HAMMER.get());
        netheriteSmithing(recipeConsumer, MWItems.STEEL_AXE.get(), Items.NETHERITE_AXE);
        netheriteSmithing(recipeConsumer, MWItems.STEEL_HOE.get(), Items.NETHERITE_HOE);
        netheriteSmithing(recipeConsumer, MWItems.STEEL_PICKAXE.get(), Items.NETHERITE_PICKAXE);
        netheriteSmithing(recipeConsumer, MWItems.STEEL_SHOVEL.get(), Items.NETHERITE_SHOVEL);
        netheriteSmithing(recipeConsumer, MWItems.STEEL_SWORD.get(), Items.NETHERITE_SWORD);
        netheriteSmithing(recipeConsumer, MWItems.STEEL_LONGSWORD.get(), MWItems.NETHERITE_LONGSWORD.get());
        netheriteSmithing(recipeConsumer, MWItems.STEEL_LONG_AXE.get(), MWItems.NETHERITE_LONG_AXE.get());
        netheriteSmithing(recipeConsumer, MWItems.STEEL_LANCE.get(), MWItems.NETHERITE_LANCE.get());

        daggerRecipe(recipeConsumer, Items.IRON_NUGGET, MWItems.IRON_DAGGER.get());
        daggerRecipe(recipeConsumer, MWItems.SILVER_NUGGET.get(), MWItems.SILVER_DAGGER.get());
        daggerRecipe(recipeConsumer, MWItems.STEEL_NUGGET.get(), MWItems.STEEL_DAGGER.get());

        ShapedRecipeBuilder.shaped(MWItems.LONGBOW.get())
                .define('0', MWItems.STEEL_INGOT.get())
                .define('1', Items.STICK)
                .define('2', Items.STRING)
                .pattern("112")
                .pattern("0 2")
                .pattern("112")
                .unlockedBy(getHasName(MWItems.STEEL_INGOT.get()), has(MWItems.STEEL_INGOT.get()))
                .save(recipeConsumer, new ResourceLocation(MedievalWorlds.MOD_ID, getItemName(MWItems.STEEL_INGOT.get())));

        lanceRecipe(recipeConsumer, Blocks.IRON_BLOCK, MWItems.IRON_LANCE.get());
        lanceRecipe(recipeConsumer, MWBlocks.SILVER_BLOCK.get(), MWItems.SILVER_LANCE.get());
        lanceRecipe(recipeConsumer, MWBlocks.STEEL_BLOCK.get(), MWItems.STEEL_LANCE.get());

        ShapedRecipeBuilder.shaped(MWItems.FIRE_STARTER.get())
                .define('S', Items.STICK)
                .define('/', Items.STRING)
                .define('P', ItemTags.PLANKS)
                .pattern(" S ")
                .pattern("S/S")
                .pattern(" P ")
                .unlockedBy(getHasName(Items.STRING), has(Items.STRING))
                .save(recipeConsumer, new ResourceLocation(MedievalWorlds.MOD_ID, "fire_starter"));

        ShapedRecipeBuilder.shaped(MWItems.ROUND_SHIELD.get())
                .define('P', ItemTags.PLANKS)
                .define('B', MWItems.BRONZE_INGOT.get())
                .pattern(" P ")
                .pattern("PBP")
                .pattern(" P ")
                .unlockedBy(getHasName(MWItems.BRONZE_INGOT.get()), has(MWItems.BRONZE_INGOT.get()))
                .save(recipeConsumer, new ResourceLocation(MedievalWorlds.MOD_ID, "round_shield"));

        ShapedRecipeBuilder.shaped(MWItems.CROWN.get())
                .define('G', Items.GOLD_INGOT)
                .define('R', MWItems.RUBY.get())
                .pattern("GRG")
                .pattern("GGG")
                .unlockedBy(getHasName(MWItems.RUBY.get()), has(MWItems.RUBY.get()))
                .save(recipeConsumer, new ResourceLocation(MedievalWorlds.MOD_ID, "crown"));

        ShapedRecipeBuilder.shaped(Blocks.BLAST_FURNACE)
                .define('S', Blocks.SMOOTH_STONE)
                .define('I', MWItems.STEEL_INGOT.get())
                .define('F', Blocks.FURNACE)
                .pattern("III")
                .pattern("IFI")
                .pattern("SSS")
                .unlockedBy(getHasName(MWItems.STEEL_INGOT.get()), has(MWItems.STEEL_INGOT.get()))
                .save(recipeConsumer);

        noRecipe(recipeConsumer, new ResourceLocation("golden_axe"));
        noRecipe(recipeConsumer, new ResourceLocation("golden_hoe"));
        noRecipe(recipeConsumer, new ResourceLocation("golden_pickaxe"));
        noRecipe(recipeConsumer, new ResourceLocation("golden_shovel"));
        noRecipe(recipeConsumer, new ResourceLocation("golden_sword"));
        noRecipe(recipeConsumer, new ResourceLocation("golden_boots"));
        noRecipe(recipeConsumer, new ResourceLocation("golden_chestplate"));
        noRecipe(recipeConsumer, new ResourceLocation("golden_leggings"));
        noRecipe(recipeConsumer, new ResourceLocation("golden_helmet"));

        noRecipe(recipeConsumer, new ResourceLocation("iron_axe"));
        noRecipe(recipeConsumer, new ResourceLocation("iron_hoe"));
        noRecipe(recipeConsumer, new ResourceLocation("iron_pickaxe"));
        noRecipe(recipeConsumer, new ResourceLocation("iron_shovel"));
        noRecipe(recipeConsumer, new ResourceLocation("iron_sword"));
        noRecipe(recipeConsumer, new ResourceLocation("iron_boots"));
        noRecipe(recipeConsumer, new ResourceLocation("iron_chestplate"));
        noRecipe(recipeConsumer, new ResourceLocation("iron_leggings"));
        noRecipe(recipeConsumer, new ResourceLocation("iron_helmet"));

        noRecipe(recipeConsumer, new ResourceLocation("iron_ingot_from_smelting_deepslate_iron_ore"));
        noRecipe(recipeConsumer, new ResourceLocation("iron_ingot_from_smelting_iron_ore"));
        noRecipe(recipeConsumer, new ResourceLocation("iron_ingot_from_smelting_raw_iron"));
        noRecipe(recipeConsumer, new ResourceLocation("iron_nugget_from_smelting"));

        noRecipe(recipeConsumer, new ResourceLocation("netherite_axe_smithing"));
        noRecipe(recipeConsumer, new ResourceLocation("netherite_hoe_smithing"));
        noRecipe(recipeConsumer, new ResourceLocation("netherite_pickaxe_smithing"));
        noRecipe(recipeConsumer, new ResourceLocation("netherite_shovel_smithing"));
        noRecipe(recipeConsumer, new ResourceLocation("netherite_sword_smithing"));
        noRecipe(recipeConsumer, new ResourceLocation("netherite_boots_smithing"));
        noRecipe(recipeConsumer, new ResourceLocation("netherite_chestplate_smithing"));
        noRecipe(recipeConsumer, new ResourceLocation("netherite_leggings_smithing"));
        noRecipe(recipeConsumer, new ResourceLocation("netherite_helmet_smithing"));

        cloakRecipe(recipeConsumer, Blocks.BROWN_WOOL, Items.RED_DYE, MWItems.CLOAK.get());
        cloakRecipe(recipeConsumer, Blocks.BLACK_WOOL, Items.BLUE_DYE, MWItems.DARK_CLOAK.get());
        cloakRecipe(recipeConsumer, Blocks.WHITE_WOOL, Items.LIME_DYE, MWItems.LIGHT_CLOAK.get());
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

    protected static void slackTubRecipe(Consumer<FinishedRecipe> recipeConsumer, Ingredient input, int inputCount, ItemLike output, int outputCount) {
        new SlackTubRecipeBuilder(new ItemStack(output, outputCount), input, inputCount)
                .unlockedBy(getHasName(output), has(output))
                .save(recipeConsumer, new ResourceLocation(MedievalWorlds.MOD_ID, getItemName(output) + "_quenching"));
    }

    protected static void noRecipe(Consumer<FinishedRecipe> recipeConsumer, ResourceLocation id) {
        new NoRecipeBuilder().save(recipeConsumer, id);
    }

    protected static void lanceRecipe(Consumer<FinishedRecipe> recipeConsumer, ItemLike materialBlock, ItemLike lanceItem) {
        ShapedRecipeBuilder.shaped(lanceItem)
                .define('#', materialBlock)
                .define('I', Items.STICK)
                .pattern("##I")
                .unlockedBy(getHasName(materialBlock), has(materialBlock))
                .save(recipeConsumer, new ResourceLocation(MedievalWorlds.MOD_ID, getItemName(materialBlock)));
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

    protected static void netheriteSmithing(@NotNull Consumer<FinishedRecipe> recipeConsumer, @NotNull Item base, Item result) {
        UpgradeRecipeBuilder.smithing(Ingredient.of(base), Ingredient.of(Items.NETHERITE_INGOT), result).unlocks("has_netherite_ingot", has(Items.NETHERITE_INGOT)).save(recipeConsumer, MedievalWorlds.MOD_ID + ":" + getItemName(result) + "_smithing_ingot");
    }

    protected static void netheriteBlockSmithing(@NotNull Consumer<FinishedRecipe> recipeConsumer, @NotNull Item base, Item result) {
        UpgradeRecipeBuilder.smithing(Ingredient.of(base), Ingredient.of(Items.NETHERITE_BLOCK), result).unlocks("has_netherite_block", has(Items.NETHERITE_BLOCK)).save(recipeConsumer, MedievalWorlds.MOD_ID + ":" + getItemName(result) + "_smithing_block");
    }

    private static void partSmithing(Consumer<FinishedRecipe> recipeConsumer, ItemLike base, ItemLike addition, Item result) {
        UpgradeRecipeBuilder.smithing(Ingredient.of(base), Ingredient.of(addition), result).unlocks("has_base_part", has(base)).save(recipeConsumer, MedievalWorlds.MOD_ID + ":" + getItemName(result) + "_smithing");
    }

    private static void toolSmithing(Consumer<FinishedRecipe> recipeConsumer, ItemLike base, Item result) {
        UpgradeRecipeBuilder.smithing(Ingredient.of(base), Ingredient.of(Items.STICK), result).unlocks("has_base_part", has(base)).save(recipeConsumer, MedievalWorlds.MOD_ID + ":" + getItemName(result) + "_smithing");
    }

    private static void bigToolSmithing(Consumer<FinishedRecipe> recipeConsumer, ItemLike base, Item result) {
        smithingRecipe(recipeConsumer, base, 1, Items.STICK, 2, result, 1, 0);
    }

    private static void smithingRecipe(Consumer<FinishedRecipe> recipeConsumer, ItemLike base, int baseCount, ItemLike addition, int additionCount, ItemLike result, int resultCount, int cost) {
        new SmithingRecipeBuilder(new ItemStack(base, baseCount), new ItemStack(addition, additionCount), new ItemStack(result, resultCount), cost)
                .unlockedBy(getItemName(base), has(base))
                .unlockedBy(getItemName(addition), has(addition))
                .save(recipeConsumer, new ResourceLocation(MedievalWorlds.MOD_ID, getItemName(result)));
    }

    private static void campfireCookingRecipe(Consumer<FinishedRecipe> recipeConsumer, ItemLike input, int duration, ItemLike output) {
        SimpleCookingRecipeBuilder.cooking(Ingredient.of(input), output, 0.0f, duration, RecipeSerializer.CAMPFIRE_COOKING_RECIPE)
                .unlockedBy(getHasName(input), has(input))
                .save(recipeConsumer, new ResourceLocation(MedievalWorlds.MOD_ID, getItemName(output) + "_from_campfire_cooking"));
    }

    private static void burnRecipe(Consumer<FinishedRecipe> recipeConsumer, String output, String input) {
        String[] outputParts = output.split(":");
        new BurnRecipeBuilder(output, input)
                .unlockedBy("has_flint_and_steel", has(Items.FLINT_AND_STEEL))
                .unlockedBy("has_fire_starter", has(MWItems.FIRE_STARTER.get()))
                .save(recipeConsumer, new ResourceLocation(MedievalWorlds.MOD_ID, outputParts[outputParts.length - 1] + "_burning"));
    }

    private static void helmetRecipe(Consumer<FinishedRecipe> recipeConsumer, ItemLike helmet, ItemLike armorPlate) {
        ShapedRecipeBuilder.shaped(helmet)
                .define('X', armorPlate)
                .pattern("XXX")
                .pattern("X X")
                .unlockedBy(getItemName(armorPlate), has(armorPlate))
                .save(recipeConsumer, new ResourceLocation(MedievalWorlds.MOD_ID, Objects.requireNonNull(helmet.asItem().getRegistryName()).getPath()));
    }

    private static void chestplateRecipe(Consumer<FinishedRecipe> recipeConsumer, ItemLike chestplate, ItemLike armorPlate) {
        ShapedRecipeBuilder.shaped(chestplate)
                .define('X', armorPlate)
                .pattern("X X")
                .pattern("XXX")
                .pattern("XXX")
                .unlockedBy(getItemName(armorPlate), has(armorPlate))
                .save(recipeConsumer, new ResourceLocation(MedievalWorlds.MOD_ID, Objects.requireNonNull(chestplate.asItem().getRegistryName()).getPath()));
    }

    private static void leggingsRecipe(Consumer<FinishedRecipe> recipeConsumer, ItemLike leggings, ItemLike armorPlate) {
        ShapedRecipeBuilder.shaped(leggings)
                .define('X', armorPlate)
                .pattern("XXX")
                .pattern("X X")
                .pattern("X X")
                .unlockedBy(getItemName(armorPlate), has(armorPlate))
                .save(recipeConsumer, new ResourceLocation(MedievalWorlds.MOD_ID, Objects.requireNonNull(leggings.asItem().getRegistryName()).getPath()));
    }

    private static void bootsRecipe(Consumer<FinishedRecipe> recipeConsumer, ItemLike boots, ItemLike armorPlate) {
        ShapedRecipeBuilder.shaped(boots)
                .define('X', armorPlate)
                .pattern("X X")
                .pattern("X X")
                .unlockedBy(getItemName(armorPlate), has(armorPlate))
                .save(recipeConsumer, new ResourceLocation(MedievalWorlds.MOD_ID, Objects.requireNonNull(boots.asItem().getRegistryName()).getPath()));
    }

    private static void anvilRecipe(Consumer<FinishedRecipe> recipeConsumer, ItemLike output, int outputCount, ItemLike input, int inputCount, String prefix) {
        new AnvilRecipeBuilder(new ItemStack(output, outputCount), new ItemStack(input, inputCount))
                .unlockedBy(getItemName(input), has(input))
                .save(recipeConsumer, new ResourceLocation(MedievalWorlds.MOD_ID, Objects.requireNonNull(output.asItem().getRegistryName()).getPath() + "_anvil" + prefix));
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

    protected static void heatingBlasting(@NotNull Consumer<FinishedRecipe> recipeConsumer, AbstractHeatedItem heatedItem) {
        oreBlasting(recipeConsumer, ImmutableList.of(heatedItem.getBaseItem()), heatedItem, 0.0f, 200, "");
    }

    @SuppressWarnings("SameParameterValue")
    protected static void alloying(@NotNull Consumer<FinishedRecipe> recipeConsumer, ItemLike result, int resultCount, ItemLike input1, int input1Count, ItemLike input2, int input2Count, String prefix) {
        new AlloyRecipeBuilder(new ItemStack(result, resultCount), new ItemStack(input1, input1Count), new ItemStack(input2, input2Count))
                .unlockedBy(getItemName(input1), has(input1))
                .unlockedBy(getItemName(input2), has(input2))
                .save(recipeConsumer, new ResourceLocation(MedievalWorlds.MOD_ID, Objects.requireNonNull(result.asItem().getRegistryName()).getPath() + "_alloying" + prefix));
    }
    
    protected static void oreSmelting(@NotNull Consumer<FinishedRecipe> recipeConsumer, List<ItemLike> variants, @NotNull ItemLike result, float xp, int duration, @NotNull String group) {
        oreCooking(recipeConsumer, RecipeSerializer.SMELTING_RECIPE, variants, result, xp, duration, group, "_from_smelting");
    }

    protected static void oreBlasting(@NotNull Consumer<FinishedRecipe> recipeConsumer, List<ItemLike> variants, @NotNull ItemLike result, float xp, int duration, @NotNull String group) {
        oreCooking(recipeConsumer, RecipeSerializer.BLASTING_RECIPE, variants, result, xp, duration, group, "_from_blasting");
    }

    protected static void oreCooking(@NotNull Consumer<FinishedRecipe> recipeConsumer, @NotNull SimpleCookingSerializer<?> serializer, List<ItemLike> variants, @NotNull ItemLike result, float xp, int duration, @NotNull String group, String prefix) {
        for(ItemLike itemlike : variants) {
            SimpleCookingRecipeBuilder.cooking(Ingredient.of(itemlike), result, xp, duration, serializer).group(group).unlockedBy(getHasName(itemlike), has(itemlike)).save(recipeConsumer, MedievalWorlds.MOD_ID + ":" + getItemName(result) + prefix + "_" + getItemName(itemlike));
        }
    }
    
    protected static void nineBlockStorageRecipes(@NotNull Consumer<FinishedRecipe> recipeConsumer, ItemLike small, ItemLike big) {
        nineBlockStorageRecipes(recipeConsumer, small, big, MedievalWorlds.MOD_ID + ":" + getSimpleRecipeName(big) + "_packing", null, MedievalWorlds.MOD_ID + ":" + getSimpleRecipeName(small) + "_unpacking", null);
    }

    protected static void nineBlockStorageRecipesWithCustomPacking(@NotNull Consumer<FinishedRecipe> recipeConsumer, ItemLike small, ItemLike big, String name, @NotNull String group) {
        nineBlockStorageRecipes(recipeConsumer, small, big, name, group, MedievalWorlds.MOD_ID + ":" + getSimpleRecipeName(small), null);
    }

    protected static void nineBlockStorageRecipesRecipesWithCustomUnpacking(@NotNull Consumer<FinishedRecipe> recipeConsumer, ItemLike small, ItemLike big, String name, @NotNull String group) {
        nineBlockStorageRecipes(recipeConsumer, small, big, MedievalWorlds.MOD_ID + ":" + getSimpleRecipeName(big), null, name, group);
    }
}
