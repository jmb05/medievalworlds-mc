package net.jmb19905.medievalworlds.common.capability;

import net.jmb19905.medievalworlds.common.inv.MWItemHandler;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.function.Predicate;

public class QuiverInv extends MWItemHandler {

    public static final Capability<IItemHandler> QUIVER_INV_CAPABILITY = CapabilityManager.get(new CapabilityToken<>() {});
    private final int size;

    public QuiverInv(int size) {
        super(size);
        this.size = size;
    }

    public float getFillLevel() {
        float combinedWeights = 0;
        for (int i=0;i<size;i++) {
            ItemStack stack = getStackInSlot(i);
            int count = stack.getCount();
            float weight = ((float) count) / ((float) stack.getMaxStackSize());
            if (stack.isEmpty()) weight = 0;
            combinedWeights += weight;
        }
        return combinedWeights / (float) size;
    }

    public Optional<ItemStack> removeOne() {
        for (int i=size-1;i>=0;i--) {
            ItemStack stack = getStackInSlot(i);
            if (!stack.isEmpty()) {
                Optional<ItemStack> optional = Optional.of(stack.copy());
                removeStackFromSlot(i);
                return optional;
            }
        }
        return Optional.empty();
    }

    public int getFreeSpace(ItemStack checkStack) {
        if (checkStack.isEmpty()) return 0;
        int combinedFreeSpace = 0;
        for (int i=0;i<size;i++) {
            combinedFreeSpace += getFreeSpaceForSlot(checkStack, i);
        }
        return combinedFreeSpace;
    }

    public int getTotalPossible() {
        int combined = 0;
        for (int i=0;i<size;i++) {
            ItemStack stack = getStackInSlot(i);
            if (stack.isEmpty()) continue;
            int weight = stack.getMaxStackSize();
            combined += weight;
        }
        return combined;
    }

    private int getFreeSpaceForSlot(ItemStack checkStack, int slot) {
        if (slot < this.size) {
            ItemStack stackInSlot = getStackInSlot(slot);
            if (stackInSlot.isEmpty()) {
                return checkStack.getMaxStackSize();
            } else if (ItemStack.isSameItemSameTags(stackInSlot, checkStack) && stackInSlot.getCount() < stackInSlot.getMaxStackSize()) {
                return  (stackInSlot.getMaxStackSize() - stackInSlot.getCount());
            }
        }
        return 0;
    }

    private boolean isViableSlots(ItemStack checkStack, int slot) {
        return getFreeSpaceForSlot(checkStack, slot) > 0;
    }

    public int add(ItemStack arrowStack) {
        if (!arrowStack.isEmpty() && arrowStack.getItem().canFitInsideContainerItems() && arrowStack.getItem() instanceof ArrowItem) {
            int addCount = Math.min(arrowStack.getCount(), getFreeSpace(arrowStack));
            if (addCount > 0) {
                int alreadyAdded = 0;
                for (int i=0;i<this.size;i++) {
                    ItemStack stackInSlot = getStackInSlot(i);
                    int freeSpaceInSlot = getFreeSpaceForSlot(arrowStack, i);
                    if (stackInSlot.isEmpty()) {
                        ItemStack arrowStackCopy = arrowStack.copy();
                        arrowStackCopy.setCount(addCount - alreadyAdded);
                        insertItem(i, arrowStackCopy, false);
                        alreadyAdded += addCount - alreadyAdded;
                    } else if (freeSpaceInSlot > 0) {
                        int slotAddCount = Math.min(freeSpaceInSlot, addCount - alreadyAdded);
                        stackInSlot.grow(slotAddCount);
                        alreadyAdded += slotAddCount;
                    }
                    if (alreadyAdded == addCount) {
                        break;
                    }
                }
            }
            return addCount;
        }
        return 0;
    }

    public boolean dropAll(Player player) {
        boolean b = false;
        for(int i=0;i<size;i++) {
            ItemStack stack = getStackInSlot(i);
            if (!stack.isEmpty()) b = true;
            player.drop(stack, true);
        }
        clear();
        return b;
    }

    public ItemStack getProjectile(Predicate<ItemStack> condition) {
        NonNullList<ItemStack> items = toNonNullList();
        return items
                .stream()
                .filter(condition)
                .findFirst().orElse(ItemStack.EMPTY);
    }

    public static class Provider implements ICapabilitySerializable<CompoundTag> {

        private final Capability<IItemHandler> capability = QuiverInv.QUIVER_INV_CAPABILITY;
        private final QuiverInv instance;
        private final LazyOptional<IItemHandler> implementation;

        public Provider(int size) {
            instance = new QuiverInv(size);
            implementation = LazyOptional.of(() -> instance);
        }

        @Override
        public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
            if(cap == capability) return implementation.cast();
            return LazyOptional.empty();
        }

        @Override
        public CompoundTag serializeNBT() {
            return instance.serializeNBT();
        }

        @Override
        public void deserializeNBT(CompoundTag nbt) {
            instance.deserializeNBT(nbt);
        }
    }

}