package net.jmb19905.medievalworlds.common.item.armor;

import net.jmb19905.medievalworlds.client.ClientSetup;
import net.jmb19905.medievalworlds.client.renderers.armor.GambesonModel;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public class GambesonChestplateItem extends ArmorItem {
    public GambesonChestplateItem(ArmorMaterial material, Properties properties) {
        super(material, EquipmentSlot.CHEST, properties);
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            @Override
            public @NotNull HumanoidModel<?> getHumanoidArmorModel(LivingEntity livingEntity, ItemStack itemStack, EquipmentSlot equipmentSlot, HumanoidModel<?> original) {
                return new GambesonModel(Minecraft.getInstance().getEntityModels().bakeLayer(ClientSetup.GAMBESON_LAYER));
            }
        });
    }

    @Override
    public @Nullable String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
        return "medievalworlds:textures/models/armor/gambeson.png";
    }
}
