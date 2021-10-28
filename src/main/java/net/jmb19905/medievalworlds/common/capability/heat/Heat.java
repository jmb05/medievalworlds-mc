package net.jmb19905.medievalworlds.common.capability.heat;

import net.jmb19905.medievalworlds.MedievalWorlds;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;

@SuppressWarnings("removal")
public class Heat implements IHeat{

    public static final ResourceLocation ID = new ResourceLocation(MedievalWorlds.MOD_ID, "heat");

    @net.minecraftforge.common.capabilities.CapabilityInject(IHeat.class)
    public static final Capability<IHeat> HEAT_CAPABILITY = null;
    public static void register() {
        CapabilityManager.INSTANCE.register(IHeat.class);
    }

    private int heat;

    public Heat(int heat) {
        this.heat = heat;
    }

    public Heat() {
        this(-1);
    }

    @Override
    public int getHeat() {
        return heat;
    }

    @Override
    public void setHeat(int heat) {
        this.heat = heat;
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        nbt.putInt(MedievalWorlds.MOD_ID + ".heat", heat);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        setHeat(nbt.getInt(MedievalWorlds.MOD_ID + ".heat"));
    }
}
