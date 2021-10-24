package net.jmb19905.medievalworlds.common.capability;

import net.jmb19905.medievalworlds.MedievalWorlds;
import net.jmb19905.medievalworlds.core.NBTTypesMW;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Objects;

public class CapabilityProviderEntities implements ICapabilitySerializable<INBT> {

    private static final Logger LOGGER = LogManager.getLogger();

    private final Motion motion = new Motion();

    @SuppressWarnings("unchecked")
    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if(MotionCapability.CAPABILITY_MOTION == cap){
            return (LazyOptional<T>) LazyOptional.of(() -> motion);
        }
        return LazyOptional.empty();
    }

    @Override
    public INBT serializeNBT() {
        CompoundNBT nbt = new CompoundNBT();
        nbt.put(MedievalWorlds.MOD_ID + "_motion_cap", Objects.requireNonNull(MotionCapability.CAPABILITY_MOTION.writeNBT(motion, null)));
        return nbt;
    }

    @Override
    public void deserializeNBT(INBT nbt) {
        if(nbt.getId() != NBTTypesMW.COMPOUND_NBT_ID){
            LOGGER.warn("Unexpected NBT type: " + nbt);
            return;
        }
        CompoundNBT compoundNBT = (CompoundNBT) nbt;
        CompoundNBT motionNBT = compoundNBT.getCompound(MedievalWorlds.MOD_ID + "_motion_cap");

        MotionCapability.CAPABILITY_MOTION.readNBT(motion, null, motionNBT);
    }
}
