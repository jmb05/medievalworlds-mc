package net.jmb19905.medievalworlds.common.item.armor;

import net.jmb19905.medievalworlds.client.ClientSetup;
import net.jmb19905.medievalworlds.client.renderers.armor.KnightArmorHelmetModel;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public class KnightArmorHelmetItem extends AbstractKnightArmorItem {

    public KnightArmorHelmetItem(ArmorMaterial material, Properties properties, String materialString) {
        super(material, EquipmentSlot.HEAD, properties, materialString);
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            @Override
            public @NotNull HumanoidModel<?> getHumanoidArmorModel(LivingEntity livingEntity, ItemStack itemStack, EquipmentSlot equipmentSlot, HumanoidModel<?> original) {
                return new KnightArmorHelmetModel(Minecraft.getInstance().getEntityModels().bakeLayer(ClientSetup.KNIGHT_HELMET_LAYER));
            }
        });
    }

    @Override
    public @Nullable String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
        return "medievalworlds:textures/models/armor/" + materialString + "_knight_helmet.png";
    }
}
