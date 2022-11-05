package net.jmb19905.medievalworlds.client.key;

import net.jmb19905.medievalworlds.common.networking.HoodChangePacket;
import net.jmb19905.medievalworlds.common.capability.Hood;
import net.jmb19905.medievalworlds.common.networking.NetworkStartupCommon;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.PacketDistributor;
import org.lwjgl.glfw.GLFW;

public class MWKeyHandler {

    public static KeyMapping hoodKey = new MWKeyBindingBuilder()
            .description("Hood Key")
            .conflictInGame()
            .keyCode(GLFW.GLFW_KEY_H)
            .onKeyDown((kb, repeat) -> {
                Player player = Minecraft.getInstance().player;
                if(player != null) {
                    player.getCapability(Hood.HOOD_CAPABILITY).ifPresent(hood -> {
                        NetworkStartupCommon.simpleChannel.send(PacketDistributor.SERVER.noArg(), new HoodChangePacket.ToServer(!hood.isHoodDown(), player.getUUID()));
                        hood.setHoodDown(!hood.isHoodDown());
                    });
                }
            })
            .build();

}