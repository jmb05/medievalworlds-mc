package net.jmb19905.medievalworlds.client.networking;

import net.minecraft.network.FriendlyByteBuf;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.UUID;

public abstract class HoodChangePacket {

    private static final Logger LOGGER = LogManager.getLogger();

    protected boolean hoodDown;
    protected UUID playerId;
    protected boolean valid;

    public HoodChangePacket(boolean hoodDown, UUID playerId) {
        this.hoodDown = hoodDown;
        this.playerId = playerId;
        this.valid = true;
    }

    private HoodChangePacket() {
        this.valid = false;
    }

    public boolean isHoodDown() {
        return hoodDown;
    }

    public UUID getPlayerId() {
        return playerId;
    }

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public boolean isValid() {
        return valid;
    }

    public void encode(FriendlyByteBuf buf) {
        if(!valid) return;
        buf.writeBoolean(this.hoodDown);
        buf.writeUUID(this.playerId);
    }

    protected static <T extends HoodChangePacket> T readFromBuffer(T packet, FriendlyByteBuf buffer) {
        try {
            packet.hoodDown = buffer.readBoolean();
            packet.playerId = buffer.readUUID();
        } catch (IllegalArgumentException | IndexOutOfBoundsException e) {
            LOGGER.warn("Exception while reading HoodChangePacket: " + e);
            return packet;
        }
        packet.valid = true;
        return packet;
    }

    public static class ToServer extends HoodChangePacket {
        public ToServer(boolean hoodDown, UUID playerId) {
            super(hoodDown, playerId);
        }

        public ToServer() {}

        public static ToServer decode(FriendlyByteBuf buf) {
            return readFromBuffer(new ToServer(), buf);
        }
    }

    public static class ToClient extends HoodChangePacket {
        public ToClient(boolean hoodDown, UUID playerId) {
            super(hoodDown, playerId);
        }

        public ToClient() {}

        public static ToClient decode(FriendlyByteBuf buf) {
            return readFromBuffer(new ToClient(), buf);
        }
    }

}