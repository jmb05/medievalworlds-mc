package net.jmb19905.medievalworlds.common.networking;

import net.jmb19905.medievalworlds.MedievalWorlds;
import net.jmb19905.medievalworlds.client.networking.MessageHandlerOnClient;
import net.jmb19905.medievalworlds.common.item.lance.EntityMessageToServer;
import net.jmb19905.medievalworlds.common.item.lance.CritEffectPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fmllegacy.network.NetworkDirection;
import net.minecraftforge.fmllegacy.network.NetworkRegistry;
import net.minecraftforge.fmllegacy.network.simple.SimpleChannel;

import java.util.Optional;

public class NetworkStartupCommon {
    public static SimpleChannel simpleChannel;

    public static final byte ENTITY_ID_MESSAGE_ID = 119;
    public static final byte TARGET_EFFECT_MESSAGE_ID = 120;
    public static final byte STEAM_EFFECT_MESSAGE_ID = 121;

    public static final String MESSAGE_PROTOCOL_VERSION = "1.1";

    public static final ResourceLocation simpleChannelRL = new ResourceLocation(MedievalWorlds.MOD_ID, "mwchannel");

    @SubscribeEvent
    public static void onCommonSetupEvent(FMLCommonSetupEvent event) {
        simpleChannel = NetworkRegistry.newSimpleChannel(simpleChannelRL, () -> MESSAGE_PROTOCOL_VERSION,
                MessageHandlerOnClient::isThisProtocolAcceptedByClient,
                MessageHandlerOnServer::isThisProtocolAcceptedByServer);

        simpleChannel.registerMessage(ENTITY_ID_MESSAGE_ID, EntityMessageToServer.class,
                EntityMessageToServer::encode, EntityMessageToServer::decode,
                MessageHandlerOnServer::onMessageReceived,
                Optional.of(NetworkDirection.PLAY_TO_SERVER));

        simpleChannel.registerMessage(TARGET_EFFECT_MESSAGE_ID, CritEffectPacket.class,
                CritEffectPacket::encode, CritEffectPacket::decode,
                MessageHandlerOnClient::onMessageReceived,
                Optional.of(NetworkDirection.PLAY_TO_CLIENT));

        simpleChannel.registerMessage(STEAM_EFFECT_MESSAGE_ID, SteamEffectPacket.class,
                SteamEffectPacket::encode, SteamEffectPacket::decode,
                MessageHandlerOnClient::onSteamEffectPacketReceived,
                Optional.of(NetworkDirection.PLAY_TO_CLIENT));
    }

}