package net.jmb19905.medievalworlds.containers;

import net.jmb19905.medievalworlds.registries.BlockRegistryHandler;
import net.jmb19905.medievalworlds.registries.ContainerTypeRegistryHandler;
import net.jmb19905.medievalworlds.tileentites.AlloyFurnaceTileEntity;
import net.jmb19905.medievalworlds.util.FunctionalIntReferenceHolder;
import net.jmb19905.medievalworlds.util.slots.AlloyInputSlot;
import net.jmb19905.medievalworlds.util.slots.FuelInputSlot;
import net.jmb19905.medievalworlds.util.slots.OutputSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IWorldPosCallable;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;
import java.util.Objects;

public class AlloyFurnaceContainer extends Container {

    private final AlloyFurnaceTileEntity tileEntity;
    private final IWorldPosCallable canInteractWithCallable;
    public FunctionalIntReferenceHolder currentAlloyTime;
    public FunctionalIntReferenceHolder currentBurnTime;
    public FunctionalIntReferenceHolder currentMaxBurnTime;

    public AlloyFurnaceContainer(final int windowID, final PlayerInventory playerInventory, final AlloyFurnaceTileEntity tile) {
        super(ContainerTypeRegistryHandler.ALLOY_FURNACE.get(), windowID);
        this.tileEntity = tile;
        this.canInteractWithCallable = IWorldPosCallable.of(Objects.requireNonNull(tile.getWorld()), tile.getPos());

        final int slotSizePlus2 = 18;
        final int startX = 8;

        // Hotbar
        int hotbarY = 142;
        for (int column = 0; column < 9; column++) {
            this.addSlot(new Slot(playerInventory, column, startX + (column * slotSizePlus2), hotbarY));
        }

        // Main Player Inventory
        final int startY = 84;

        for (int row = 0; row < 3; row++) {
            for (int column = 0; column < 9; column++) {
                this.addSlot(new Slot(playerInventory, 9 + (row * 9) + column, startX + (column * slotSizePlus2),
                        startY + (row * slotSizePlus2)));
            }
        }

        //Input
        this.addSlot(new AlloyInputSlot(tile.getInventory(), 0, 20, 16, tile.getWorld()));
        this.addSlot(new AlloyInputSlot(tile.getInventory(), 1, 56, 16, tile.getWorld()));
        this.addSlot(new FuelInputSlot(tile.getInventory(), 2, 38, 51));

        //Output
        this.addSlot(new OutputSlot(tile.getInventory(), 3, 116, 35));

        this.trackInt(currentAlloyTime = new FunctionalIntReferenceHolder(() -> this.tileEntity.currentAlloyTime, val -> this.tileEntity.currentAlloyTime = val));
        this.trackInt(currentBurnTime = new FunctionalIntReferenceHolder(() -> this.tileEntity.currentBurnTime >= 0 ? this.tileEntity.currentBurnTime : this.tileEntity.currentMaxBurnTime, val -> this.tileEntity.currentBurnTime = val));
        this.trackInt(currentMaxBurnTime = new FunctionalIntReferenceHolder(() -> this.tileEntity.currentMaxBurnTime, val -> this.tileEntity.currentMaxBurnTime = val));
    }

    public AlloyFurnaceContainer(final int windowID, final PlayerInventory playerInventory, final PacketBuffer data) {
        this(windowID, playerInventory, getTileEntity(playerInventory, data));
    }

    private static AlloyFurnaceTileEntity getTileEntity(final PlayerInventory playerInv, final PacketBuffer data) {
        Objects.requireNonNull(playerInv, "playerInv cannot be null");
        Objects.requireNonNull(data, "data cannot be null");
        final TileEntity tileAtPos = playerInv.player.world.getTileEntity(data.readBlockPos());
        if (tileAtPos instanceof AlloyFurnaceTileEntity) {
            return (AlloyFurnaceTileEntity) tileAtPos;
        }
        throw new IllegalStateException("TileEntity is not correct " + tileAtPos);
    }

    @Override
    public boolean canInteractWith(@Nonnull PlayerEntity playerIn) {
        return isWithinUsableDistance(canInteractWithCallable, playerIn, BlockRegistryHandler.ALLOY_FURNACE_BLOCK.get());
    }

    @Nonnull
    @Override
    public ItemStack transferStackInSlot(@Nonnull final PlayerEntity player, final int index) {
        ItemStack returnStack = ItemStack.EMPTY;
        final Slot slot = this.inventorySlots.get(index);
        if (slot != null && slot.getHasStack()) {
            final ItemStack slotStack = slot.getStack();
            returnStack = slotStack.copy();

            final int containerSlots = this.inventorySlots.size() - player.inventory.mainInventory.size();
            if (index < containerSlots) {
                if (!mergeItemStack(slotStack, containerSlots, this.inventorySlots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!mergeItemStack(slotStack, 0, containerSlots, false)) {
                return ItemStack.EMPTY;
            }
            if (slotStack.getCount() == 0) {
                slot.putStack(ItemStack.EMPTY);
            } else {
                slot.onSlotChanged();
            }
            if (slotStack.getCount() == returnStack.getCount()) {
                return ItemStack.EMPTY;
            }
            slot.onTake(player, slotStack);
        }
        return returnStack;
    }

    @OnlyIn(Dist.CLIENT)
    public int getSmeltProgressionScaled(){
        return this.currentAlloyTime.get() != 0 ? this.currentAlloyTime.get() * 24 / this.tileEntity.maxAlloyTime : 0;
    }

    @OnlyIn(Dist.CLIENT)
    public int getBurnProgressionScaled(){
        return this.currentBurnTime.get() != 0 && this.currentMaxBurnTime.get() != 0 ? this.currentBurnTime.get() * 13 / this.tileEntity.currentMaxBurnTime : 0;
    }

    @OnlyIn(Dist.CLIENT)
    public int getCurrentMaxBurnTime(){
        return this.currentMaxBurnTime.get();
    }

}
