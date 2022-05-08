package net.jmb19905.medievalworlds.client.screen;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AnvilRecipeSelectedPacket {

    private static final Logger LOGGER = LogManager.getLogger();

    private int containerId;
    private ResourceLocation recipeId;
    private boolean valid;

    public AnvilRecipeSelectedPacket(int containerId, ResourceLocation recipeId) {
        this.containerId = containerId;
        this.recipeId = recipeId;
        this.valid = true;
    }

    private AnvilRecipeSelectedPacket() {
        this.valid = false;
    }

    public int getContainerId() {
        return containerId;
    }

    public ResourceLocation getRecipeId() {
        return recipeId;
    }

    public static AnvilRecipeSelectedPacket decode(FriendlyByteBuf buffer) {
        AnvilRecipeSelectedPacket packet = new AnvilRecipeSelectedPacket();
        try {
            packet.containerId = buffer.readInt();
            packet.recipeId = buffer.readResourceLocation();
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
        buffer.writeResourceLocation(recipeId);
    }

    public boolean isValid() {
        return valid;
    }

}
