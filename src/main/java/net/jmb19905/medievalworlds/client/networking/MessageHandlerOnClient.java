package net.jmb19905.medievalworlds.client.networking;

import net.jmb19905.medievalworlds.common.networking.NetworkStartupCommon;
import net.jmb19905.medievalworlds.common.networking.SteamEffectPacket;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.util.LogicalSidedProvider;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.network.NetworkEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;
import java.util.function.Supplier;

public class MessageHandlerOnClient {

    public static boolean isThisProtocolAcceptedByClient(String protocolVersion) {
        return NetworkStartupCommon.MESSAGE_PROTOCOL_VERSION.equals(protocolVersion);
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
        RandomSource random = level.getRandom();
        BlockPos coords = packet.getTargetCoords();
        float spread = packet.getSpread();
        for(int i = 0; i < 8; ++i) {
            level.addParticle(ParticleTypes.CLOUD, coords.getX() + random.nextDouble() * spread, coords.getY() + 1.2D * spread, coords.getZ() + random.nextDouble() * spread, random.nextDouble() * .1, 0.1D, Math.abs(random.nextDouble()) * .05);
        }
    }

    private static final Logger LOGGER = LogManager.getLogger();
}