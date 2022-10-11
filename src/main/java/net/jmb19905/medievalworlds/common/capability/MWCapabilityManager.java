package net.jmb19905.medievalworlds.common.capability;

import net.jmb19905.medievalworlds.common.capability.hood.Hood;
import net.jmb19905.medievalworlds.common.capability.hood.HoodCapProvider;
import net.jmb19905.medievalworlds.common.capability.hood.IHood;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class MWCapabilityManager {

    @SubscribeEvent
    public static void register(RegisterCapabilitiesEvent event) {
        event.register(IHood.class);
    }

    @SubscribeEvent
    public static void attachCapabilityToEntityHandler(AttachCapabilitiesEvent<Entity> event) {
        Entity entity = event.getObject();
        if(entity instanceof Player) {
            event.addCapability(Hood.ID, new HoodCapProvider());
        }
    }

}
