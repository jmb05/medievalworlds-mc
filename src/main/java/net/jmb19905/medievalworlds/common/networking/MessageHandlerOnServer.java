package net.jmb19905.medievalworlds.common.networking;

import net.jmb19905.medievalworlds.common.capability.Motion;
import net.jmb19905.medievalworlds.common.item.LanceItem;
import net.jmb19905.medievalworlds.common.item.lance.EntityMessageToServer;
import net.jmb19905.medievalworlds.common.item.lance.TargetEffectMessageToClient;
import net.jmb19905.medievalworlds.util.ConfigHandler;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.entity.animal.horse.Llama;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fmllegacy.network.NetworkEvent;
import net.minecraftforge.fmllegacy.network.PacketDistributor;
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
            LOGGER.warn("EntityMessageToServer was invalid" + message);
            return;
        }

        final ServerPlayer sendingPlayer = context.getSender();

        if(sendingPlayer != null) {
            context.enqueueWork(() -> processMessage(message, sendingPlayer));
        }else{
            LOGGER.warn("EntityPlayerMP was null when EntityMessageToServer was received -> not processing Message");
        }
    }

    @SuppressWarnings("ConstantConditions")
    static void processMessage(EntityMessageToServer message, ServerPlayer sendingPlayer) {
        Entity entity = sendingPlayer.level.getEntity(message.getEntityID());
        //Attack Entity
        if(entity instanceof LivingEntity){
            ItemStack laneStack = sendingPlayer.getUseItem();
            if(laneStack.getItem() instanceof LanceItem) {
                if (sendingPlayer.getRootVehicle() instanceof AbstractHorse && !(sendingPlayer.getRootVehicle() instanceof Llama)) {
                    if(sendingPlayer.getRootVehicle().equals(entity)){
                        return;
                    }
                    if (message.isCritical()) {
                        AABB boundingBox = entity.getBoundingBox();
                        TargetEffectMessageToClient msg = new TargetEffectMessageToClient(new Vec3(entity.getX(), entity.getY(), entity.getZ()), boundingBox.maxX - boundingBox.minX, boundingBox.maxY - boundingBox.minY, boundingBox.maxZ - boundingBox.minZ);   // must generate a fresh message for every player!
                        ResourceKey<Level> playerDimension = sendingPlayer.getRespawnDimension();  // TODO: not respawn dimension but playerDimension ???
                        NetworkStartupCommon.simpleChannel.send(PacketDistributor.DIMENSION.with(() -> playerDimension), msg);
                    }

                    Motion motion = (Motion) sendingPlayer.getCapability(Motion.MOTION_CAPABILITY).orElse(new Motion());
                    double moveDist = motion.getMoveDist();

                    double speedFactor = moveDist + 0.25;

                    float attackDamageFactor = ((LanceItem) (sendingPlayer.getUseItem().getItem())).getAttackDamageFactor();
                    float attackDamage = (float) (ConfigHandler.COMMON.lanceBaseAttackDamage.get() * speedFactor * attackDamageFactor);
                    System.out.println(ConfigHandler.COMMON.lanceBaseAttackDamage.get() + " * " + speedFactor + " * " + attackDamageFactor + " = " + attackDamage);

                    LivingEntity livingEntity = (LivingEntity) entity;
                    livingEntity.hurt(DamageSource.playerAttack(sendingPlayer), attackDamage);
                    laneStack.hurtAndBreak(1, sendingPlayer, player -> player.broadcastBreakEvent(player.getUsedItemHand()));
                    sendingPlayer.getCooldowns().addCooldown(laneStack.getItem(), ((LanceItem) laneStack.getItem()).getFullCharge());
                }
            }else {
                System.err.println("Client tried to use lanceattack without holding lance");
            }
        }
    }

    public static boolean isThisProtocolAcceptedByServer(String protocolVersion) {
        return NetworkStartupCommon.MESSAGE_PROTOCOL_VERSION.equals(protocolVersion);
    }

    private static final Logger LOGGER = LogManager.getLogger();
}