package net.jmb19905.medievalworlds.common.item;

import net.jmb19905.medievalworlds.client.renderers.curio.CloakRenderer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.Item;
import org.jetbrains.annotations.Nullable;
import top.theillusivec4.curios.api.client.CuriosRendererRegistry;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

public class CloakItem extends Item implements ICurioItem {
    public CloakItem(Properties properties) {
        super(properties);
        CuriosRendererRegistry.register(this, CloakRenderer::new);
    }

    @Nullable
    @Override
    public SoundEvent getEquipSound() {
        return SoundEvents.ARMOR_EQUIP_LEATHER;
    }
}
