package net.jmb19905.medievalworlds.common.item.armor;

import net.jmb19905.medievalworlds.MedievalWorlds;
import net.jmb19905.medievalworlds.client.model.armor.KnightArmorHelmetModel;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.IItemRenderProperties;

import javax.annotation.Nullable;
import java.util.function.Consumer;

public class KnightArmorHelmetItem extends ArmorItem {

    private final String material;

    public KnightArmorHelmetItem(ArmorMaterial materialIn, EquipmentSlot slot, Properties builder, String material) {
        super(materialIn, slot, builder);
        this.material = material;
    }

    @Override
    public void initializeClient(Consumer<IItemRenderProperties> consumer) {
        consumer.accept(new IItemRenderProperties() {
            @SuppressWarnings("unchecked")
            @Override
            public <A extends HumanoidModel<?>> A getArmorModel(LivingEntity entityLiving, ItemStack itemStack, EquipmentSlot armorSlot, A _default) {
                KnightArmorHelmetModel model = new KnightArmorHelmetModel(Minecraft.getInstance().getEntityModels().bakeLayer(MedievalWorlds.ClientSetup.KNIGHT_HELMET_LAYER));
                return (A) model;
            }
        });
    }

    @Nullable
    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
        return "medievalworlds:textures/models/armor/"+material+"_knight_helmet.png";
    }

}
