package net.jmb19905.medievalworlds.item.armor;

import net.jmb19905.medievalworlds.MedievalWorlds;
import net.jmb19905.medievalworlds.client.model.armor.KnightArmorHelmetModel;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.common.Mod;

import javax.annotation.Nullable;

@Mod.EventBusSubscriber(modid = MedievalWorlds.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class KnightArmorHelmetItem extends ArmorItem {

    private final String material;

    public KnightArmorHelmetItem(IArmorMaterial materialIn, EquipmentSlotType slot, Properties builder, String material) {
        super(materialIn, slot, builder);
        this.material = material;
    }

    @OnlyIn(Dist.CLIENT)
    @Nullable
    @Override
    public <A extends BipedModel<?>> A getArmorModel(LivingEntity entityLiving, ItemStack itemStack, EquipmentSlotType armorSlot, A _default) {
        BipedModel<LivingEntity> armorModel = new BipedModel<>(1);
        KnightArmorHelmetModel helmetModel = new KnightArmorHelmetModel();
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
        return "medievalworlds:textures/models/armor/"+material+"_knight_helmet.png";
    }

}
