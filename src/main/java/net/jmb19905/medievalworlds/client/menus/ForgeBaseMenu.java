package net.jmb19905.medievalworlds.client.menus;

import net.jmb19905.medievalworlds.common.blockentities.forge.ForgeBaseBlockEntity;
import net.jmb19905.medievalworlds.common.registries.MWBlocks;
import net.jmb19905.medievalworlds.common.registries.MWMenuTypes;
import net.jmb19905.medievalworlds.common.registries.MWRecipeSerializers;
import net.jmb19905.medievalworlds.util.slots.FuelInputSlot;
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

public class ForgeBaseMenu extends AbstractContainerMenu {

    private final ForgeBaseBlockEntity blockEntity;
    private final ContainerLevelAccess containerLevelAccess;

    protected ForgeBaseMenu(final int windowId, final Inventory playerInventory, final ForgeBaseBlockEntity entity) {
        super(MWMenuTypes.FORGE.get(), windowId);
        blockEntity = entity;
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

        //Fuel Slot
        this.addSlot(new FuelInputSlot(entity.getInventory(), 0, 79, 42, MWRecipeSerializers.FORGE_TYPE));
    }

    public ForgeBaseMenu(final int windowID, final Inventory playerInventory, final FriendlyByteBuf data) {
        this(windowID, playerInventory, getTileEntity(playerInventory, data));
    }

    private static ForgeBaseBlockEntity getTileEntity(final Inventory playerInv, final FriendlyByteBuf data) {
        Objects.requireNonNull(playerInv, "playerInv cannot be null");
        Objects.requireNonNull(data, "data cannot be null");
        final BlockEntity tileAtPos = playerInv.player.level.getBlockEntity(data.readBlockPos());
        if (tileAtPos instanceof ForgeBaseBlockEntity) {
            return (ForgeBaseBlockEntity) tileAtPos;
        }
        throw new IllegalStateException("TileEntity is not correct " + tileAtPos);
    }

    //I do not understand this :C
    @Nonnull
    @Override
    public ItemStack quickMoveStack(@Nonnull Player player, int slotIndex) {
        ItemStack returnStack = ItemStack.EMPTY;
        final Slot slot = this.slots.get(slotIndex);
        if (slot.hasItem()) {
            final ItemStack slotStack = slot.getItem();
            returnStack = slotStack.copy();

            final int containerSlotCount = this.slots.size() - player.getInventory().items.size();
            if (slotIndex < containerSlotCount) {//if the slot is in the container
                if (!moveItemStackTo(slotStack, containerSlotCount, this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!moveItemStackTo(slotStack, 0, containerSlotCount, false)) {
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
    public int getBurnProgressionScaled(){
        return (this.blockEntity.currentBurnTime == 0 || this.blockEntity.currentMaxBurnTime == 0) ? 0 : (this.blockEntity.currentBurnTime * 13 / this.blockEntity.currentMaxBurnTime);
    }

    @OnlyIn(Dist.CLIENT)
    public int getCurrentMaxBurnTime(){
        return this.blockEntity.currentMaxBurnTime;
    }

    @Override
    public boolean stillValid(@Nonnull Player player) {
        return stillValid(containerLevelAccess, player, MWBlocks.FORGE_BASE_BLOCK.get());
    }
}
