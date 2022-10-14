package net.jmb19905.medievalworlds.common.registries;

import net.jmb19905.medievalworlds.MedievalWorlds;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class MWTags {

    public static class Items {
        public static final TagKey<Item> STAFFS = ItemTags.create(new ResourceLocation(MedievalWorlds.MOD_ID, "staffs"));
        public static final TagKey<Item> INGOTS_STEEL = ItemTags.create(new ResourceLocation("forge", "ingots/steel"));
        public static final TagKey<Item> INGOTS_SILVER = ItemTags.create(new ResourceLocation("forge", "ingots/silver"));
        public static final TagKey<Item> INGOTS_BRONZE = ItemTags.create(new ResourceLocation("forge", "ingots/bronze"));
        public static final TagKey<Item> INGOTS_TIN = ItemTags.create(new ResourceLocation("forge", "ingots/tin"));
        public static final TagKey<Item> NUGGETS_STEEL = ItemTags.create(new ResourceLocation("forge", "nuggets/steel"));
        public static final TagKey<Item> NUGGETS_SILVER = ItemTags.create(new ResourceLocation("forge", "nuggets/silver"));
        public static final TagKey<Item> NUGGETS_BRONZE = ItemTags.create(new ResourceLocation("forge", "nuggets/bronze"));
        public static final TagKey<Item> NUGGETS_TIN = ItemTags.create(new ResourceLocation("forge", "nuggets/tin"));

        public static final TagKey<Item> ORES_SILVER = ItemTags.create(new ResourceLocation("forge", "ores/silver"));
        public static final TagKey<Item> ORES_TIN = ItemTags.create(new ResourceLocation("forge", "ores/tin"));
        public static final TagKey<Item> STORAGE_BLOCKS_STEEL = ItemTags.create(new ResourceLocation("forge", "storage_blocks/steel"));
        public static final TagKey<Item> STORAGE_BLOCKS_SILVER = ItemTags.create(new ResourceLocation("forge", "storage_blocks/silver"));
        public static final TagKey<Item> STORAGE_BLOCKS_BRONZE = ItemTags.create(new ResourceLocation("forge", "storage_blocks/bronze"));
        public static final TagKey<Item> STORAGE_BLOCKS_TIN = ItemTags.create(new ResourceLocation("forge", "storage_blocks/tin"));
        public static final TagKey<Item> STORAGE_BLOCKS_RAW_SILVER = ItemTags.create(new ResourceLocation("forge", "storage_blocks/raw_silver"));
        public static final TagKey<Item> STORAGE_BLOCKS_RAW_TIN = ItemTags.create(new ResourceLocation("forge", "storage_blocks/raw_tin"));

    }

    public static class Blocks {
        public static final TagKey<Block> ORES_SILVER = BlockTags.create(new ResourceLocation("forge", "ores/silver"));
        public static final TagKey<Block> ORES_TIN = BlockTags.create(new ResourceLocation("forge", "ores/tin"));
        public static final TagKey<Block> STORAGE_BLOCKS_STEEL = BlockTags.create(new ResourceLocation("forge", "storage_blocks/steel"));
        public static final TagKey<Block> STORAGE_BLOCKS_SILVER = BlockTags.create(new ResourceLocation("forge", "storage_blocks/silver"));
        public static final TagKey<Block> STORAGE_BLOCKS_BRONZE = BlockTags.create(new ResourceLocation("forge", "storage_blocks/bronze"));
        public static final TagKey<Block> STORAGE_BLOCKS_TIN = BlockTags.create(new ResourceLocation("forge", "storage_blocks/tin"));
        public static final TagKey<Block> STORAGE_BLOCKS_RAW_SILVER = BlockTags.create(new ResourceLocation("forge", "storage_blocks/raw_silver"));
        public static final TagKey<Block> STORAGE_BLOCKS_RAW_TIN = BlockTags.create(new ResourceLocation("forge", "storage_blocks/raw_tin"));
    }

}
