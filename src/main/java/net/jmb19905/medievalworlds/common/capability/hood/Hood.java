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

    private boolean hoodDown;

    public void setHoodDown(boolean hoodDown) {
        this.hoodDown = hoodDown;
    }

    @Override
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
}