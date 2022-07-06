package net.jmb19905.medievalworlds.common.networking;

import net.minecraft.network.FriendlyByteBuf;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DrawSwordPacket {

    private static final Logger LOGGER = LogManager.getLogger();
    private boolean messageValid;
    private int curioSlot;

    public DrawSwordPacket(int curioSlot) {
        this.curioSlot = curioSlot;
        this.messageValid = true;
    }

    public DrawSwordPacket() {
        this.messageValid = false;
    }

    public int getCurioSlot() {
        return curioSlot;
    }

    public boolean isMessageValid() {
        return messageValid;
    }

    public static DrawSwordPacket decode(FriendlyByteBuf buffer) {
        DrawSwordPacket packet = new DrawSwordPacket();
        try {
            packet.curioSlot = buffer.readInt();
        } catch (IllegalArgumentException | IndexOutOfBoundsException e) {
            LOGGER.warn("Exception while reading DrawSwordPacket: " + e);
            return packet;
        }
        packet.messageValid = true;
        return packet;
    }

    public void encode(FriendlyByteBuf buffer) {
        if(!messageValid) return;
        buffer.writeInt(curioSlot);
    }

    @Override
    public String toString() {
        return "DrawSwordPacket{" +
                "messageValid=" + messageValid +
                ", curioSlot=" + curioSlot +
                '}';
    }
}
