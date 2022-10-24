package net.jmb19905.medievalworlds.common.item.armor;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public abstract class AbstractKnightArmorItem extends ArmorItem {

    protected final String materialString;

    public AbstractKnightArmorItem(ArmorMaterial material, EquipmentSlot slot, Properties properties, String materialString) {
        super(material, slot, properties);
        this.materialString = materialString;
    }

    @Override
    public void onArmorTick(ItemStack stack, Level level, Player player) {
        player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 1, 1));
    }
}
