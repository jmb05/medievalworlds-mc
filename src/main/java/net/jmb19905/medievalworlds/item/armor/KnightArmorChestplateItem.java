package net.jmb19905.medievalworlds.item.armor;

import net.minecraft.entity.Entity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.ItemStack;

import javax.annotation.Nullable;

public class KnightArmorChestplateItem extends ArmorItem {

    private final String material;

    public KnightArmorChestplateItem(IArmorMaterial materialIn, EquipmentSlotType slot, Properties builder, String material) {
        super(materialIn, slot, builder);
        this.material = material;
    }

    @Nullable
    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type) {
        return "medievalworlds:textures/models/armor/"+material+"_knight_layer_1.png";
    }

    /*@Nullable
    @Override
    public <A extends BipedModel<?>> A getArmorModel(LivingEntity entityLiving, ItemStack itemStack, EquipmentSlotType armorSlot, A _default) {
        BipedModel<LivingEntity> armorModel = new BipedModel<>(1);
        KnightArmorChestplateModel chestplateModel = new KnightArmorChestplateModel();

        armorModel.bipedBody = chestplateModel.body;
        armorModel.bipedLeftArm = chestplateModel.armL;
        armorModel.bipedRightArm = chestplateModel.armR;

        armorModel.isSneak = entityLiving.isSneaking();
        armorModel.isSitting = _default.isSitting;
        armorModel.isChild = entityLiving.isChild();

        return (A) armorModel;
    }*/
}
