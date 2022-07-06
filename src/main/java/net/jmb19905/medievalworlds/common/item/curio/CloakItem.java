package net.jmb19905.medievalworlds.common.item.curio;

import net.jmb19905.medievalworlds.client.er.CloakRenderer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.client.CuriosRendererRegistry;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

public class CloakItem extends Item implements ICurioItem {
    public CloakItem(Properties properties) {
        super(properties);
        CuriosRendererRegistry.register(this, CloakRenderer::new);
    }

    @Override
    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        slotContext.entity().level.playSound(null, slotContext.entity(), SoundEvents.ARMOR_EQUIP_LEATHER, SoundSource.PLAYERS, 1, 1);
    }
}
