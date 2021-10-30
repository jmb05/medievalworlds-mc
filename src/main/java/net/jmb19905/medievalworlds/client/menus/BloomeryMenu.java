package net.jmb19905.medievalworlds.client.menus;

import net.jmb19905.medievalworlds.common.blockentities.bloomery.BloomeryBottomBlockEntity;
import net.jmb19905.medievalworlds.core.registries.MWBlocks;
import net.jmb19905.medievalworlds.core.registries.MWMenuTypes;
import net.jmb19905.medievalworlds.core.registries.MWRecipeSerializers;
import net.jmb19905.medievalworlds.util.slots.FuelInputSlot;
import net.jmb19905.medievalworlds.util.slots.InputSlot;
import net.jmb19905.medievalworlds.util.slots.OutputSlot;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;
import java.util.Objects;

public class BloomeryMenu extends AbstractContainerMenu {

    private final BloomeryBottomBlockEntity tileEntity;
    private final ContainerLevelAccess containerLevelAccess;

    public BloomeryMenu(final int windowID, final Inventory playerInventory, final BloomeryBottomBlockEntity entity) {
        super(MWMenuTypes.BLOOMERY.get(), windowID);
        this.tileEntity = entity;
        this.containerLevelAccess = ContainerLevelAccess.create(Objects.requireNonNull(entity.getLevel()), entity.getBlockPos());

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
        this.addSlot(new InputSlot(entity.getInventory(), 0, 43, 17, MWRecipeSerializers.BLOOM_TYPE));
        this.addSlot(new FuelInputSlot(entity.getInventory(), 1, 43, 51, MWRecipeSerializers.BLOOM_TYPE));
        this.addSlot(new OutputSlot(entity.getInventory(), 2, 116, 21));
        this.addSlot(new OutputSlot(entity.getInventory(), 3, 116, 47));
    }

    public BloomeryMenu(final int windowID, final Inventory playerInventory, final FriendlyByteBuf data) {
        this(windowID, playerInventory, getTileEntity(playerInventory, data));
    }

    private static BloomeryBottomBlockEntity getTileEntity(final Inventory playerInv, final FriendlyByteBuf data) {
        Objects.requireNonNull(playerInv, "playerInv cannot be null");
        Objects.requireNonNull(data, "data cannot be null");
        final BlockEntity blockEntityAtPos = playerInv.player.level.getBlockEntity(data.readBlockPos());
        if (blockEntityAtPos instanceof BloomeryBottomBlockEntity) {
            return (BloomeryBottomBlockEntity) blockEntityAtPos;
        }
        throw new IllegalStateException("TileEntity is not correct " + blockEntityAtPos);
    }

    @Override
    public boolean stillValid(@Nonnull Player player) {
        return stillValid(containerLevelAccess, player, MWBlocks.BLOOMERY_CLAY_BOTTOM_BLOCK.get());
    }

    @Nonnull
    @Override
    public ItemStack quickMoveStack(@Nonnull Player player, int index) {
        ItemStack returnStack = ItemStack.EMPTY;
        final Slot slot = this.slots.get(index);
        if (slot.hasItem()) {
            final ItemStack slotStack = slot.getItem();
            returnStack = slotStack.copy();

            final int containerSlots = this.slots.size() - player.getInventory().items.size();
            if (index < containerSlots) {
                if (!moveItemStackTo(slotStack, containerSlots, this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!moveItemStackTo(slotStack, 0, containerSlots, false)) {
                return ItemStack.EMPTY;
            }
            if (slotStack.getCount() == 0) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
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
        if(this.tileEntity.currentMaxBurnTime == 0 || this.tileEntity.currentBurnTime == 0) return 0;
        return this.tileEntity.currentBurnTime * 24 / this.tileEntity.currentMaxBurnTime;
    }

    @OnlyIn(Dist.CLIENT)
    public int getBurnProgressionScaled(){
        if(this.tileEntity.currentMaxBurnTime == 0 || this.tileEntity.currentBurnTime == 0) return 0;
        return this.tileEntity.currentBurnTime * 13 / this.tileEntity.currentMaxBurnTime;
    }

    @OnlyIn(Dist.CLIENT)
    public int getCurrentMaxBurnTime(){
        return this.tileEntity.currentMaxBurnTime;
    }
}
