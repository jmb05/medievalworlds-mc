package net.jmb19905.medievalworlds.common.capability;

import net.jmb19905.medievalworlds.common.capability.motion.Motion;
import net.jmb19905.medievalworlds.common.capability.motion.MotionCapProvider;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class CapabilityAttachEventHandler {

    @SubscribeEvent
    public static void attachCapabilityToEntityHandler(AttachCapabilitiesEvent<Entity> event){
        Entity entity = event.getObject();
        if(entity instanceof Player) {
            event.addCapability(Motion.ID, new MotionCapProvider());
        }
    }

}
