package net.jmb19905.medievalworlds.common;

import net.jmb19905.medievalworlds.MedievalWorlds;
import net.jmb19905.medievalworlds.common.capability.MWCapabilityManager;
import net.jmb19905.medievalworlds.common.capability.quiverInv.QuiverInv;
import net.jmb19905.medievalworlds.common.item.quiver.QuiverItem;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import top.theillusivec4.curios.api.CuriosApi;

public class MWEvents {
    @Mod.EventBusSubscriber(modid = MedievalWorlds.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
    public static class ForgeEvents {
        @SubscribeEvent
        public static void onPlayerPickup(EntityItemPickupEvent event) {
            Player player = event.getEntity();
            ItemStack stack = event.getItem().getItem();
            CuriosApi.getCuriosHelper().findFirstCurio(player, curioStack -> curioStack.getItem() instanceof QuiverItem).ifPresent(slotResult -> {
                ItemStack quiverStack = slotResult.stack();
                QuiverInv inv = MWCapabilityManager.retrieveCapability(quiverStack, QuiverInv.QUIVER_INV_CAPABILITY);
                if (inv == null) return;
                int addCount = inv.add(stack);
                if (addCount > 0) {
                    if (addCount == stack.getCount()) {
                        player.take(event.getItem(), 0);
                        event.getItem().discard();
                        event.setCanceled(true);
                    } else {
                        stack.shrink(addCount);
                    }
                }
            });
        }
    }
}