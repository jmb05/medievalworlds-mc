package net.jmb19905.medievalworlds.common.inv;

import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class MWItemHandler extends ItemStackHandler {

    private boolean dirty = false;
    private final Map<Integer, Runnable> contentsChanged;

    public MWItemHandler(int size, ItemStack... stacks) {
        super(size);

        for (int index = 0; index < stacks.length; index++) {
            this.stacks.set(index, stacks[index]);
        }

        contentsChanged = new HashMap<>();
    }

    public void clear() {
        for (int index = 0; index < this.getSlots(); index++) {
            this.stacks.set(index, ItemStack.EMPTY);
            this.onContentsChanged(index);
        }
    }

    public boolean isEmpty() {
        for (ItemStack stack : this.stacks) {
            if (stack.isEmpty() || stack.getItem() == Items.AIR) {
                return true;
            }
        }
        return false;
    }

    public void decreaseStackSize(int index, int count) {
        ItemStack stack = getStackInSlot(index);
        stack.shrink(count);
        setStackInSlot(index, stack);
    }

    public void removeStackFromSlot(int index) {
        this.stacks.set(index, ItemStack.EMPTY);
        this.onContentsChanged(index);
    }

    @Override
    protected void onContentsChanged(int slot) {
        super.onContentsChanged(slot);
        dirty = true;
        if(contentsChanged.get(slot) != null) {
            contentsChanged.get(slot).run();
        }
    }

    public void setContentsChangedListener(int slot, Runnable contentsChanged) {
        this.contentsChanged.put(slot, contentsChanged);
    }

    public NonNullList<ItemStack> toNonNullList() {
        NonNullList<ItemStack> items = NonNullList.create();
        items.addAll(this.stacks);
        return items;
    }

    public void setNonNullList(NonNullList<ItemStack> items) {
        if (items.size() != this.getSlots())
            throw new IndexOutOfBoundsException("NonNullList must be same size as ItemStackHandler!");
        for (int index = 0; index < items.size(); index++) {
            this.stacks.set(index, items.get(index));
        }
    }

    @Override
    public String toString() {
        return this.stacks.toString();
    }

    @Override
    public void setStackInSlot(int slot, @NotNull ItemStack stack) {
        super.setStackInSlot(slot, stack);
    }

    public boolean isDirty() {
        return dirty;
    }

    public void markClean() {
        dirty = false;
    }

    public void markDirty() {
        dirty = true;
    }

    public void saveToTag(CompoundTag tag) {
        ContainerHelper.saveAllItems(tag, this.toNonNullList());
    }

    public void loadFromTag(CompoundTag tag) {
        NonNullList<ItemStack> inventoryAsList = NonNullList.withSize(this.getSlots(), ItemStack.EMPTY);
        ContainerHelper.loadAllItems(tag, inventoryAsList);
        setNonNullList(inventoryAsList);
    }

}