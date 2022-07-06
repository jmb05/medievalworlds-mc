package net.jmb19905.medievalworlds.common.item.armor;

import net.jmb19905.medievalworlds.client.ClientSetup;
import net.jmb19905.medievalworlds.client.er.GambesonCurioRenderer;
import net.jmb19905.medievalworlds.client.model.armor.GambesonModel;
import net.jmb19905.medievalworlds.common.item.tiers.MWArmorMaterials;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.IItemRenderProperties;
import top.theillusivec4.curios.api.client.CuriosRendererRegistry;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.function.Consumer;

public class GambesonChestplateItem extends CurioArmorItem {

    public GambesonChestplateItem(Properties properties) {
        super(MWArmorMaterials.GAMBESON_MATERIAL, EquipmentSlot.CHEST, properties);
        CuriosRendererRegistry.register(this, GambesonCurioRenderer::new);
    }

    @Override
    public void initializeClient(@Nonnull Consumer<IItemRenderProperties> consumer) {
        consumer.accept(new IItemRenderProperties() {
            @Override
            public HumanoidModel<?> getArmorModel(LivingEntity entityLiving, ItemStack itemStack, EquipmentSlot armorSlot, HumanoidModel<?> _default) {
                return new GambesonModel(Minecraft.getInstance().getEntityModels().bakeLayer(ClientSetup.GAMBESON_LAYER));
            }
        });
    }

    @Nullable
    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
        return "medievalworlds:textures/models/armor/gambeson.png";
    }
}
