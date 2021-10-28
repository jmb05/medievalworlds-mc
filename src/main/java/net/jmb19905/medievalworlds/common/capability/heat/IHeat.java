package net.jmb19905.medievalworlds.common.capability.heat;

import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.util.INBTSerializable;

public interface IHeat extends INBTSerializable<CompoundTag> {
    int getHeat();
    void setHeat(int heat);
}
