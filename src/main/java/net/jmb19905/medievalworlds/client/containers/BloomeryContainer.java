package net.jmb19905.medievalworlds.client.containers;

import net.jmb19905.medievalworlds.common.registries.BlockRegistryHandler;
import net.jmb19905.medievalworlds.common.registries.ContainerTypeRegistryHandler;
import net.jmb19905.medievalworlds.common.tileentites.BloomeryTileEntity;
import net.jmb19905.medievalworlds.util.FunctionalIntReferenceHolder;
import net.jmb19905.medievalworlds.util.slots.BloomInputSlot;
import net.jmb19905.medievalworlds.util.slots.FuelInputSlot;
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

public class BloomeryContainer extends Container {

    private final BloomeryTileEntity.Bottom tileEntity;
    private final IWorldPosCallable canInteractWithCallable;
    public FunctionalIntReferenceHolder currentSmeltTime;
    public FunctionalIntReferenceHolder currentBurnTime;
    public FunctionalIntReferenceHolder currentMaxBurnTime;

    public BloomeryContainer(final int windowID, final PlayerInventory playerInventory, final BloomeryTileEntity.Bottom tile) {
        super(ContainerTypeRegistryHandler.BLOOMERY.get(), windowID);
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
        this.addSlot(new BloomInputSlot(tile.getInventory(), 0, 1, 79, 16));
        this.addSlot(new FuelInputSlot(tile.getInventory(), 1, 79, 51));

        this.trackInt(currentSmeltTime = new FunctionalIntReferenceHolder(() -> this.tileEntity.currentSmeltTime, val -> this.tileEntity.currentSmeltTime = val));
        this.trackInt(currentBurnTime = new FunctionalIntReferenceHolder(() -> this.tileEntity.currentBurnTime >= 0 ? this.tileEntity.currentBurnTime : this.tileEntity.currentMaxBurnTime, val -> this.tileEntity.currentBurnTime = val));
        this.trackInt(currentMaxBurnTime = new FunctionalIntReferenceHolder(() -> this.tileEntity.currentMaxBurnTime, val -> this.tileEntity.currentMaxBurnTime = val));
    }

    public BloomeryContainer(final int windowID, final PlayerInventory playerInventory, final PacketBuffer data) {
        this(windowID, playerInventory, getTileEntity(playerInventory, data));
    }

    private static BloomeryTileEntity.Bottom getTileEntity(final PlayerInventory playerInv, final PacketBuffer data) {
        Objects.requireNonNull(playerInv, "playerInv cannot be null");
        Objects.requireNonNull(data, "data cannot be null");
        final TileEntity tileAtPos = playerInv.player.world.getTileEntity(data.readBlockPos());
        if (tileAtPos instanceof BloomeryTileEntity.Bottom) {
            return (BloomeryTileEntity.Bottom) tileAtPos;
        }
        throw new IllegalStateException("TileEntity is not correct " + tileAtPos);
    }

    @Override
    public boolean canInteractWith(@Nonnull PlayerEntity playerIn) {
        return isWithinUsableDistance(canInteractWithCallable, playerIn, BlockRegistryHandler.BLOOMERY_BOTTOM_BLOCK.get());
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
        return this.currentSmeltTime.get() != 0 ? this.currentSmeltTime.get() * 24 / this.tileEntity.maxSmeltTime : 0;
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
