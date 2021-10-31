package net.jmb19905.medievalworlds.common.item.armor;

import net.jmb19905.medievalworlds.client.ClientSetup;
import net.jmb19905.medievalworlds.client.model.armor.GambesonModel;
import net.jmb19905.medievalworlds.common.item.tiers.MWArmorMaterials;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.IItemRenderProperties;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.function.Consumer;

public class GambesonChestplateItem extends ArmorItem {

    public GambesonChestplateItem(Properties properties) {
        super(MWArmorMaterials.GAMBESON_MATERIAL, EquipmentSlot.CHEST, properties);
    }

    @Override
    public void initializeClient(@Nonnull Consumer<IItemRenderProperties> consumer) {
        consumer.accept(new IItemRenderProperties() {
            @SuppressWarnings("unchecked")
            @Override
            public <A extends HumanoidModel<?>> A getArmorModel(LivingEntity entityLiving, ItemStack itemStack, EquipmentSlot armorSlot, A _default) {
                GambesonModel model = new GambesonModel(Minecraft.getInstance().getEntityModels().bakeLayer(ClientSetup.GAMBESON_LAYER));
                return (A) model;
            }
        });
    }

    @Nullable
    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
        return "medievalworlds:textures/models/armor/gambeson.png";
    }
}
