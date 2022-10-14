package net.jmb19905.medievalworlds.common.datagen.loot;

import net.jmb19905.medievalworlds.common.registries.MWBlocks;
import net.jmb19905.medievalworlds.common.registries.MWItems;
import net.minecraft.data.loot.BlockLoot;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

public class MWBlockLootTables extends BlockLoot {

    @Override
    protected void addTables() {
        this.dropSelf(MWBlocks.STONE_ANVIL.get());
        this.dropSelf(MWBlocks.STEEL_ANVIL.get());

        this.dropSelf(MWBlocks.STEEL_BLOCK.get());
        this.dropSelf(MWBlocks.SILVER_BLOCK.get());
        this.dropSelf(MWBlocks.BRONZE_BLOCK.get());
        this.dropSelf(MWBlocks.TIN_BLOCK.get());

        this.dropSelf(MWBlocks.RAW_SILVER_BLOCK.get());
        this.dropSelf(MWBlocks.RAW_TIN_BLOCK.get());

        this.add(MWBlocks.SILVER_ORE.get(), (block) -> createOreDrop(block, MWItems.RAW_SILVER.get()));
        this.add(MWBlocks.DEEPSLATE_SILVER_ORE.get(), (block) -> createOreDrop(block, MWItems.RAW_SILVER.get()));
        this.add(MWBlocks.TIN_ORE.get(), (block) -> createOreDrop(block, MWItems.RAW_TIN.get()));
        this.add(MWBlocks.DEEPSLATE_TIN_ORE.get(), (block) -> createOreDrop(block, MWItems.RAW_TIN.get()));

        this.add(MWBlocks.CHARCOAL_LOG.get(),
                (block) -> createSingleItemTableWithSilkTouch(block, Items.CHARCOAL, ConstantValue.exactly(4.0F)));

        this.add(MWBlocks.CHARCOAL_PLANKS.get(),
                (block) -> createSingleItemTableWithSilkTouch(block, Items.CHARCOAL, ConstantValue.exactly(4.0F)));
    }

    protected static LootTable.Builder empty() {
        return LootTable.lootTable();
    }

    @Override
    protected @NotNull Iterable<Block> getKnownBlocks() {
        return MWBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
    }
}
