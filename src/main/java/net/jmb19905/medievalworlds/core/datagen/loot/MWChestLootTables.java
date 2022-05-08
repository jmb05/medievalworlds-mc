package net.jmb19905.medievalworlds.core.datagen.loot;

import net.jmb19905.medievalworlds.core.registries.MWItems;
import net.minecraft.data.loot.ChestLoot;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import java.util.function.BiConsumer;

public class MWChestLootTables extends ChestLoot {
    @Override
    public void accept(BiConsumer<ResourceLocation, LootTable.Builder> consumer) {
        consumer.accept(BuiltInLootTables.VILLAGE_WEAPONSMITH, LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(3.0F, 8.0F))
                        .add(LootItem.lootTableItem(Items.DIAMOND).setWeight(3)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F))))
                        .add(LootItem.lootTableItem(MWItems.BRONZE_INGOT.get()).setWeight(15)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 10.0F))))
                        .add(LootItem.lootTableItem(Items.IRON_INGOT).setWeight(7)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 5.0F))))
                        .add(LootItem.lootTableItem(Items.GOLD_INGOT).setWeight(5)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F))))
                        .add(LootItem.lootTableItem(Items.BREAD).setWeight(15)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F))))
                        .add(LootItem.lootTableItem(Items.APPLE).setWeight(15)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F))))
                        .add(LootItem.lootTableItem(Items.IRON_PICKAXE).setWeight(3))
                        .add(LootItem.lootTableItem(Items.IRON_SWORD).setWeight(3))
                        .add(LootItem.lootTableItem(Items.IRON_CHESTPLATE).setWeight(3))
                        .add(LootItem.lootTableItem(Items.IRON_HELMET).setWeight(3))
                        .add(LootItem.lootTableItem(Items.IRON_LEGGINGS).setWeight(3))
                        .add(LootItem.lootTableItem(Items.IRON_BOOTS).setWeight(3))
                        .add(LootItem.lootTableItem(MWItems.BRONZE_PICKAXE.get()).setWeight(5))
                        .add(LootItem.lootTableItem(MWItems.BRONZE_SWORD.get()).setWeight(5))
                        .add(LootItem.lootTableItem(MWItems.BRONZE_CHESTPLATE.get()).setWeight(5))
                        .add(LootItem.lootTableItem(MWItems.BRONZE_HELMET.get()).setWeight(5))
                        .add(LootItem.lootTableItem(MWItems.BRONZE_LEGGINGS.get()).setWeight(5))
                        .add(LootItem.lootTableItem(MWItems.BRONZE_BOOTS.get()).setWeight(5))
                        .add(LootItem.lootTableItem(Blocks.OBSIDIAN).setWeight(5)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(3.0F, 7.0F))))
                        .add(LootItem.lootTableItem(Blocks.OAK_SAPLING).setWeight(5)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(3.0F, 7.0F))))
                        .add(LootItem.lootTableItem(Items.SADDLE).setWeight(3))
                        .add(LootItem.lootTableItem(Items.IRON_HORSE_ARMOR))
                        .add(LootItem.lootTableItem(Items.GOLDEN_HORSE_ARMOR))
                        .add(LootItem.lootTableItem(Items.DIAMOND_HORSE_ARMOR))));
        consumer.accept(BuiltInLootTables.VILLAGE_TOOLSMITH, LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(3.0F, 8.0F))
                        .add(LootItem.lootTableItem(Items.DIAMOND).setWeight(1)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F))))
                        .add(LootItem.lootTableItem(Items.IRON_INGOT).setWeight(3)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 5.0F))))
                        .add(LootItem.lootTableItem(MWItems.BRONZE_INGOT.get()).setWeight(15)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 10.0F))))
                        .add(LootItem.lootTableItem(Items.GOLD_INGOT).setWeight(1)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F))))
                        .add(LootItem.lootTableItem(Items.BREAD).setWeight(15)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F))))
                        .add(LootItem.lootTableItem(Items.IRON_PICKAXE).setWeight(3))
                        .add(LootItem.lootTableItem(MWItems.BRONZE_PICKAXE.get()).setWeight(5))
                        .add(LootItem.lootTableItem(Items.COAL).setWeight(1)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F))))
                        .add(LootItem.lootTableItem(Items.STICK).setWeight(20)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F))))
                        .add(LootItem.lootTableItem(Items.IRON_SHOVEL).setWeight(3))
                        .add(LootItem.lootTableItem(MWItems.BRONZE_SHOVEL.get()).setWeight(5))));
        consumer.accept(BuiltInLootTables.VILLAGE_ARMORER, LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(1.0F, 5.0F))
                        .add(LootItem.lootTableItem(Items.IRON_INGOT).setWeight(2)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F))))
                        .add(LootItem.lootTableItem(MWItems.BRONZE_INGOT.get()).setWeight(15)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 10.0F))))
                        .add(LootItem.lootTableItem(Items.BREAD).setWeight(4)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 4.0F))))
                        .add(LootItem.lootTableItem(Items.IRON_HELMET).setWeight(1))
                        .add(LootItem.lootTableItem(MWItems.BRONZE_HELMET.get()).setWeight(3))
                        .add(LootItem.lootTableItem(Items.EMERALD).setWeight(1))));
    }
}
