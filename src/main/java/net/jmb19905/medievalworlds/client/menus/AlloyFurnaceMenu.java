package net.jmb19905.medievalworlds.client.menus;

import net.jmb19905.medievalworlds.common.registries.BlockRegistryHandler;
import net.jmb19905.medievalworlds.common.registries.MenuTypeRegistryHandler;
import net.jmb19905.medievalworlds.common.registries.RecipeSerializerRegistryHandler;
import net.jmb19905.medievalworlds.common.blockentities.AlloyFurnaceBlockEntity;
import net.jmb19905.medievalworlds.util.slots.AlloyInputSlot;
import net.jmb19905.medievalworlds.util.slots.FuelInputSlot;
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

public class AlloyFurnaceMenu extends AbstractContainerMenu {

    private final AlloyFurnaceBlockEntity tileEntity;
    private final ContainerLevelAccess containerLevelAccess;
    public int currentAlloyTime;
    public int currentBurnTime;
    public int currentMaxBurnTime;

    public AlloyFurnaceMenu(final int windowID, final Inventory playerInventory, final AlloyFurnaceBlockEntity tile) {
        super(MenuTypeRegistryHandler.ALLOY_FURNACE.get(), windowID);
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
        this.addSlot(new AlloyInputSlot(tile.getInventory(), 0, 20, 16, tile.getLevel()));
        this.addSlot(new AlloyInputSlot(tile.getInventory(), 1, 56, 16, tile.getLevel()));
        this.addSlot(new FuelInputSlot(tile.getInventory(), 2, 38, 51, RecipeSerializerRegistryHandler.ALLOY_TYPE));

        //Output
        this.addSlot(new OutputSlot(tile.getInventory(), 3, 116, 35));
    }

    public AlloyFurnaceMenu(final int windowID, final Inventory playerInventory, final FriendlyByteBuf data) {
        this(windowID, playerInventory, getTileEntity(playerInventory, data));
    }

    private static AlloyFurnaceBlockEntity getTileEntity(final Inventory playerInv, final FriendlyByteBuf data) {
        Objects.requireNonNull(playerInv, "playerInv cannot be null");
        Objects.requireNonNull(data, "data cannot be null");
        final BlockEntity tileAtPos = playerInv.player.level.getBlockEntity(data.readBlockPos());
        if (tileAtPos instanceof AlloyFurnaceBlockEntity) {
            return (AlloyFurnaceBlockEntity) tileAtPos;
        }
        throw new IllegalStateException("TileEntity is not correct " + tileAtPos);
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
        return this.currentAlloyTime != 0 ? this.currentAlloyTime * 24 / this.tileEntity.maxAlloyTime : 0;
    }

    @OnlyIn(Dist.CLIENT)
    public int getBurnProgressionScaled(){
        return this.currentBurnTime != 0 && this.currentMaxBurnTime != 0 ? this.currentBurnTime * 13 / this.tileEntity.currentMaxBurnTime : 0;
    }

    @OnlyIn(Dist.CLIENT)
    public int getCurrentMaxBurnTime(){
        return this.currentMaxBurnTime;
    }

    @Override
    public boolean stillValid(@Nonnull Player player) {
        return stillValid(containerLevelAccess, player, BlockRegistryHandler.ALLOY_FURNACE_BLOCK.get());
    }
}
