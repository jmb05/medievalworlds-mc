package net.jmb19905.medievalworlds.common.capability.hood;

import net.jmb19905.medievalworlds.MedievalWorlds;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;

public class Hood implements IHood{

    public static final ResourceLocation ID = new ResourceLocation(MedievalWorlds.MOD_ID, "hood");

    public static final Capability<IHood> HOOD_CAPABILITY = CapabilityManager.get(new CapabilityToken<>() {});

    private boolean hoodUp;

    public void setHoodUp(boolean hoodUp) {
        this.hoodUp = hoodUp;
    }

    @Override
    public boolean getHoodUp() {
        return hoodUp;
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        tag.putBoolean(MedievalWorlds.MOD_ID + ".hoodUp", hoodUp);
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        hoodUp = nbt.getBoolean(MedievalWorlds.MOD_ID + ".hoodUp");
    }
}
