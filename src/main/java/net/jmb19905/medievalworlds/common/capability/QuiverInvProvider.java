package net.jmb19905.medievalworlds.common.capability;

import net.jmb19905.medievalworlds.common.capability.quiverInv.QuiverInv;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class QuiverInvProvider implements ICapabilitySerializable<CompoundTag> {

    private final Capability<IItemHandler> capability = QuiverInv.QUIVER_INV_CAPABILITY;
    private final QuiverInv instance;
    private final LazyOptional<IItemHandler> implementation;

    public QuiverInvProvider(int size) {
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
