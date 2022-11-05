package net.jmb19905.medievalworlds.common.menus;

import net.jmb19905.medievalworlds.common.blockentities.SmelteryBlockEntity;
import net.jmb19905.medievalworlds.common.inv.slots.InputSlot;
import net.jmb19905.medievalworlds.common.registries.MWBlocks;
import net.jmb19905.medievalworlds.common.registries.MWMenuTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.function.Predicate;

public class SmelteryMenu extends MWInventoryMenu<SmelteryBlockEntity> {

    public SmelteryMenu(int windowId, Inventory inventory, SmelteryBlockEntity blockEntity) {
        super(MWMenuTypes.SMELTERY.get(), windowId, inventory, blockEntity);
    }

    public SmelteryMenu(int windowId, Inventory inventory, FriendlyByteBuf buffer) {
        this(windowId, inventory, getSmelteryBlockEntity(inventory, buffer));
    }

    protected static SmelteryBlockEntity getSmelteryBlockEntity(final Inventory playerInv, final FriendlyByteBuf data) {
        final BlockEntity blockEntityAtPos = getBlockEntity(playerInv, data);
        if (blockEntityAtPos instanceof SmelteryBlockEntity smeltery) {
            return smeltery;
        }
        throw new IllegalStateException("TileEntity is not correct " + blockEntityAtPos);
    }

    @Override
    protected int addInputSlots() {
        this.addSlot(new InputSlot(blockEntity.getInventory(), 0, 8, 20));
        this.addSlot(new InputSlot(blockEntity.getInventory(), 1, 8, 38));
        this.addSlot(new InputSlot(blockEntity.getInventory(), 2, 8, 56));
        this.addSlot(new InputSlot(blockEntity.getInventory(), 3, 152, 56));
        return 0;
    }

    @Override
    protected int addOutputSlots() {
        return 0;
    }

    @Override
    public Predicate<BlockState> isValidBlockState() {
        return blockState -> blockState.getBlock() == MWBlocks.SIMPLE_SMELTERY.get();
    }

    @OnlyIn(Dist.CLIENT)
    public int getBurnProgressionScaled(){
        return (this.blockEntity.currentBurnTime == 0 || this.blockEntity.currentMaxBurnTime == 0) ? 0 : (this.blockEntity.currentBurnTime * 13 / this.blockEntity.currentMaxBurnTime);
    }

    @OnlyIn(Dist.CLIENT)
    public int getCurrentMaxBurnTime(){
        return this.blockEntity.currentMaxBurnTime;
    }
}
