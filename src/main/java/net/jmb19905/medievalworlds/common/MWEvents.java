package net.jmb19905.medievalworlds.common;

import net.jmb19905.medievalworlds.MedievalWorlds;
import net.jmb19905.medievalworlds.common.block.anvil.AnvilInteraction;
import net.jmb19905.medievalworlds.common.block.slackTub.SlackTubInteraction;
import net.jmb19905.medievalworlds.common.registries.MWRecipeSerializers;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Collections;
import java.util.stream.Collectors;

public class MWEvents {

    @Mod.EventBusSubscriber(modid = MedievalWorlds.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
    public static class ForgeEvents {

        @SubscribeEvent
        public static void onWorldLoad(WorldEvent.Load event) {
            SlackTubInteraction.bootstrap();
            AnvilInteraction.bootstrap((Level) event.getWorld());
        }

    }

    @Mod.EventBusSubscriber(modid = MedievalWorlds.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ModEvents {

    }

}
