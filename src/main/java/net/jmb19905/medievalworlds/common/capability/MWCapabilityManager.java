package net.jmb19905.medievalworlds.common.capability;

import net.jmb19905.medievalworlds.common.capability.hood.Hood;
import net.jmb19905.medievalworlds.common.capability.hood.HoodCapProvider;
import net.jmb19905.medievalworlds.common.capability.hood.IHood;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityProvider;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.jetbrains.annotations.Nullable;

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

    @SuppressWarnings({"UnstableApiUsage", "ConstantConditions", "unchecked"})
    @Nullable
    public static <T, C extends T, B extends CapabilityProvider<B>> C retrieveCapability(B obj, Capability<T> capability) {
        return (C) obj.getCapability(capability).orElse(null);
    }

}
