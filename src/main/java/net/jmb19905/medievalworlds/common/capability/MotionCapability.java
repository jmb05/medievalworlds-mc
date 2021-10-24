package net.jmb19905.medievalworlds.common.capability;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

public class MotionCapability {

    @CapabilityInject(Motion.class)
    public static Capability<Motion> CAPABILITY_MOTION = null;

    public static void register(){
        CapabilityManager.INSTANCE.register(Motion.class, new Motion.MotionNBTStorage(), Motion::createDefaultInstance);
    }

}
