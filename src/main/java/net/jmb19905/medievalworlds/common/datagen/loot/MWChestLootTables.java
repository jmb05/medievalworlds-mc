package net.jmb19905.medievalworlds.common.datagen.loot;

import net.minecraft.data.loot.ChestLoot;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootTable;
import org.jetbrains.annotations.NotNull;

import java.util.function.BiConsumer;

public class MWChestLootTables extends ChestLoot {

    @Override
    public void accept(@NotNull BiConsumer<ResourceLocation, LootTable.Builder> consumer) {

    }
}
