package net.jmb19905.medievalworlds.common.networking;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.phys.Vec3;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CritEffectPacket {

    public CritEffectPacket(Vec3 i_targetCoordinates, double xSpread, double ySpread, double zSpread) {
        targetCoordinates = i_targetCoordinates;
        this.xSpread = xSpread;
        this.ySpread = ySpread;
        this.zSpread = zSpread;
        messageIsValid = true;
    }

    public Vec3 getTargetCoordinates() {
        return targetCoordinates;
    }

    public double getXSpread() {
        return xSpread;
    }

    public double getYSpread() {
        return ySpread;
    }

    public double getZSpread() {
        return zSpread;
    }

    public boolean isMessageValid() {
        return messageIsValid;
    }

    // for use by the message handler only.
    public CritEffectPacket()
    {
        messageIsValid = false;
    }

    /**
     * Called by the network code once it has received the message bytes over the network.
     * Used to read the ByteBuf contents into your member variables
     * @param buf the PacketBuffer
     */
    public static CritEffectPacket decode(FriendlyByteBuf buf) {
        CritEffectPacket retval = new CritEffectPacket();
        try {
            double x = buf.readDouble();
            double y = buf.readDouble();
            double z = buf.readDouble();
            retval.targetCoordinates = new Vec3(x, y, z);
            retval.xSpread = buf.readDouble();
            retval.ySpread = buf.readDouble();
            retval.zSpread = buf.readDouble();
        } catch (IllegalArgumentException | IndexOutOfBoundsException e) {
            LOGGER.warn("Exception while reading TargetEffectMessageToClient: " + e);
            return retval;
        }
        retval.messageIsValid = true;
        return retval;
    }

    /**
     * Called by the network code.
     * Used to write the contents of your message member variables into the ByteBuf, ready for transmission over the network.
     * @param buf the PacketBuffer
     */
    public void encode(FriendlyByteBuf buf) {
        if (!messageIsValid) return;
        buf.writeDouble(targetCoordinates.x);
        buf.writeDouble(targetCoordinates.y);
        buf.writeDouble(targetCoordinates.z);

        buf.writeDouble(xSpread);
        buf.writeDouble(ySpread);
        buf.writeDouble(zSpread);
    }

    @Override
    public String toString()
    {
        return "TargetEffectMessageToClient[targetCoordinates=" + targetCoordinates + "]";
    }

    private Vec3 targetCoordinates;
    private double xSpread;
    private double ySpread;
    private double zSpread;
    private boolean messageIsValid;

    private static final Logger LOGGER = LogManager.getLogger();
}