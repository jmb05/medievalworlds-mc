package net.jmb19905.medievalworlds.client.networking;

import net.jmb19905.medievalworlds.common.capability.hood.Hood;
import net.jmb19905.medievalworlds.common.networking.NetworkStartupCommon;
import net.jmb19905.medievalworlds.common.networking.SteamEffectPacket;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.util.LogicalSidedProvider;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.network.NetworkEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;
import java.util.function.Supplier;

public class MessageHandlerOnClient {

    //private static Random random = new Random();

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
            level.addAlwaysVisibleParticle(ParticleTypes.POOF, coords.getX() + random.nextDouble() * spread, coords.getY() + 1.2D * spread, coords.getZ() + random.nextDouble() * spread, (random.nextDouble() - 0.5) * 0.05, 0.03D, (random.nextDouble() - 0.5) * 0.05);
        }
    }

    public static void onHoodChangePacketReceived(final HoodChangePacket.ToClient packet, Supplier<NetworkEvent.Context> ctxSupplier) {
        NetworkEvent.Context context = ctxSupplier.get();
        LogicalSide sideReceived = context.getDirection().getReceptionSide();
        context.setPacketHandled(true);

        System.out.println("Client: HCP received");

        if (sideReceived != LogicalSide.CLIENT) {
            LOGGER.warn("HoodChangePacket received on wrong side:" + context.getDirection().getReceptionSide());
            return;
        }
        if (!packet.isValid()) {
            LOGGER.warn("HoodChangePacket was invalid" + packet);
            return;
        }

        Optional<Level> clientWorld = LogicalSidedProvider.CLIENTWORLD.get(sideReceived);
        if (clientWorld.isEmpty() || !(clientWorld.get() instanceof ClientLevel)) {
            LOGGER.warn("HoodChangePacket context could not provide a ClientWorld.");
            return;
        }

        context.enqueueWork(() -> processHoodChange(packet, clientWorld.get()));
    }

    static void processHoodChange(HoodChangePacket.ToClient packet, Level level) {
        Player player = level.getPlayerByUUID(packet.getPlayerId());
        if (player != null) {
            player.getCapability(Hood.HOOD_CAPABILITY).ifPresent(iHood -> ((Hood) iHood).setHoodDown(packet.isHoodDown()));
        } else {
            LOGGER.warn("HoodChange sent for player that doesn't exist");
        }
    }

    private static final Logger LOGGER = LogManager.getLogger();
}