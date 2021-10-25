package net.jmb19905.medievalworlds.client.model.armor;


import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;

public class CustomArmorModel extends HumanoidModel<LivingEntity> {
    protected final EquipmentSlot slot;

    public CustomArmorModel(ModelPart part, EquipmentSlot slot) {
        super(part);
        this.slot = slot;
    }
}