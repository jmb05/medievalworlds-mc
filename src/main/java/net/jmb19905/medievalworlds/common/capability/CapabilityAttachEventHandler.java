package net.jmb19905.medievalworlds.common.capability;

import net.jmb19905.medievalworlds.MedievalWorlds;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class CapabilityAttachEventHandler {

    @SubscribeEvent
    public static void attachCapabilityToEntityHandler(AttachCapabilitiesEvent<Entity> event){
        Entity entity = event.getObject();
        if(entity instanceof PlayerEntity) {
            event.addCapability(new ResourceLocation(MedievalWorlds.MOD_ID, "capability_providers_entities"), new CapabilityProviderEntities());
        }
    }

}
