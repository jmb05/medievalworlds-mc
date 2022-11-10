package net.jmb19905.medievalworlds.common.menus;

import net.jmb19905.medievalworlds.common.block.hearth.HearthBlock;
import net.jmb19905.medievalworlds.common.block.hearth.HearthPartProperty;
import net.jmb19905.medievalworlds.common.blockentities.HearthBlockEntity;
import net.jmb19905.medievalworlds.common.inv.slots.FuelInputSlot;
import net.jmb19905.medievalworlds.common.inv.slots.InputSlot;
import net.jmb19905.medievalworlds.common.inv.slots.OutputSlot;
import net.jmb19905.medievalworlds.common.registries.MWBlocks;
import net.jmb19905.medievalworlds.common.registries.MWMenuTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.function.Predicate;

public class HearthMenu extends MWInventoryMenu<HearthBlockEntity> {

    public HearthMenu(int windowId, Inventory inventory, HearthBlockEntity blockEntity) {
        super(MWMenuTypes.HEARTH.get(), windowId, inventory, blockEntity);
    }

    protected static HearthBlockEntity getHearthBlockEntity(final Inventory playerInv, final FriendlyByteBuf data) {
        final BlockEntity blockEntityAtPos = getBlockEntity(playerInv, data);
        if (blockEntityAtPos instanceof HearthBlockEntity hearthBlockEntity) {
            return hearthBlockEntity;
        }
        throw new IllegalStateException("TileEntity is not correct " + blockEntityAtPos);
    }

    public HearthMenu(int windowId, Inventory inventory, FriendlyByteBuf buffer) {
        this(windowId, inventory, getHearthBlockEntity(inventory, buffer));
    }

    @Override
    protected int addInputSlots() {
        this.addSlot(new InputSlot(blockEntity.getInventory(), 0, 38, 16));
        this.addSlot(new FuelInputSlot(blockEntity.getInventory(), 1, 38, 51));
        return 2;
    }

    @Override
    protected int addOutputSlots() {
        this.addSlot(new OutputSlot(blockEntity.getInventory(), 2, 116, 35));
        return 1;
    }

    @OnlyIn(Dist.CLIENT)
    public int getSmeltProgressionScaled(){
        return (this.blockEntity.currentHeatTime != 0) ? (this.blockEntity.currentHeatTime * 24 / this.blockEntity.maxHeatTime) : 0;
    }

    @OnlyIn(Dist.CLIENT)
    public int getBurnProgressionScaled(){
        return (this.blockEntity.currentBurnTime == 0 || this.blockEntity.currentMaxBurnTime == 0) ? 0 : (this.blockEntity.currentBurnTime * 13 / this.blockEntity.currentMaxBurnTime);
    }

    @OnlyIn(Dist.CLIENT)
    public int getCurrentMaxBurnTime(){
        return this.blockEntity.currentMaxBurnTime;
    }

    @Override
    public Predicate<BlockState> isValidBlockState() {
        return state -> state.is(MWBlocks.HEARTH.get()) && state.getValue(HearthBlock.PART) == HearthPartProperty.MAIN;
    }
}
