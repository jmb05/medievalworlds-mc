package net.jmb19905.medievalworlds.common.block;

import net.jmb19905.medievalworlds.common.blockentities.SmithingTableBlockEntity;
import net.jmb19905.medievalworlds.common.registries.MWBlockEntityTypes;
import net.minecraft.stats.Stats;
import net.minecraft.world.entity.player.Player;

public class CustomSmithingTable extends MWCraftingBlock<SmithingTableBlockEntity> {

    public CustomSmithingTable(Properties properties) {
        super(MWBlockEntityTypes.SMITHING_TABLE, properties);
    }

    @Override
    protected void awardUseStat(Player player) {
        player.awardStat(Stats.INTERACT_WITH_SMITHING_TABLE);
    }
}