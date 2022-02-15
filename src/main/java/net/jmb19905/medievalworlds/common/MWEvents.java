package net.jmb19905.medievalworlds.common;

import net.jmb19905.medievalworlds.MedievalWorlds;
import net.jmb19905.medievalworlds.common.block.anvil.CustomAnvilBlock;
import net.jmb19905.medievalworlds.common.item.ForgeHammerItem;
import net.minecraft.world.level.block.FireBlock;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class MWEvents {

    @Mod.EventBusSubscriber(modid = MedievalWorlds.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
    public static class ForgeEvents {

        @SubscribeEvent
        public static void onWorldLoad(WorldEvent.Load event) {

        }

        @SubscribeEvent
        public static void onPlayerRightClick(PlayerInteractEvent.RightClickBlock event) {
            if (event.getWorld().getBlockState(event.getPos()).getBlock() instanceof CustomAnvilBlock && event.getItemStack().getItem() instanceof ForgeHammerItem) {
                //event.setUseBlock(Event.Result.ALLOW);
            }
        }

    }

}
