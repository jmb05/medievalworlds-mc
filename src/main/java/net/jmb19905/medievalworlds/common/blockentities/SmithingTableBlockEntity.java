package net.jmb19905.medievalworlds.common.blockentities;

import net.jmb19905.medievalworlds.common.blockentities.abstr.MWNamedInventoryBlockEntity;
import net.jmb19905.medievalworlds.common.menus.MWSmithingMenu;
import net.jmb19905.medievalworlds.common.registries.MWBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Predicate;

public class SmithingTableBlockEntity extends MWNamedInventoryBlockEntity {
    private static final Component CONTAINER_TITLE = Component.translatable("container.medievalworlds.smithing");

    public SmithingTableBlockEntity(BlockPos pos, BlockState state) {
        super(MWBlockEntityTypes.SMITHING_TABLE.get(), 3, pos, state);
    }

    @Override
    public Predicate<Integer> isDropSlot() {
        return i -> i != 2;
    }

    @Override
    public @NotNull Component getDefaultName() {
        return CONTAINER_TITLE;
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int windowId, @NotNull Inventory inv, @NotNull Player player) {
        return new MWSmithingMenu(windowId, inv, this);
    }
}