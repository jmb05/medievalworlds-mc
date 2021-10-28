package net.jmb19905.medievalworlds.common.capability.motion;

import net.jmb19905.medievalworlds.MedievalWorlds;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;

@SuppressWarnings("removal")
public class Motion implements IMotion{

    public static final ResourceLocation ID = new ResourceLocation(MedievalWorlds.MOD_ID, "motion");

    @net.minecraftforge.common.capabilities.CapabilityInject(IMotion.class)
    public static final Capability<IMotion> MOTION_CAPABILITY = null;
    public static void register() {
        CapabilityManager.INSTANCE.register(IMotion.class);
    }

    private Vec3 prevPos;
    private Vec3 pos;

    public Motion(){
        this(Vec3.ZERO, Vec3.ZERO);
    }

    public Motion(Vec3 prevPos, Vec3 pos){
        this.prevPos = prevPos;
        this.pos = pos;
    }

    public Vec3 getPrevPos() {
        return prevPos;
    }

    public Vec3 getPos() {
        return pos;
    }

    public void setPrevPos(Vec3 prevPos) {
        this.prevPos = prevPos;
    }

    public void setPos(Vec3 pos) {
        this.pos = pos;
    }

    @Override
    public Vec3 getMoveVec(){
        return pos.subtract(prevPos);
    }

    @Override
    public double getMoveDist() {
        return pos.distanceTo(prevPos);
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        nbt.putDouble(MedievalWorlds.MOD_ID + ".motionPrevX", getPrevPos().x());
        nbt.putDouble(MedievalWorlds.MOD_ID + ".motionPrevY", getPrevPos().x());
        nbt.putDouble(MedievalWorlds.MOD_ID + ".motionPrevZ", getPrevPos().x());
        nbt.putDouble(MedievalWorlds.MOD_ID + ".motionX", getPos().x());
        nbt.putDouble(MedievalWorlds.MOD_ID + ".motionX", getPos().x());
        nbt.putDouble(MedievalWorlds.MOD_ID + ".motionX", getPos().x());
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        double prevX = nbt.getDouble(MedievalWorlds.MOD_ID + "motionPrevX");
        double prevY = nbt.getDouble(MedievalWorlds.MOD_ID + "motionPrevY");
        double prevZ = nbt.getDouble(MedievalWorlds.MOD_ID + "motionPrevZ");
        setPrevPos(new Vec3(prevX, prevY, prevZ));
        double x = nbt.getDouble(MedievalWorlds.MOD_ID + "motionX");
        double y = nbt.getDouble(MedievalWorlds.MOD_ID + "motionY");
        double z = nbt.getDouble(MedievalWorlds.MOD_ID + "motionZ");
        setPos(new Vec3(x, y, z));
    }
}
