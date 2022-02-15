package net.jmb19905.medievalworlds.client.networking;

import net.jmb19905.medievalworlds.common.blockentities.bellows.BellowsActivationPacket;
import net.jmb19905.medievalworlds.common.blockentities.bellows.BellowsBlockEntity;
import net.jmb19905.medievalworlds.common.item.lance.CritEffectPacket;
import net.jmb19905.medievalworlds.common.networking.NetworkStartupCommon;
import net.jmb19905.medievalworlds.common.networking.SteamEffectPacket;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.util.LogicalSidedProvider;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.network.NetworkEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;
import java.util.Random;
import java.util.function.Supplier;

public class MessageHandlerOnClient {

    public static void onMessageReceived(final CritEffectPacket message, Supplier<NetworkEvent.Context> ctxSupplier) {
        NetworkEvent.Context ctx = ctxSupplier.get();
        LogicalSide sideReceived = ctx.getDirection().getReceptionSide();
        ctx.setPacketHandled(true);

        if (sideReceived != LogicalSide.CLIENT) {
            LOGGER.warn("TargetEffectMessageToClient received on wrong side :" + ctx.getDirection().getReceptionSide());
            return;
        }
        if (!message.isMessageValid()) {
            LOGGER.warn("TargetEffectMessageToClient was invalid " + message);
            return;
        }
        // we know for sure that this handler is only used on the client side, so it is ok to assume
        //  that the ctx handler is a client, and that Minecraft exists.
        // Packets received on the server side must be handled differently!  See MessageHandlerOnServer

        Optional<Level> clientWorld = LogicalSidedProvider.CLIENTWORLD.get(sideReceived);
        if (clientWorld.isEmpty() || !(clientWorld.get() instanceof ClientLevel)) {
            LOGGER.warn("TargetEffectMessageToClient context could not provide a ClientWorld.");
            return;
        }

        // This code creates a new task which will be executed by the client during the next tick
        //  In this case, the task is to call messageHandlerOnClient.processMessage(worldclient, message)
        ctx.enqueueWork(() -> processCritEffectPacket((ClientLevel) clientWorld.get(), message));
    }

    // This message is called from the Client thread.
    //   It spawns a number of Particle particles at the target location within a short range around the target location
    private static void processCritEffectPacket(ClientLevel worldClient, CritEffectPacket message) {
        Random random = new Random();
        final int NUMBER_OF_PARTICLES = 10;
        for (int i = 0; i < NUMBER_OF_PARTICLES; ++i) {
            Vec3 targetCoordinates = message.getTargetCoordinates();
            double spawnXPos = targetCoordinates.x + (2 * random.nextDouble() - 1) * message.getXSpread();
            double spawnYPos = targetCoordinates.y + (2 * random.nextDouble() - 1) * message.getYSpread();
            double spawnZPos = targetCoordinates.z + (2 * random.nextDouble() - 1) * message.getZSpread();
            worldClient.addParticle(ParticleTypes.CRIT, spawnXPos, spawnYPos, spawnZPos, 0, 0, 0);
            worldClient.playSound(null, spawnXPos, spawnYPos, spawnZPos, SoundEvents.PLAYER_ATTACK_CRIT, SoundSource.PLAYERS, 1.0F, 1.0F);
        }
    }

    public static void onSteamEffectPacketReceived(final SteamEffectPacket packet, Supplier<NetworkEvent.Context> ctxSupplier) {
        NetworkEvent.Context ctx = ctxSupplier.get();
        LogicalSide sideReceived = ctx.getDirection().getReceptionSide();
        ctx.setPacketHandled(true);

        if(sideReceived != LogicalSide.CLIENT) {
            LOGGER.warn("SteamEffectPacket received on wrong side: " + ctx.getDirection().getReceptionSide());
            return;
        }if(!packet.isValid()) {
            LOGGER.warn("SteamEffectPacket was invalid " + packet);
            return;
        }

        Optional<Level> clientWorld = LogicalSidedProvider.CLIENTWORLD.get(sideReceived);
        if (clientWorld.isEmpty() || !(clientWorld.get() instanceof ClientLevel)) {
            LOGGER.warn("SteamEffectPacket context could not provide a ClientWorld.");
            return;
        }

        ctx.enqueueWork(() -> processSteamEffectPacket((ClientLevel) clientWorld.get(), packet));
    }

    private static void processSteamEffectPacket(ClientLevel level, SteamEffectPacket packet) {
        Random random = level.getRandom();
        BlockPos coords = packet.getTargetCoords();
        float spread = packet.getSpread();
        for(int i = 0; i < 8; ++i) {
            level.addParticle(ParticleTypes.CLOUD, coords.getX() + random.nextDouble() * spread, coords.getY() + 1.2D * spread, coords.getZ() + random.nextDouble() * spread, random.nextDouble() * .1, 0.1D, Math.abs(random.nextDouble()) * .05);
        }
    }

    public static void onBellowsActivationPacketReceived(final BellowsActivationPacket packet, Supplier<NetworkEvent.Context> ctxSupplier) {
        NetworkEvent.Context ctx = ctxSupplier.get();
        LogicalSide side = ctx.getDirection().getReceptionSide();
        ctx.setPacketHandled(true);

        if(side != LogicalSide.CLIENT) {
            LOGGER.warn("BellowsActivationPacket received on wrong side: " + ctx.getDirection().getReceptionSide());
            return;
        }if(!packet.isValid()) {
            LOGGER.warn("BellowsActivationPacket was invalid " + packet);
            return;
        }

        Optional<Level> clientWorld = LogicalSidedProvider.CLIENTWORLD.get(side);
        if(clientWorld.isEmpty() || !(clientWorld.get() instanceof ClientLevel)) {
            LOGGER.warn("BellowsActivationPacket ctx could not provide a ClientLevel");
            return;
        }

        ctx.enqueueWork(() -> processBellowsActivationPacket((ClientLevel) clientWorld.get(), packet));
    }

    private static void processBellowsActivationPacket(ClientLevel level, BellowsActivationPacket packet) {
        BlockEntity entity = level.getBlockEntity(packet.getPos());
        if(entity instanceof BellowsBlockEntity bellowsEntity) {
            bellowsEntity.setActive(packet.isActivated());
        }
    }

    public static boolean isThisProtocolAcceptedByClient(String protocolVersion) {
        return NetworkStartupCommon.MESSAGE_PROTOCOL_VERSION.equals(protocolVersion);
    }

    private static final Logger LOGGER = LogManager.getLogger();
}