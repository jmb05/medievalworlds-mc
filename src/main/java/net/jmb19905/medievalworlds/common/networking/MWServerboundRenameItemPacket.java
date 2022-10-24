package net.jmb19905.medievalworlds.common.networking;

import net.minecraft.network.FriendlyByteBuf;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MWServerboundRenameItemPacket {

    private static final Logger LOGGER = LogManager.getLogger();

    private String name;
    private boolean valid;

    public MWServerboundRenameItemPacket(String name) {
        this.name = name;
        this.valid = true;
    }

    private MWServerboundRenameItemPacket() {
        this.valid = false;
    }

    public String getName() {
        return name;
    }

    public static MWServerboundRenameItemPacket decode(FriendlyByteBuf buffer) {
        MWServerboundRenameItemPacket packet = new MWServerboundRenameItemPacket();
        try {
            packet.name = buffer.readUtf();
        } catch (IllegalArgumentException | IndexOutOfBoundsException e) {
            LOGGER.warn("Exception while reading MWServerboundRenameItemPacket: " + e);
            return packet;
        }
        packet.valid = true;
        return packet;
    }

    public void encode(FriendlyByteBuf buffer) {
        if(!valid) return;
        buffer.writeUtf(name);
    }

    public boolean isValid() {
        return valid;
    }
}