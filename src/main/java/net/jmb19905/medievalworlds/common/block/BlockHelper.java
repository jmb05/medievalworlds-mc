package net.jmb19905.medievalworlds.common.block;

import net.jmb19905.medievalworlds.common.blockentities.abstr.MWInventoryBlockEntity;
import net.jmb19905.medievalworlds.common.inv.MWItemHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class BlockHelper {
    //Copied from https://forums.minecraftforge.net/topic/74979-1144-rotate-voxel-shapes/
    public static VoxelShape rotateVoxelShape(Direction from, Direction to, VoxelShape shape) {
        VoxelShape[] buffer = new VoxelShape[]{ shape, Shapes.empty() };

        int times = (to.get2DDataValue() - from.get2DDataValue() + 4) % 4;
        for (int i = 0; i < times; i++) {
            buffer[0].forAllBoxes((minX, minY, minZ, maxX, maxY, maxZ) -> buffer[1] = Shapes.or(buffer[1], Shapes.create(1-maxZ, minY, minX, 1-minZ, maxY, maxX)));
            buffer[0] = buffer[1];
            buffer[1] = Shapes.empty();
        }

        return buffer[0];
    }

    public static void dropBlockEntityContents(Level level, BlockPos pos, MWInventoryBlockEntity blockEntity) {
        MWItemHandler inventory = blockEntity.getInventory();
        for (int i=0;i<inventory.getSlots();i++) {
            if(blockEntity.isDropSlot().test(i)) {
                ItemEntity itemEntity = new ItemEntity(level, pos.getX(), pos.getY(), pos.getZ(), inventory.getStackInSlot(i));
                level.addFreshEntity(itemEntity);
            }
        }
    }

}