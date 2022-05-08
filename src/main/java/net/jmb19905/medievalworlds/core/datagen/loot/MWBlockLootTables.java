package net.jmb19905.medievalworlds.core.datagen.loot;

import net.jmb19905.medievalworlds.core.registries.MWBlocks;
import net.jmb19905.medievalworlds.core.registries.MWItems;
import net.minecraft.data.loot.BlockLoot;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

public class MWBlockLootTables extends BlockLoot {

    @Override
    protected void addTables() {
        this.dropSelf(MWBlocks.BRONZE_BLOCK.get());
        this.dropSelf(MWBlocks.TIN_BLOCK.get());
        this.dropSelf(MWBlocks.RUBY_BLOCK.get());
        this.dropSelf(MWBlocks.STEEL_BLOCK.get());
        this.dropSelf(MWBlocks.SILVER_BLOCK.get());
        this.dropSelf(MWBlocks.RAW_TIN_BLOCK.get());
        this.dropSelf(MWBlocks.RAW_SILVER_BLOCK.get());
        this.dropSelf(MWBlocks.ALLOY_FURNACE_BLOCK.get());
        this.dropSelf(MWBlocks.BELLOWS.get());
        this.dropSelf(MWBlocks.FORGE_BLOCK.get());
        this.dropSelf(MWBlocks.LIMESTONE_BLOCK.get());
        this.dropSelf(MWBlocks.LIMESTONE_BRICKS_BLOCK.get());
        this.dropSelf(MWBlocks.SLACK_TUB_BLOCK.get());
        this.dropSelf(MWBlocks.BRICK_CHIMNEY.get());
        this.dropSelf(MWBlocks.QUERN.get());

        this.add(MWBlocks.RUBY_ORE_BLOCK.get(),
                (block -> createOreDrop(MWBlocks.RUBY_ORE_BLOCK.get(), MWItems.RUBY.get())));

        this.add(MWBlocks.DEEPSLATE_RUBY_ORE_BLOCK.get(),
                (block -> createOreDrop(MWBlocks.DEEPSLATE_RUBY_ORE_BLOCK.get(), MWItems.RUBY.get())));

        this.add(MWBlocks.TIN_ORE_BLOCK.get(),
                (block -> createOreDrop(MWBlocks.TIN_ORE_BLOCK.get(), MWItems.RAW_TIN.get())));

        this.add(MWBlocks.DEEPSLATE_TIN_ORE_BLOCK.get(),
                (block -> createOreDrop(MWBlocks.DEEPSLATE_TIN_ORE_BLOCK.get(), MWItems.RAW_TIN.get())));

        this.add(MWBlocks.SILVER_ORE_BLOCK.get(),
                (block -> createOreDrop(MWBlocks.SILVER_ORE_BLOCK.get(), MWItems.RAW_SILVER.get())));

        this.add(MWBlocks.DEEPSLATE_SILVER_ORE_BLOCK.get(),
                (block -> createOreDrop(MWBlocks.DEEPSLATE_SILVER_ORE_BLOCK.get(), MWItems.RAW_SILVER.get())));

        this.add(MWBlocks.CHARCOAL_LOG.get(),
                (block) -> createSingleItemTableWithSilkTouch(block, Items.CHARCOAL, ConstantValue.exactly(4.0F)));

        this.add(MWBlocks.CHARCOAl_PLANKS.get(),
                (block) -> createSingleItemTableWithSilkTouch(block, Items.CHARCOAL, ConstantValue.exactly(4.0F)));

        this.add(MWBlocks.BLOOMERY.get(), empty());
        this.add(MWBlocks.STONE_ANVIL.get(), empty());
    }

    protected static LootTable.Builder empty() {
        return LootTable.lootTable();
    }

    @Override
    protected @NotNull Iterable<Block> getKnownBlocks() {
        return MWBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
    }
}
