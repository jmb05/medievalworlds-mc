package net.jmb19905.medievalworlds.common.networking;

import net.jmb19905.medievalworlds.client.screen.AnvilRecipeSelectedPacket;
import net.jmb19905.medievalworlds.common.blockentities.AnvilBlockEntity;
import net.jmb19905.medievalworlds.common.capability.motion.Motion;
import net.jmb19905.medievalworlds.common.item.lance.LanceItem;
import net.jmb19905.medievalworlds.common.menus.CustomAnvilMenu;
import net.jmb19905.medievalworlds.common.menus.CustomSmithingMenu;
import net.jmb19905.medievalworlds.common.recipes.anvil.AnvilRecipe;
import net.jmb19905.medievalworlds.core.registries.MWRecipeSerializers;
import net.jmb19905.medievalworlds.util.ConfigHandler;
import net.jmb19905.medievalworlds.util.Util;
import net.minecraft.SharedConstants;
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
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.PacketDistributor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.function.Supplier;

public class MessageHandlerOnServer {

    private static final Logger LOGGER = LogManager.getLogger();

    /**
     * Called when a message is received of the appropriate type.
     * CALLED BY THE NETWORK THREAD, NOT THE SERVER THREAD
     * @param message The message
     */
    public static void onEntityMessageToServerReceived(final EntityMessageToServer message, Supplier<NetworkEvent.Context> ctxSupplier) {
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
            context.enqueueWork(() -> processEntityMessageToServer(message, sendingPlayer));
        }else{
            LOGGER.warn("ServerPlayer was null when EntityMessageToServer was received -> not processing Message");
        }
    }

    static void processEntityMessageToServer(EntityMessageToServer message, ServerPlayer sendingPlayer) {
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
                        CritEffectPacket msg = new CritEffectPacket(new Vec3(entity.getX(), entity.getY(), entity.getZ()), boundingBox.maxX - boundingBox.minX, boundingBox.maxY - boundingBox.minY, boundingBox.maxZ - boundingBox.minZ);   // must generate a fresh message for every player!
                        ResourceKey<Level> playerDimension = sendingPlayer.getLevel().dimension();
                        NetworkStartupCommon.simpleChannel.send(PacketDistributor.DIMENSION.with(() -> playerDimension), msg);
                    }

                    Motion motion = (Motion) sendingPlayer.getCapability(Motion.MOTION_CAPABILITY).orElse(new Motion());
                    double moveDist = motion.getMoveDist();

                    double speedFactor = moveDist + 0.25;

                    float attackDamageFactor = ((LanceItem) (sendingPlayer.getUseItem().getItem())).getAttackDamageFactor();
                    float attackDamage = (float) (ConfigHandler.COMMON.lanceBaseAttackDamage.get() * speedFactor * attackDamageFactor);

                    LivingEntity livingEntity = (LivingEntity) entity;
                    livingEntity.hurt(DamageSource.playerAttack(sendingPlayer), attackDamage);
                    laneStack.hurtAndBreak(1, sendingPlayer, player -> player.broadcastBreakEvent(player.getUsedItemHand()));
                    sendingPlayer.getCooldowns().addCooldown(laneStack.getItem(), ((LanceItem) laneStack.getItem()).getFullCharge());
                }
            }
        }
    }

    public static void onAnvilRecipeSelectedPacket(final AnvilRecipeSelectedPacket packet, Supplier<NetworkEvent.Context> ctxSupplier) {
        NetworkEvent.Context context = ctxSupplier.get();
        LogicalSide sideReceived = context.getDirection().getReceptionSide();
        context.setPacketHandled(true);

        if (sideReceived != LogicalSide.SERVER) {
            LOGGER.warn("AnvilRecipeSelectedPacket received on wrong side:" + context.getDirection().getReceptionSide());
            return;
        }
        if (!packet.isValid()) {
            LOGGER.warn("AnvilRecipeSelectedPacket was invalid" + packet);
            return;
        }

        final ServerPlayer sendingPlayer = context.getSender();

        if(sendingPlayer != null) {
            context.enqueueWork(() -> processAnvilRecipeSelectedPacket(packet, sendingPlayer));
        }else{
            LOGGER.warn("ServerPlayer was null when AnvilRecipeSelectedPacket was received -> not processing Packet");
        }
    }

    static void processAnvilRecipeSelectedPacket(AnvilRecipeSelectedPacket packet, ServerPlayer sendingPlayer) {
        if(sendingPlayer.containerMenu.containerId == packet.getContainerId()) {
            CustomAnvilMenu menu = (CustomAnvilMenu) sendingPlayer.containerMenu;
            Level level = sendingPlayer.getLevel();
            AnvilBlockEntity entity = (AnvilBlockEntity) level.getBlockEntity(menu.getBlockEntityPos());
            if(entity != null) {
                List<AnvilRecipe> recipes = Util.findRecipeByType(MWRecipeSerializers.ANVIL_TYPE, level)
                        .stream()
                        .filter(recipe -> ((AnvilRecipe) recipe).matches(entity.getInventory().getStackInSlot(0)))
                        .map(recipe -> (AnvilRecipe) recipe)
                        .toList();
                try {
                    recipes.stream().filter(recipe -> recipe.getId().equals(packet.getRecipeId())).findAny().ifPresent(entity::setCurrentRecipe);
                }catch (IndexOutOfBoundsException ignored) {}
            }
        }
    }

    public static void onDrawSword(final DrawSwordPacket packet, Supplier<NetworkEvent.Context> ctxSupplier) {
        NetworkEvent.Context context = ctxSupplier.get();
        LogicalSide sideReceived = context.getDirection().getReceptionSide();
        context.setPacketHandled(true);

        if (sideReceived != LogicalSide.SERVER) {
            LOGGER.warn("DrawSwordPacket received on wrong side:" + context.getDirection().getReceptionSide());
            return;
        }
        if (!packet.isMessageValid()) {
            LOGGER.warn("DrawSwordPacket was invalid" + packet);
            return;
        }

        final ServerPlayer player = context.getSender();

        if(player != null) {
            context.enqueueWork(() -> processDrawSword(packet, player));
        }
    }

    private static void processDrawSword(final DrawSwordPacket packet, ServerPlayer player) {

    }

    public static void onRenameItemPacketReceived(final MWServerboundRenameItemPacket packet, Supplier<NetworkEvent.Context> ctxSupplier) {
        NetworkEvent.Context context = ctxSupplier.get();
        LogicalSide sideReceived = context.getDirection().getReceptionSide();
        context.setPacketHandled(true);

        if (sideReceived != LogicalSide.SERVER) {
            LOGGER.warn("MWServerboundRenameItemPacket received on wrong side:" + context.getDirection().getReceptionSide());
            return;
        }
        if (!packet.isValid()) {
            LOGGER.warn("MWServerboundRenameItemPacket was invalid" + packet);
            return;
        }

        final ServerPlayer player = context.getSender();

        if(player != null) {
            context.enqueueWork(() -> processRename(packet, player));
        }
    }

    static void processRename(final MWServerboundRenameItemPacket packet, ServerPlayer player) {
        if (player.containerMenu instanceof CustomSmithingMenu smithingMenu) {
            String s = SharedConstants.filterText(packet.getName());
            if (s.length() <= 50) {
                smithingMenu.setItemName(s);
            }
        }
    }

    public static boolean isThisProtocolAcceptedByServer(String protocolVersion) {
        return NetworkStartupCommon.MESSAGE_PROTOCOL_VERSION.equals(protocolVersion);
    }
}