package net.jmb19905.medievalworlds.common.block;

import net.jmb19905.medievalworlds.common.blockentities.FletchingTableBlockEntity;
import net.jmb19905.medievalworlds.common.registries.MWBlockEntityTypes;

public class CustomFletchingTable extends MWCraftingBlock<FletchingTableBlockEntity> {
    public CustomFletchingTable(Properties properties) {
        super(MWBlockEntityTypes.FLETCHING_TABLE, properties);
    }
}
