package net.jmb19905.medievalworlds.common.capability.motion;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.util.INBTSerializable;

public interface IMotion extends INBTSerializable<CompoundTag> {
    Vec3 getMoveVec();
    double getMoveDist();
}
