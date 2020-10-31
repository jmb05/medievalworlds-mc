package net.jmb19905.medievalworlds.item.test;

import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.Vec3d;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TargetEffectMessageToClient
{
    public TargetEffectMessageToClient(Vec3d i_targetCoordinates)
    {
        targetCoordinates = i_targetCoordinates;
        messageIsValid = true;
    }

    public Vec3d getTargetCoordinates() {
        return targetCoordinates;
    }

    public boolean isMessageValid() {
        return messageIsValid;
    }

    // for use by the message handler only.
    public TargetEffectMessageToClient()
    {
        messageIsValid = false;
    }

    /**
     * Called by the network code once it has received the message bytes over the network.
     * Used to read the ByteBuf contents into your member variables
     * @param buf the PacketBuffer
     */
    public static TargetEffectMessageToClient decode(PacketBuffer buf)
    {
        TargetEffectMessageToClient retval = new TargetEffectMessageToClient();
        try {
            double x = buf.readDouble();
            double y = buf.readDouble();
            double z = buf.readDouble();
            retval.targetCoordinates = new Vec3d(x, y, z);
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
    public void encode(PacketBuffer buf)
    {
        if (!messageIsValid) return;
        buf.writeDouble(targetCoordinates.x);
        buf.writeDouble(targetCoordinates.y);
        buf.writeDouble(targetCoordinates.z);

        //System.out.println("TargetEffectMessageToClient:toBytes length=" + buf.readableBytes());  // debugging only
    }

    @Override
    public String toString()
    {
        return "TargetEffectMessageToClient[targetCoordinates=" + targetCoordinates + "]";
    }

    private Vec3d targetCoordinates;
    private boolean messageIsValid;

    private static final Logger LOGGER = LogManager.getLogger();
}