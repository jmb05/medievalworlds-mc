package net.jmb19905.medievalworlds.common.networking;

import net.jmb19905.medievalworlds.client.screen.AnvilRecipeSelectedPacket;
import net.jmb19905.medievalworlds.common.blockentities.AnvilBlockEntity;
import net.jmb19905.medievalworlds.common.menus.CustomAnvilMenu;
import net.jmb19905.medievalworlds.common.recipes.anvil.AnvilRecipe;
import net.jmb19905.medievalworlds.common.registries.MWRecipeSerializers;
import net.jmb19905.medievalworlds.util.MWUtil;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.network.NetworkEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.function.Supplier;

public class MessageHandlerOnServer {

    private static final Logger LOGGER = LogManager.getLogger();

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
                List<AnvilRecipe> recipes = MWUtil.findRecipeByType(MWRecipeSerializers.ANVIL_TYPE, level)
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

    public static boolean isThisProtocolAcceptedByServer(String protocolVersion) {
        return NetworkStartupCommon.MESSAGE_PROTOCOL_VERSION.equals(protocolVersion);
    }
}