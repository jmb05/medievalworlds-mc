package net.jmb19905.medievalworlds.client.networking;

import net.jmb19905.medievalworlds.common.networking.NetworkStartupCommon;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MessageHandlerOnClient {

    public static boolean isThisProtocolAcceptedByClient(String protocolVersion) {
        return NetworkStartupCommon.MESSAGE_PROTOCOL_VERSION.equals(protocolVersion);
    }

    private static final Logger LOGGER = LogManager.getLogger();
}