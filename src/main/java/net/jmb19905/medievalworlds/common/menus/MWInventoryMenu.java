package net.jmb19905.medievalworlds.common.menus;

import net.jmb19905.medievalworlds.common.blockentities.abstr.IMWInventoryHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.function.Predicate;

public abstract class MWInventoryMenu<T extends BlockEntity & IMWInventoryHandler> extends AbstractContainerMenu {

    protected final Level level;
    protected final ContainerLevelAccess access;
    protected final Player player;
    protected final T blockEntity;
    protected final int inputSlotCount;
    protected final int outputSlotCount;
    protected final int invStart;
    protected final int hotbarStart;
    protected final int hotbarEnd;

    public MWInventoryMenu(@Nullable MenuType<?> type, int windowId, Inventory inventory, T blockEntity) {
        super(type, windowId);
        this.level = inventory.player.level;
        this.player = inventory.player;
        this.access = ContainerLevelAccess.create(Objects.requireNonNull(blockEntity.getLevel()), blockEntity.getBlockPos());
        this.blockEntity = blockEntity;

        this.inputSlotCount = addInputSlots();
        this.outputSlotCount = addOutputSlots();
        this.invStart = this.inputSlotCount + this.outputSlotCount;
        this.hotbarStart = this.invStart + 27;
        this.hotbarEnd = this.hotbarStart + 9;

        for (Slot slot : slots) {
            blockEntity.getInventory().setContentsChangedListener(slot.getSlotIndex(), () -> slotsChanged(slot.getSlotIndex()));
        }

        //Player Inventory
        for(int i = 0; i < 3; ++i) {
            for(int j = 0; j < 9; ++j) {
                this.addSlot(new Slot(inventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }

        //Hotbar
        for(int k = 0; k < 9; ++k) {
            this.addSlot(new Slot(inventory, k, 8 + k * 18, 142));
        }
    }

    protected void slotsChanged(int index){}

    protected abstract int addInputSlots();

    protected abstract int addOutputSlots();

    public abstract Predicate<BlockState> isValidBlockState();

    protected boolean checkPlayerDistance(@NotNull Player player, BlockPos pos) {
        return player.distanceToSqr((double) pos.getX() + 0.5D, (double) pos.getY() + 0.5D, (double) pos.getZ() + 0.5D) <= 64.0D;
    }

    @Override
    public boolean stillValid(@NotNull Player player) {
        return access.evaluate((level, pos) -> isValidBlockState().test(level.getBlockState(pos)) && checkPlayerDistance(player, pos), true);
    }

    protected static BlockEntity getBlockEntity(final Inventory playerInv, final FriendlyByteBuf data) {
        Objects.requireNonNull(playerInv, "playerInv cannot be null");
        Objects.requireNonNull(data, "data cannot be null");
        return playerInv.player.level.getBlockEntity(data.readBlockPos());
    }

    public @NotNull ItemStack quickMoveStack(@NotNull Player player, int index) {
        Slot slot = this.slots.get(index);
        if (!slot.hasItem()) return ItemStack.EMPTY;
        ItemStack slotStack = slot.getItem();
        ItemStack copyStack = slotStack.copy();

        if (index >= this.invStart) {
            if(!moveItemStackTo(slotStack, 0, this.inputSlotCount, false)) {
                return ItemStack.EMPTY;
            }
        } else {
            if(!moveItemStackTo(slotStack, this.invStart, this.hotbarEnd, false)) {
                return ItemStack.EMPTY;
            }
        }

        if (slotStack.getCount() == 0) {
            slot.set(ItemStack.EMPTY);
        } else {
            slot.setChanged();
        }
        slot.onTake(player, slotStack);
        return copyStack;
    }

}
