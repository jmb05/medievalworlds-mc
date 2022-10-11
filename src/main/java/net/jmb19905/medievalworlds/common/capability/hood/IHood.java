package net.jmb19905.medievalworlds.common.capability.hood;

import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.util.INBTSerializable;

public interface IHood extends INBTSerializable<CompoundTag> {

    boolean isHoodDown();

}