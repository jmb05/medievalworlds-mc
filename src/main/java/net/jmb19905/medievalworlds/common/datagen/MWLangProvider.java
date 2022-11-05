package net.jmb19905.medievalworlds.common.datagen;

import com.mojang.datafixers.util.Pair;
import net.jmb19905.medievalworlds.MedievalWorlds;
import net.jmb19905.medievalworlds.common.registries.MWBlocks;
import net.jmb19905.medievalworlds.common.registries.MWEnchantments;
import net.jmb19905.medievalworlds.common.registries.MWItems;
import net.jmb19905.medievalworlds.common.registries.MWMobEffects;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.data.LanguageProvider;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.BiConsumer;

public class MWLangProvider extends LanguageProvider {

    public MWLangProvider(DataGenerator gen) {
        super(gen, MedievalWorlds.MOD_ID, "en_us");
    }

    @Override
    protected void addTranslations() {
        //Items
        add(MWItems.STEEL_INGOT.get(), "Steel Ingot");
        add(MWItems.SILVER_INGOT.get(), "Silver Ingot");
        add(MWItems.BRONZE_INGOT.get(), "Bronze Ingot");
        add(MWItems.TIN_INGOT.get(), "Tin Ingot");

        add(MWItems.STEEL_NUGGET.get(), "Steel Nugget");
        add(MWItems.SILVER_NUGGET.get(), "Silver Nugget");
        add(MWItems.BRONZE_NUGGET.get(), "Bronze Nugget");
        add(MWItems.TIN_NUGGET.get(), "Tin Nugget");

        add(MWItems.RAW_SILVER.get(), "Raw Silver");
        add(MWItems.RAW_TIN.get(), "Raw Tin");

        add(MWItems.CLOAK.get(), "Cloak");
        add(MWItems.DARK_CLOAK.get(), "Dark Cloak");
        add(MWItems.LIGHT_CLOAK.get(), "Light Cloak");

        add(MWItems.QUIVER.get(), "Quiver");
        add("item." + MedievalWorlds.MOD_ID + ".quiver.fullness", "%s/%s");
        add("item." + MedievalWorlds.MOD_ID + ".quiver.creative_broken", "Interaction with the quiver in creative when on a server is currently broken!");

        add(MWItems.OAK_STAFF.get(), "Oak Staff");
        add(MWItems.SPRUCE_STAFF.get(), "Spruce Staff");
        add(MWItems.DARK_OAK_STAFF.get(), "Dark Oak Staff");
        add(MWItems.BIRCH_STAFF.get(), "Birch Staff");
        add(MWItems.ACACIA_STAFF.get(), "Acacia Staff");
        add(MWItems.JUNGLE_STAFF.get(), "Jungle Staff");
        add(MWItems.MANGROVE_STAFF.get(), "Mangrove Staff");

        add(MWItems.IRON_HAMMER.get(), "Iron Hammer");
        add(MWItems.SILVER_HAMMER.get(), "Silver Hammer");
        add(MWItems.STEEL_HAMMER.get(), "Steel Hammer");
        add(MWItems.NETHERITE_HAMMER.get(), "Netherite Hammer");

        add(MWItems.STONE_FORGE_HAMMER.get(), "Stone Forge Hammer");
        add(MWItems.IRON_FORGE_HAMMER.get(), "Iron Forge Hammer");
        add(MWItems.STEEL_FORGE_HAMMER.get(), "Steel Forge Hammer");
        add(MWItems.NETHERITE_FORGE_HAMMER.get(), "Netherite Forge Hammer");

        add(MWItems.IRON_LONGSWORD.get(), "Iron Longsword");
        add(MWItems.SILVER_LONGSWORD.get(), "Silver Longsword");
        add(MWItems.STEEL_LONGSWORD.get(), "Steel Longsword");
        add(MWItems.NETHERITE_LONGSWORD.get(), "Netherite Longsword");

        add(MWItems.IRON_LONG_AXE.get(), "Iron Long Axe");
        add(MWItems.SILVER_LONG_AXE.get(), "Silver Long Axe");
        add(MWItems.STEEL_LONG_AXE.get(), "Steel Long Axe");
        add(MWItems.NETHERITE_LONG_AXE.get(), "Netherite Long Axe");

        add(MWItems.IRON_DAGGER.get(), "Iron Dagger");
        add(MWItems.SILVER_DAGGER.get(), "Silver Dagger");
        add(MWItems.STEEL_DAGGER.get(), "Steel Dagger");
        add(MWItems.NETHERITE_DAGGER.get(), "Netherite Dagger");

        add(MWItems.BRONZE_PICKAXE.get(), "Bronze Pickaxe");
        add(MWItems.BRONZE_AXE.get(), "Bronze Axe");
        add(MWItems.BRONZE_SHOVEL.get(), "Bronze Shovel");
        add(MWItems.BRONZE_HOE.get(), "Bronze Hoe");
        add(MWItems.BRONZE_SWORD.get(), "Bronze Sword");

        add(MWItems.SILVER_PICKAXE.get(), "Silver Pickaxe");
        add(MWItems.SILVER_AXE.get(), "Silver Axe");
        add(MWItems.SILVER_SHOVEL.get(), "Silver Shovel");
        add(MWItems.SILVER_HOE.get(), "Silver Hoe");
        add(MWItems.SILVER_SWORD.get(), "Silver Sword");

        add(MWItems.STEEL_PICKAXE.get(), "Steel Pickaxe");
        add(MWItems.STEEL_AXE.get(), "Steel Axe");
        add(MWItems.STEEL_SHOVEL.get(), "Steel Shovel");
        add(MWItems.STEEL_HOE.get(), "Steel Hoe");
        add(MWItems.STEEL_SWORD.get(), "Steel Sword");

        add(MWItems.BRONZE_HELMET.get(), "Bronze Helmet");
        add(MWItems.BRONZE_CHESTPLATE.get(), "Bronze Chestplate");
        add(MWItems.BRONZE_LEGGINGS.get(), "Bronze Leggings");
        add(MWItems.BRONZE_BOOTS.get(), "Bronze Boots");

        add(MWItems.SILVER_HELMET.get(), "Silver Helmet");
        add(MWItems.SILVER_CHESTPLATE.get(), "Silver Chestplate");
        add(MWItems.SILVER_LEGGINGS.get(), "Silver Leggings");
        add(MWItems.SILVER_BOOTS.get(), "Silver Boots");

        add(MWItems.STEEL_HELMET.get(), "Steel Helmet");
        add(MWItems.STEEL_CHESTPLATE.get(), "Steel Chestplate");
        add(MWItems.STEEL_LEGGINGS.get(), "Steel Leggings");
        add(MWItems.STEEL_BOOTS.get(), "Steel Boots");

        add(MWItems.IRON_KNIGHT_HELMET.get(), "Iron Knight Helmet");
        add(MWItems.IRON_KNIGHT_CHESTPLATE.get(), "Iron Knight Chestplate");
        add(MWItems.IRON_KNIGHT_LEGGINGS.get(), "Iron Knight Leggings");
        add(MWItems.IRON_KNIGHT_BOOTS.get(), "Iron Knight Boots");

        add(MWItems.SILVER_KNIGHT_HELMET.get(), "Silver Knight Helmet");
        add(MWItems.SILVER_KNIGHT_CHESTPLATE.get(), "Silver Knight Chestplate");
        add(MWItems.SILVER_KNIGHT_LEGGINGS.get(), "Silver Knight Leggings");
        add(MWItems.SILVER_KNIGHT_BOOTS.get(), "Silver Knight Boots");

        add(MWItems.STEEL_KNIGHT_HELMET.get(), "Steel Knight Helmet");
        add(MWItems.STEEL_KNIGHT_CHESTPLATE.get(), "Steel Knight Chestplate");
        add(MWItems.STEEL_KNIGHT_LEGGINGS.get(), "Steel Knight Leggings");
        add(MWItems.STEEL_KNIGHT_BOOTS.get(), "Steel Knight Boots");

        add(MWItems.NETHERITE_KNIGHT_HELMET.get(), "Netherite Knight Helmet");
        add(MWItems.NETHERITE_KNIGHT_CHESTPLATE.get(), "Netherite Knight Chestplate");
        add(MWItems.NETHERITE_KNIGHT_LEGGINGS.get(), "Netherite Knight Leggings");
        add(MWItems.NETHERITE_KNIGHT_BOOTS.get(), "Netherite Knight Boots");

        add(MWItems.SILVER_HORSE_ARMOR.get(), "Silver Horse Armor");
        add(MWItems.STEEL_HORSE_ARMOR.get(), "Steel Horse Armor");
        add(MWItems.NETHERITE_HORSE_ARMOR.get(), "Netherite Horse Armor");

        add(MWItems.LONGBOW.get(), "Longbow");

        add(MWItems.COIF.get(), "Coif");
        add(MWItems.GAMBESON.get(), "Gambeson");

        add(MWItems.FIRE_STARTER.get(), "Fire Starter");

        MWItems.TOOL_PARTS.forEach(addPart());

        MWItems.HEATED_INGOTS.forEach((material, regOb) -> {
            String upperCaseMaterial = upperCaseFirstChar(material);
            add(regOb.get(), "Heated " + upperCaseMaterial + " Ingot");
        });

        MWItems.ARROWS.forEach((material, regOb) -> {
            String upperCaseMaterial = upperCaseFirstChar(material);
            add(regOb.get(), upperCaseMaterial + " Arrow");
        });

        add(Items.ARROW, "Flint Arrow");

        //Blocks
        add(MWBlocks.CHARCOAL_LOG.get(), "Charcoal Log");
        add(MWBlocks.CHARCOAL_PLANKS.get(), "Charcoal Planks");

        add(MWBlocks.STONE_ANVIL.get(), "Stone Anvil");
        add(MWBlocks.STEEL_ANVIL.get(), "Steel Anvil");

        add(MWBlocks.STEEL_BLOCK.get(), "Steel Block");
        add(MWBlocks.SILVER_BLOCK.get(), "Silver Block");
        add(MWBlocks.BRONZE_BLOCK.get(), "Bronze Block");
        add(MWBlocks.TIN_BLOCK.get(), "Tin Block");

        add(MWBlocks.SILVER_ORE.get(), "Silver Ore");
        add(MWBlocks.TIN_ORE.get(), "Tin Ore");
        add(MWBlocks.DEEPSLATE_SILVER_ORE.get(), "Deepslate Silver Ore");
        add(MWBlocks.DEEPSLATE_TIN_ORE.get(), "Deepslate Tin Ore");

        add(MWBlocks.RAW_SILVER_BLOCK.get(), "Raw Silver Block");
        add(MWBlocks.RAW_TIN_BLOCK.get(), "Raw Tin Block");

        add(MWBlocks.ALLOY_FURNACE.get(), "Alloy Furnace");
        add(MWBlocks.SLACK_TUB.get(), "Slack Tub");
        add(MWBlocks.SIMPLE_BLOOMERY.get(), "Simple Bloomery");

        //Enchantments
        add(MWEnchantments.LIGHTNING_STRIKE.get(), "Lightning Strike");
        add(MWEnchantments.MEGA_MINER.get(), "Mega Miner");

        add(MWMobEffects.CLEANSING.get(), "Cleansing");

        add("container.medievalworlds.smithing", "Smithing");
        add("container.medievalworlds.alloy_furnace", "Alloy Furnace");
        add("container.medievalworlds.anvil", "Anvil");
        add("tooltip.medievalworlds.cleansing", "§6Cleansing");

        add("itemGroup.medievalworlds.blocks", "MedievalWorlds Blocks");
        add("itemGroup.medievalworlds.combat", "MedievalWorlds Combat");
        add("itemGroup.medievalworlds.materials", "MedievalWorlds Crafting Materials");
        add("itemGroup.medievalworlds.tools", "MedievalWorlds Tools");
    }

    private BiConsumer<? super String, ? super Pair<RegistryObject<Item>, Boolean>> addPart() {
        return (key, pair) -> {
            StringBuilder builder = new StringBuilder();
            String[] parts = key.split("_");
            for (String part : parts) {
                part = part.substring(0, 1).toUpperCase() + part.substring(1);
                builder.append(part);
                builder.append(" ");
            }
            add(pair.getFirst().get(), builder.toString().trim());
        };
    }

    public static String upperCaseFirstChar(String s) {
        return s.substring(0, 1).toUpperCase() + s.substring(1);
    }

}
