package net.jmb19905.medievalworlds.networking;

import net.jmb19905.medievalworlds.item.LanceItem;
import net.jmb19905.medievalworlds.item.lance.EntityMessageToServer;
import net.jmb19905.medievalworlds.item.lance.TargetEffectMessageToClient;
import net.jmb19905.medievalworlds.util.ConfigHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.horse.AbstractHorseEntity;
import net.minecraft.entity.passive.horse.LlamaEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;
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
            if(sendingPlayer.getRidingEntity() instanceof AbstractHorseEntity && !(sendingPlayer.getRidingEntity() instanceof LlamaEntity)) {
                if (message.isCritical()) {
                    AxisAlignedBB boundingBox = entity.getBoundingBox();
                    TargetEffectMessageToClient msg = new TargetEffectMessageToClient(Objects.requireNonNull(entity.getPositionVec()), boundingBox.maxX - boundingBox.minX, boundingBox.maxY - boundingBox.minY, boundingBox.maxZ - boundingBox.minZ);   // must generate a fresh message for every player!
                    RegistryKey<World> playerDimension = sendingPlayer.func_241141_L_();  // func_241141_L_ is getPlayerDimension
                    NetworkStartupCommon.simpleChannel.send(PacketDistributor.DIMENSION.with(() -> playerDimension), msg);
                }
                AbstractHorseEntity horseEntity = (AbstractHorseEntity) sendingPlayer.getRidingEntity();

                //blocks per tick
                double speed = horseEntity.getDistanceSq(horseEntity.prevPosX, horseEntity.prevPosY, horseEntity.prevPosZ);

                //because the speed is usually below 1 we add 1
                double speedFactor = speed + 1D;

                float x = 0;
                float bias = 0;

                float k = (float) Math.pow(1 - bias, 3);
                float retVal = (x * k) / (x * k - x + 1);

                if(speedFactor == 1){
                    speedFactor = 0.1D;
                }

                System.out.println("attacked with lance");
                LivingEntity livingEntity = (LivingEntity) entity;
                livingEntity.attackEntityFrom(DamageSource.causePlayerDamage(sendingPlayer), (float) (ConfigHandler.COMMON.lanceBaseAttackDamage.get() * speedFactor * ((LanceItem) (sendingPlayer.getActiveItemStack().getItem())).getAttackDamageFactor()));
            }
        }
    }

    public static boolean isThisProtocolAcceptedByServer(String protocolVersion) {
        return NetworkStartupCommon.MESSAGE_PROTOCOL_VERSION.equals(protocolVersion);
    }

    private static final Logger LOGGER = LogManager.getLogger();
}