package net.jmb19905.medievalworlds.common.capability;

import net.jmb19905.medievalworlds.MedievalWorlds;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nullable;

public class Motion {

    private Vector3d prevPos;
    private Vector3d pos;

    public Motion(){
        this(Vector3d.ZERO, Vector3d.ZERO);
    }

    public Motion(Vector3d prevPos, Vector3d pos){
        this.prevPos = prevPos;
        this.pos = pos;
    }

    public Vector3d getPrevPos() {
        return prevPos;
    }

    public Vector3d getPos() {
        return pos;
    }

    public void setPrevPos(Vector3d prevPos) {
        this.prevPos = prevPos;
    }

    public void setPos(Vector3d pos) {
        this.pos = pos;
    }

    public double getMotionDistance(){
        return pos.distanceTo(prevPos);
    }

    public static class MotionNBTStorage implements Capability.IStorage<Motion> {
        @Nullable
        @Override
        public INBT writeNBT(Capability<Motion> capability, Motion instance, Direction side) {
            CompoundNBT nbt = new CompoundNBT();
            nbt.putDouble(MedievalWorlds.MOD_ID + "_prevPos_x", instance.prevPos.x);
            nbt.putDouble(MedievalWorlds.MOD_ID + "_prevPos_y", instance.prevPos.y);
            nbt.putDouble(MedievalWorlds.MOD_ID + "_prevPos_x", instance.prevPos.x);
            nbt.putDouble(MedievalWorlds.MOD_ID + "_pos_x", instance.pos.x);
            nbt.putDouble(MedievalWorlds.MOD_ID + "_pos_y", instance.pos.y);
            nbt.putDouble(MedievalWorlds.MOD_ID + "_pos_x", instance.pos.x);
            return nbt;
        }

        @Override
        public void readNBT(Capability<Motion> capability, Motion instance, Direction side, INBT nbt) {
            double prevX = 0;
            double prevY = 0;
            double prevZ = 0;
            double x = 0;
            double y = 0;
            double z = 0;
            if(nbt.getType() == CompoundNBT.TYPE){
                prevX = ((CompoundNBT) nbt).getDouble(MedievalWorlds.MOD_ID + "_prevPos_x");
                prevY = ((CompoundNBT) nbt).getDouble(MedievalWorlds.MOD_ID + "_prevPos_y");
                prevZ = ((CompoundNBT) nbt).getDouble(MedievalWorlds.MOD_ID + "_prevPos_z");
                x = ((CompoundNBT) nbt).getDouble(MedievalWorlds.MOD_ID + "_pos_x");
                y = ((CompoundNBT) nbt).getDouble(MedievalWorlds.MOD_ID + "_pos_y");
                z = ((CompoundNBT) nbt).getDouble(MedievalWorlds.MOD_ID + "_pos_z");
            }
            instance.setPrevPos(new Vector3d(prevX,prevY,prevZ));
            instance.setPos(new Vector3d(x,y,z));
        }
    }

    public static Motion createDefaultInstance() {
        return new Motion();
    }
}
