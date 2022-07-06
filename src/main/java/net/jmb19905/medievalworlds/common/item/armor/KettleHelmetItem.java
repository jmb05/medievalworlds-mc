package net.jmb19905.medievalworlds.common.item.armor;

import net.jmb19905.medievalworlds.client.ClientSetup;
import net.jmb19905.medievalworlds.client.model.armor.KettleHelmetModel;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.IItemRenderProperties;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public class KettleHelmetItem extends ArmorItem {
    public KettleHelmetItem(ArmorMaterial material, EquipmentSlot slot, Properties properties) {
        super(material, slot, properties);
    }

    @Override
    public void initializeClient(@NotNull Consumer<IItemRenderProperties> consumer) {
        consumer.accept(new IItemRenderProperties() {
            @Override
            public @NotNull HumanoidModel<?> getArmorModel(LivingEntity entityLiving, ItemStack itemStack, EquipmentSlot armorSlot, HumanoidModel<?> _default) {
                return new KettleHelmetModel(Minecraft.getInstance().getEntityModels().bakeLayer(ClientSetup.KETTLE_HELMET_LAYER));
            }
        });
    }

    @Nullable
    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
        return "medievalworlds:textures/models/armor/steel_kettle_helmet.png";
    }
}
