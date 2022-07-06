package net.jmb19905.medievalworlds.common.item;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

public interface ITwoHandedWeapon {

    UUID id = UUID.fromString("cf71f402-5a64-4841-bba0-34ff35e29f39");

    default void manageTwoHandedModifier(Player player) {
        AttributeModifier modifier = Objects.requireNonNull(player.getAttribute(Attributes.ATTACK_DAMAGE)).getModifier(id);
        if(modifier != null && player.getItemInHand(InteractionHand.OFF_HAND).isEmpty()) {
            Objects.requireNonNull(player.getAttribute(Attributes.ATTACK_DAMAGE)).removeModifier(id);
        } else if(modifier == null && !player.getItemInHand(InteractionHand.OFF_HAND).isEmpty()) {
            Objects.requireNonNull(player.getAttribute(Attributes.ATTACK_DAMAGE)).addTransientModifier(new AttributeModifier(id, "Two-Handed Debuff", -.75, AttributeModifier.Operation.MULTIPLY_TOTAL));
        }
    }

    default void addTwoHandedTooltip(List<Component> components) {
        components.add(new TextComponent(" "));
        if(!Screen.hasShiftDown()) {
            components.add(new TranslatableComponent("tooltip.medievalworlds.twohanded").append(" ").append(new TranslatableComponent("tooltip.medievalworlds.press_shift")));
        } else {
            components.add(new TranslatableComponent("tooltip.medievalworlds.twohanded"));
            components.add(new TranslatableComponent("tooltip.medievalworlds.twohanded_info"));
        }
    }

}
