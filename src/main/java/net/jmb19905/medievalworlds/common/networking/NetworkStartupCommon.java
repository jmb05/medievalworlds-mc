package net.jmb19905.medievalworlds.common.networking;

import net.jmb19905.medievalworlds.MedievalWorlds;
import net.jmb19905.medievalworlds.client.networking.MessageHandlerOnClient;
import net.jmb19905.medievalworlds.client.screen.AnvilRecipeSelectedPacket;
import net.jmb19905.medievalworlds.common.blockentities.bellows.BellowsActivationPacket;
import net.jmb19905.medievalworlds.common.item.lance.EntityMessageToServer;
import net.jmb19905.medievalworlds.common.item.lance.CritEffectPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.Optional;

public class NetworkStartupCommon {
    public static SimpleChannel simpleChannel;

    public static final byte ENTITY_ID_MESSAGE_ID = 0;
    public static final byte TARGET_EFFECT_MESSAGE_ID = 1;
    public static final byte STEAM_EFFECT_MESSAGE_ID = 2;
    public static final byte ANVIL_RECIPE_SELECTED_ID = 3;
    public static final byte BELLOWS_ACTIVATION_ID = 4;

    public static final byte RENAME_ITEM_ID = 5;

    public static final String MESSAGE_PROTOCOL_VERSION = "2";

    public static final ResourceLocation simpleChannelRL = new ResourceLocation(MedievalWorlds.MOD_ID, "mwchannel");

    @SubscribeEvent
    public static void onCommonSetupEvent(FMLCommonSetupEvent event) {
        simpleChannel = NetworkRegistry.newSimpleChannel(simpleChannelRL, () -> MESSAGE_PROTOCOL_VERSION,
                MessageHandlerOnClient::isThisProtocolAcceptedByClient,
                MessageHandlerOnServer::isThisProtocolAcceptedByServer);

        simpleChannel.registerMessage(ENTITY_ID_MESSAGE_ID, EntityMessageToServer.class,
                EntityMessageToServer::encode, EntityMessageToServer::decode,
                MessageHandlerOnServer::onEntityMessageToServerReceived,
                Optional.of(NetworkDirection.PLAY_TO_SERVER));

        simpleChannel.registerMessage(TARGET_EFFECT_MESSAGE_ID, CritEffectPacket.class,
                CritEffectPacket::encode, CritEffectPacket::decode,
                MessageHandlerOnClient::onMessageReceived,
                Optional.of(NetworkDirection.PLAY_TO_CLIENT));

        simpleChannel.registerMessage(STEAM_EFFECT_MESSAGE_ID, SteamEffectPacket.class,
                SteamEffectPacket::encode, SteamEffectPacket::decode,
                MessageHandlerOnClient::onSteamEffectPacketReceived,
                Optional.of(NetworkDirection.PLAY_TO_CLIENT));

        simpleChannel.registerMessage(ANVIL_RECIPE_SELECTED_ID, AnvilRecipeSelectedPacket.class,
                AnvilRecipeSelectedPacket::encode, AnvilRecipeSelectedPacket::decode,
                MessageHandlerOnServer::onAnvilRecipeSelectedPacket,
                Optional.of(NetworkDirection.PLAY_TO_SERVER));

        simpleChannel.registerMessage(BELLOWS_ACTIVATION_ID, BellowsActivationPacket.class,
                BellowsActivationPacket::encode, BellowsActivationPacket::decode,
                MessageHandlerOnClient::onBellowsActivationPacketReceived,
                Optional.of(NetworkDirection.PLAY_TO_CLIENT));
        simpleChannel.registerMessage(RENAME_ITEM_ID, MWServerboundRenameItemPacket.class,
                MWServerboundRenameItemPacket::encode, MWServerboundRenameItemPacket::decode,
                MessageHandlerOnServer::onRenameItemPacketReceived,
                Optional.of(NetworkDirection.PLAY_TO_SERVER));
    }

}