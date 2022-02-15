package net.jmb19905.medievalworlds.common.block.smithing;

import net.jmb19905.medievalworlds.common.menus.CustomSmithingMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.SmithingTableBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class CustomSmithingTable extends SmithingTableBlock {
    private static final Component CONTAINER_TITLE = new TranslatableComponent("container.medievalworlds.smithing");

    public CustomSmithingTable(Properties properties) {
        super(properties);
    }

    @Override
    public MenuProvider getMenuProvider(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos) {
        return new SimpleMenuProvider((i, inv, player) -> new CustomSmithingMenu(i, inv, ContainerLevelAccess.create(level, pos)), CONTAINER_TITLE);
    }
}
