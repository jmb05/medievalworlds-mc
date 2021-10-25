package net.jmb19905.medievalworlds.client.menus;

import net.jmb19905.medievalworlds.common.registries.BlockRegistryHandler;
import net.jmb19905.medievalworlds.common.registries.MenuTypeRegistryHandler;
import net.jmb19905.medievalworlds.common.registries.RecipeSerializerRegistryHandler;
import net.jmb19905.medievalworlds.common.blockentities.BloomeryBlockEntity;
import net.jmb19905.medievalworlds.util.slots.BloomInputSlot;
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

public class BloomeryMenu extends AbstractContainerMenu {

    private final BloomeryBlockEntity.Bottom tileEntity;
    private final ContainerLevelAccess containerLevelAccess;
    public int currentSmeltTime;
    public int currentBurnTime;
    public int currentMaxBurnTime;

    public BloomeryMenu(final int windowID, final Inventory playerInventory, final BloomeryBlockEntity.Bottom tile) {
        super(MenuTypeRegistryHandler.BLOOMERY.get(), windowID);
        this.tileEntity = tile;
        this.containerLevelAccess = ContainerLevelAccess.create(Objects.requireNonNull(tile.getLevel()), tile.getBlockPos());

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
        this.addSlot(new FuelInputSlot(tile.getInventory(), 1, 79, 51, RecipeSerializerRegistryHandler.BLOOM_TYPE));
    }

    public BloomeryMenu(final int windowID, final Inventory playerInventory, final FriendlyByteBuf data) {
        this(windowID, playerInventory, getTileEntity(playerInventory, data));
    }

    private static BloomeryBlockEntity.Bottom getTileEntity(final Inventory playerInv, final FriendlyByteBuf data) {
        Objects.requireNonNull(playerInv, "playerInv cannot be null");
        Objects.requireNonNull(data, "data cannot be null");
        final BlockEntity blockEntityAtPos = playerInv.player.level.getBlockEntity(data.readBlockPos());
        if (blockEntityAtPos instanceof BloomeryBlockEntity.Bottom) {
            return (BloomeryBlockEntity.Bottom) blockEntityAtPos;
        }
        throw new IllegalStateException("TileEntity is not correct " + blockEntityAtPos);
    }

    @Override
    public boolean stillValid(@Nonnull Player player) {
        return stillValid(containerLevelAccess, player, BlockRegistryHandler.BLOOMERY_BOTTOM_BLOCK.get());
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
        return this.currentSmeltTime != 0 ? this.currentSmeltTime * 24 / this.tileEntity.maxSmeltTime : 0;
    }

    @OnlyIn(Dist.CLIENT)
    public int getBurnProgressionScaled(){
        return this.currentBurnTime != 0 && this.currentMaxBurnTime != 0 ? this.currentBurnTime * 13 / this.tileEntity.currentMaxBurnTime : 0;
    }

    @OnlyIn(Dist.CLIENT)
    public int getCurrentMaxBurnTime(){
        return this.currentMaxBurnTime;
    }
}
