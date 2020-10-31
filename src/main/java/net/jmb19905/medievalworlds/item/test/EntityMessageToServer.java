package net.jmb19905.medievalworlds.item.test;

import net.minecraft.network.PacketBuffer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * This will hold the UUID of an entity that is attacked with the lance that we need to Send to the server
 */
public class EntityMessageToServer {

    private static final Logger LOGGER = LogManager.getLogger();
    private int entityID;
    private int attackDamage;
    private boolean messageIsValid;

    public EntityMessageToServer(int entityID, int attackDamage){
        this.entityID = entityID;
        this.attackDamage = attackDamage;
        messageIsValid = true;
    }

    public int getEntityID() {
        return entityID;
    }

    public int getAttackDamage() {
        return attackDamage;
    }

    public boolean isMessageValid() {
        return messageIsValid;
    }

    // not a valid way to construct the message
    private EntityMessageToServer() {
        messageIsValid = false;
    }

    public static EntityMessageToServer decode(PacketBuffer buf) {
        EntityMessageToServer retval = new EntityMessageToServer();
        try {
            retval.entityID = buf.readInt();
            retval.attackDamage = buf.readInt();

        } catch (IllegalArgumentException | IndexOutOfBoundsException e) {
            LOGGER.warn("Exception while reading EntityMessageToServer: " + e);
            return retval;
        }
        retval.messageIsValid = true;
        return retval;
    }

    public void encode(PacketBuffer buf) {
        if (!messageIsValid) return;
        buf.writeInt(entityID);
        buf.writeInt(attackDamage);
    }

    @Override
    public String toString() {
        return "EntityMessageToServer{" +
                "entityID=" + entityID +
                ", attackDamage=" + attackDamage +
                ", messageIsValid=" + messageIsValid +
                '}';
    }
}
