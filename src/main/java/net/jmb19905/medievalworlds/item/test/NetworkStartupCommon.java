package net.jmb19905.medievalworlds.item.test;

import net.jmb19905.medievalworlds.MedievalWorlds;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

import java.util.Optional;

import static net.minecraftforge.fml.network.NetworkDirection.PLAY_TO_CLIENT;
import static net.minecraftforge.fml.network.NetworkDirection.PLAY_TO_SERVER;

public class NetworkStartupCommon {
    public static SimpleChannel simpleChannel;    // used to transmit your network messages

    public static final byte ENTITY_ID_MESSAGE_ID = 112;
    public static final byte TARGET_EFFECT_MESSAGE_ID = 120;

    //yay protocol version  1.0 \o/
    public static final String MESSAGE_PROTOCOL_VERSION = "1.0";

    public static final ResourceLocation simpleChannelRL = new ResourceLocation(MedievalWorlds.MOD_ID, "mwchannel");

    @SubscribeEvent
    public static void onCommonSetupEvent(FMLCommonSetupEvent event) {

        simpleChannel = NetworkRegistry.newSimpleChannel(simpleChannelRL, () -> MESSAGE_PROTOCOL_VERSION,
                MessageHandlerOnClient::isThisProtocolAcceptedByClient,
                MessageHandlerOnServer::isThisProtocolAcceptedByServer);

        // Register the two different types of messages:
        //  AirStrike, which is sent from the client to the server to say "call an air strike on {this location} that I just clicked on"
        //  TargetEffect, which is sent from the server to all clients to say "someone called an air strike on {this location}, draw some particles there"

        simpleChannel.registerMessage(ENTITY_ID_MESSAGE_ID, EntityMessageToServer.class,
                EntityMessageToServer::encode, EntityMessageToServer::decode,
                MessageHandlerOnServer::onMessageReceived,
                Optional.of(PLAY_TO_SERVER));

        simpleChannel.registerMessage(TARGET_EFFECT_MESSAGE_ID, TargetEffectMessageToClient.class,
                TargetEffectMessageToClient::encode, TargetEffectMessageToClient::decode,
                MessageHandlerOnClient::onMessageReceived,
                Optional.of(PLAY_TO_CLIENT));

        // it is possible to register the same message class and handler on both sides if you want, eg,
//    simpleChannel.registerMessage(AIRSTRIKE_MESSAGE_ID, AirstrikeMessageToServer.class,
//            AirstrikeMessageBothDirections::encode, AirstrikeMessageBothDirections::decode,
//            MessageHandlerOnBothSides::onMessage);
        // I recommend that you don't do this because it can lead to crashes (and in particular dedicated server problems) if you aren't
        //    very careful to keep the client-side and server-side code separate
    }

}