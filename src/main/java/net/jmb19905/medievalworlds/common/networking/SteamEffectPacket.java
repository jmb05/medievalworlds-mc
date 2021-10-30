package net.jmb19905.medievalworlds.common.networking;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SteamEffectPacket {

    private static final Logger LOGGER = LogManager.getLogger();

    private BlockPos targetCoords;
    private float spread;
    private boolean valid;

    public SteamEffectPacket(BlockPos targetCoords, float spread){
        this.targetCoords = targetCoords;
        this.spread = spread;
        this.valid = true;
    }

    private SteamEffectPacket() {
        this.valid = false;
    }

    public BlockPos getTargetCoords() {
        return targetCoords;
    }

    public float getSpread() {
        return spread;
    }

    public static SteamEffectPacket decode(FriendlyByteBuf buffer) {
        SteamEffectPacket packet = new SteamEffectPacket();
        try {
            double x = buffer.readDouble();
            double y = buffer.readDouble();
            double z = buffer.readDouble();
            packet.targetCoords = new BlockPos(x,y,z);
            packet.spread = buffer.readFloat();
        } catch (IllegalArgumentException | IndexOutOfBoundsException e) {
            LOGGER.warn("Exception while reading SteamEffectPacket: " + e);
            return packet;
        }
        packet.valid = true;
        return packet;
    }

    public void encode(FriendlyByteBuf buffer) {
        if(!valid) return;
        buffer.writeDouble(targetCoords.getX());
        buffer.writeDouble(targetCoords.getY());
        buffer.writeDouble(targetCoords.getZ());
        buffer.writeFloat(spread);
    }

    public boolean isValid() {
        return valid;
    }
}
