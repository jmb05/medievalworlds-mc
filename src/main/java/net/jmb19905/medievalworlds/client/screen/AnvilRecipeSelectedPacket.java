package net.jmb19905.medievalworlds.client.screen;

import net.minecraft.network.FriendlyByteBuf;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AnvilRecipeSelectedPacket {

    private static final Logger LOGGER = LogManager.getLogger();

    private int containerId;
    private int recipeIndex;
    private boolean valid;

    public AnvilRecipeSelectedPacket(int containerId, int recipeIndex) {
        this.containerId = containerId;
        this.recipeIndex = recipeIndex;
        this.valid = true;
    }

    private AnvilRecipeSelectedPacket() {
        this.valid = false;
    }

    public int getContainerId() {
        return containerId;
    }

    public int getRecipeIndex() {
        return recipeIndex;
    }

    public static AnvilRecipeSelectedPacket decode(FriendlyByteBuf buffer) {
        AnvilRecipeSelectedPacket packet = new AnvilRecipeSelectedPacket();
        try {
            packet.containerId = buffer.readInt();
            packet.recipeIndex = buffer.readInt();
        } catch (IllegalArgumentException | IndexOutOfBoundsException e) {
            LOGGER.warn("Exception while reading SteamEffectPacket: " + e);
            return packet;
        }
        packet.valid = true;
        return packet;
    }

    public void encode(FriendlyByteBuf buffer) {
        if(!valid) return;
        buffer.writeInt(containerId);
        buffer.writeInt(recipeIndex);
    }

    public boolean isValid() {
        return valid;
    }

}
