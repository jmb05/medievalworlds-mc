package net.jmb19905.medievalworlds.networking;

import net.jmb19905.medievalworlds.item.lance.EntityMessageToServer;
import net.jmb19905.medievalworlds.item.lance.TargetEffectMessageToClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.dimension.DimensionType;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.PacketDistributor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Objects;
import java.util.function.Supplier;

public class MessageHandlerOnServer {

    /**
     * Called when a message is received of the appropriate type.
     * CALLED BY THE NETWORK THREAD, NOT THE SERVER THREAD
     * @param message The message
     */
    public static void onMessageReceived(final EntityMessageToServer message, Supplier<NetworkEvent.Context> ctxSupplier) {
        NetworkEvent.Context context = ctxSupplier.get();
        LogicalSide sideReceived = context.getDirection().getReceptionSide();
        context.setPacketHandled(true);

        if (sideReceived != LogicalSide.SERVER) {
            LOGGER.warn("EntityMessageToServer received on wrong side:" + context.getDirection().getReceptionSide());
            return;
        }
        if (!message.isMessageValid()) {
            LOGGER.warn("EntityMessageToServer was invalid" + message.toString());
            return;
        }

        final ServerPlayerEntity sendingPlayer = context.getSender();

        if(sendingPlayer != null) {
            context.enqueueWork(() -> processMessage(message, sendingPlayer));
        }else{
            LOGGER.warn("EntityPlayerMP was null when EntityMessageToServer was received -> not processing Message");
        }
    }

    static void processMessage(EntityMessageToServer message, ServerPlayerEntity sendingPlayer) {
        Entity entity = sendingPlayer.world.getEntityByID(message.getEntityID());

        //Attack Entity
        if(entity instanceof LivingEntity){
            if(message.isCritical()) {
                AxisAlignedBB boundingBox = entity.getBoundingBox();
                TargetEffectMessageToClient msg = new TargetEffectMessageToClient(Objects.requireNonNull(entity.getPositionVec()), boundingBox.maxX - boundingBox.minX, boundingBox.maxY - boundingBox.minY, boundingBox.maxZ - boundingBox.minZ);   // must generate a fresh message for every player!
                DimensionType playerDimension = sendingPlayer.dimension;
                NetworkStartupCommon.simpleChannel.send(PacketDistributor.DIMENSION.with(() -> playerDimension), msg);
            }

            LivingEntity livingEntity = (LivingEntity) entity;
            livingEntity.attackEntityFrom(DamageSource.causePlayerDamage(sendingPlayer), message.getAttackDamage());
        }
    }

    public static boolean isThisProtocolAcceptedByServer(String protocolVersion) {
        return NetworkStartupCommon.MESSAGE_PROTOCOL_VERSION.equals(protocolVersion);
    }

    private static final Logger LOGGER = LogManager.getLogger();
}