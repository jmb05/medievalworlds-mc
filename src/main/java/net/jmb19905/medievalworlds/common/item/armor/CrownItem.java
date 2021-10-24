package net.jmb19905.medievalworlds.common.item.armor;

import net.jmb19905.medievalworlds.client.model.armor.CrownHelmetModel;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;

public class CrownItem extends ArmorItem {

    public CrownItem(IArmorMaterial material, Properties builderIn) {
        super(material, EquipmentSlotType.HEAD, builderIn);
    }

    @OnlyIn(Dist.CLIENT)
    @Nullable
    @Override
    public <A extends BipedModel<?>> A getArmorModel(LivingEntity entityLiving, ItemStack itemStack, EquipmentSlotType armorSlot, A _default) {
        BipedModel<LivingEntity> armorModel = new BipedModel<>(1);
        CrownHelmetModel helmetModel = new CrownHelmetModel();
        armorModel.bipedHead = helmetModel.Helmet;
        armorModel.bipedHeadwear = helmetModel.Overlay;

        armorModel.isSneak = entityLiving.isSneaking();
        armorModel.isSitting = _default.isSitting;
        armorModel.isChild = entityLiving.isChild();

        return (A) armorModel;
    }

    @Nullable
    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type) {
        return "medievalworlds:textures/models/armor/crown.png";
    }

}
