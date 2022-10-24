package net.jmb19905.medievalworlds.common.item.silver;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

public interface ISilverWeapon {

    UUID id = UUID.fromString("19303101-03b3-4c00-92bf-3423fadcb922");

    default void manageSilverModifier(Player player, LivingEntity target) {
        AttributeModifier modifier = Objects.requireNonNull(player.getAttribute(Attributes.ATTACK_DAMAGE)).getModifier(id);
        if(target.getMobType() != MobType.UNDEAD && modifier != null) {
            Objects.requireNonNull(player.getAttribute(Attributes.ATTACK_DAMAGE)).removeModifier(id);
        } else if(target.getMobType() == MobType.UNDEAD && modifier == null){
            Objects.requireNonNull(player.getAttribute(Attributes.ATTACK_DAMAGE)).addTransientModifier(new AttributeModifier(id, "Silver Buff", .75, AttributeModifier.Operation.MULTIPLY_TOTAL));
        }
    }

    default void addCleansingTooltip(List<Component> components) {
        components.add(Component.empty());
        components.add(Component.translatable("tooltip.medievalworlds.cleansing"));
    }
}