package net.jmb19905.medievalworlds.common.block.forge;

import net.jmb19905.medievalworlds.common.blockentities.AnvilBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ForgeBaseBlock extends AbstractForgeBlock{

    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final BooleanProperty LIT = BlockStateProperties.LIT;

    public ForgeBaseBlock(Properties properties) {
        super(properties);
        registerDefaultState(getStateDefinition().any().setValue(FACING, Direction.NORTH).setValue(LIT, false));
    }

    @Override
    protected void createBlockStateDefinition(@Nonnull StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(FACING, LIT);
    }

    public static boolean setItem(Level level, BlockPos pos, Item item) {
        BlockEntity entity = level.getBlockEntity(pos);
        if(!(entity instanceof AnvilBlockEntity anvilEntity)){
            return false;
        }
        anvilEntity.getInventory().setStackInSlot(0, new ItemStack(item));
        return true;
    }

    public static Item clearItem(Level level, BlockPos pos) {
        BlockEntity entity = level.getBlockEntity(pos);
        if(!(entity instanceof AnvilBlockEntity anvilEntity)) return Items.AIR;
        Item anvilStack = anvilEntity.getInventory().getStackInSlot(0).getItem();
        anvilEntity.getInventory().setStackInSlot(0, ItemStack.EMPTY);
        return anvilStack;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(@Nonnull BlockPos pos, @Nonnull BlockState state) {
        return null;
    }
}
