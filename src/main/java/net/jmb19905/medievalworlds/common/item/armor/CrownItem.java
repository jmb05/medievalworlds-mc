package net.jmb19905.medievalworlds.common.item.armor;

import net.jmb19905.medievalworlds.client.model.armor.CrownHelmetModel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;

public class CrownItem extends ArmorItem {

    public CrownItem(ArmorMaterial material, Properties builderIn) {
        super(material, EquipmentSlot.HEAD, builderIn);
    }

    /*@OnlyIn(Dist.CLIENT)
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
    }*/

    @Nullable
    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
        return "medievalworlds:textures/models/armor/crown.png";
    }

}
