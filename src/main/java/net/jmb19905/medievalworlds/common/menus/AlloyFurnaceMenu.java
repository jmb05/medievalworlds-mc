package net.jmb19905.medievalworlds.common.menus;

import net.jmb19905.medievalworlds.common.blockentities.AlloyFurnaceBlockEntity;
import net.jmb19905.medievalworlds.common.inv.slots.FuelInputSlot;
import net.jmb19905.medievalworlds.common.inv.slots.InputSlot;
import net.jmb19905.medievalworlds.common.inv.slots.OutputSlot;
import net.jmb19905.medievalworlds.common.registries.MWBlocks;
import net.jmb19905.medievalworlds.common.registries.MWMenuTypes;
import net.jmb19905.medievalworlds.common.registries.MWRecipeSerializers;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.function.Predicate;

public class AlloyFurnaceMenu extends MWInventoryMenu<AlloyFurnaceBlockEntity> {

    public AlloyFurnaceMenu(final int windowID, final Inventory playerInventory, final AlloyFurnaceBlockEntity blockEntity) {
        super(MWMenuTypes.ALLOY.get(), windowID, playerInventory, blockEntity);
    }

    public AlloyFurnaceMenu(final int windowID, final Inventory playerInventory, final FriendlyByteBuf data) {
        this(windowID, playerInventory, getAlloyFurnaceBlockEntity(playerInventory, data));
    }

    protected static AlloyFurnaceBlockEntity getAlloyFurnaceBlockEntity(final Inventory playerInv, final FriendlyByteBuf data) {
        final BlockEntity blockEntityAtPos = getBlockEntity(playerInv, data);
        if (blockEntityAtPos instanceof AlloyFurnaceBlockEntity alloyFurnaceBlockEntity) {
            return alloyFurnaceBlockEntity;
        }
        throw new IllegalStateException("TileEntity is not correct " + blockEntityAtPos);
    }

    @Override
    protected int addInputSlots() {
        this.addSlot(new InputSlot(blockEntity.getInventory(), 0, 20, 16));
        this.addSlot(new InputSlot(blockEntity.getInventory(), 1, 56, 16));
        this.addSlot(new FuelInputSlot(blockEntity.getInventory(), 2, 38, 51, MWRecipeSerializers.ALLOY_TYPE));
        return 3;
    }

    @Override
    protected int addOutputSlots() {
        this.addSlot(new OutputSlot(blockEntity.getInventory(), 3, 116, 35));
        return 1;
    }

    @OnlyIn(Dist.CLIENT)
    public int getSmeltProgressionScaled(){
        return (this.blockEntity.currentAlloyTime != 0) ? (this.blockEntity.currentAlloyTime * 24 / this.blockEntity.maxAlloyTime) : 0;
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
        return state -> state.is(MWBlocks.ALLOY_FURNACE.get());
    }
}