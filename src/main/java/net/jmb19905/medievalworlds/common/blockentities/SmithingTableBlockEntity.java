package net.jmb19905.medievalworlds.common.blockentities;

import net.jmb19905.medievalworlds.common.menus.CustomSmithingMenu;
import net.jmb19905.medievalworlds.common.registries.MWBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SmithingTableBlockEntity extends MWCraftingBlockEntity {
    private static final Component CONTAINER_TITLE = Component.translatable("container.medievalworlds.smithing");

    public SmithingTableBlockEntity(BlockPos pos, BlockState state) {
        super(MWBlockEntityTypes.SMITHING_TABLE.get(), 3, pos, state);
    }

    @Override
    public @NotNull Component getDisplayName() {
        return CONTAINER_TITLE;
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int windowId, @NotNull Inventory inv, @NotNull Player player) {
        return new CustomSmithingMenu(windowId, inv, this);
    }
}