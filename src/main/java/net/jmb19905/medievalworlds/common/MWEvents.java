package net.jmb19905.medievalworlds.common;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.jmb19905.medievalworlds.MedievalWorlds;
import net.jmb19905.medievalworlds.common.capability.MWCapabilityManager;
import net.jmb19905.medievalworlds.common.capability.QuiverInv;
import net.jmb19905.medievalworlds.common.item.quiver.QuiverItem;
import net.jmb19905.medievalworlds.common.registries.MWItems;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import top.theillusivec4.curios.api.CuriosApi;

import java.util.List;

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

        @SubscribeEvent
        public static void onVillagerTradesSetup(VillagerTradesEvent event) {
            VillagerProfession type = event.getType();
            Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();
            if (type == VillagerProfession.FLETCHER) {
                List<VillagerTrades.ItemListing> lvl2Trades = trades.get(2);
                lvl2Trades.add(new VillagerTrades.EmeraldForItems(MWItems.getToolPart("iron_arrow_head").get(), 12, 16, 10));
                lvl2Trades.add(new VillagerTrades.ItemsForEmeralds(MWItems.IRON_ARROW.get(), 1, 16, 8));
            }
        }
    }
}