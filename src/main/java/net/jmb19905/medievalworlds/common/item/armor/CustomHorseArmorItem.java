package net.jmb19905.medievalworlds.common.item.armor;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.HorseArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class CustomHorseArmorItem extends HorseArmorItem {

    public CustomHorseArmorItem(int prot, String material, Properties properties) {
        super(prot, material, properties);
    }

    public CustomHorseArmorItem(int prot, ResourceLocation texture, Properties properties) {
        super(prot, texture, properties);
    }

    @Override
    public void appendHoverText(@Nonnull ItemStack stack, @Nullable Level level, @Nonnull List<Component> components, @Nonnull TooltipFlag flags) {
        components.add(new TranslatableComponent("tooltip.usedOnHorse"));
        components.add(new TranslatableComponent("tooltip.protection").append("" + getProtection()));
    }
}
