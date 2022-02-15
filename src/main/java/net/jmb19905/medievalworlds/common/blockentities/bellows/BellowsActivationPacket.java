package net.jmb19905.medievalworlds.common.blockentities.bellows;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BellowsActivationPacket {

    private static final Logger LOGGER = LogManager.getLogger();

    private BlockPos pos;
    private boolean activated;
    private boolean valid;

    public BellowsActivationPacket(BlockPos pos, boolean activated) {
        this.pos = pos;
        this.activated = activated;
        this.valid = true;
    }

    private BellowsActivationPacket() {
        this.valid = false;
    }

    public BlockPos getPos() {
        return pos;
    }

    public boolean isActivated() {
        return activated;
    }

    public static BellowsActivationPacket decode(FriendlyByteBuf buffer) {
        BellowsActivationPacket packet = new BellowsActivationPacket();
        try {
            packet.activated = buffer.readBoolean();
            packet.pos = buffer.readBlockPos();
        } catch (IllegalArgumentException | IndexOutOfBoundsException e) {
            LOGGER.warn("Exception while reading BellowsActivationPacket: " + e);
            return packet;
        }
        packet.valid = true;
        return packet;
    }

    public void encode(FriendlyByteBuf buffer) {
        if(!valid) return;
        buffer.writeBlockPos(pos);
        buffer.writeBoolean(activated);
    }

    public boolean isValid() {
        return valid;
    }
}
