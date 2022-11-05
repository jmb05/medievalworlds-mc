package net.jmb19905.medievalworlds.common.capability;

import net.jmb19905.medievalworlds.MedievalWorlds;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Hood implements INBTSerializable<CompoundTag> {

    public static final ResourceLocation ID = new ResourceLocation(MedievalWorlds.MOD_ID, "hood");

    public static final Capability<Hood> HOOD_CAPABILITY = CapabilityManager.get(new CapabilityToken<>() {});

    private boolean hoodDown;

    public void setHoodDown(boolean hoodDown) {
        this.hoodDown = hoodDown;
    }

    public boolean isHoodDown() {
        return hoodDown;
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        tag.putBoolean(MedievalWorlds.MOD_ID + ".hoodUp", hoodDown);
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        hoodDown = nbt.getBoolean(MedievalWorlds.MOD_ID + ".hoodUp");
    }

    public static class CapProvider implements ICapabilitySerializable<CompoundTag> {

        private final Capability<Hood> capability = Hood.HOOD_CAPABILITY;
        private final Hood instance = new Hood();
        private final LazyOptional<Hood> implementation = LazyOptional.of(() -> instance);

        @NotNull
        @Override
        public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
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