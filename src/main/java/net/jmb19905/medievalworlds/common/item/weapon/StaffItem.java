package net.jmb19905.medievalworlds.common.item.weapon;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.jmb19905.medievalworlds.MedievalWorlds;
import net.jmb19905.medievalworlds.client.ClientSetup;
import net.jmb19905.medievalworlds.common.item.MWTiers;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public class StaffItem extends MWSwordWeapon {
    private final Multimap<Attribute, AttributeModifier> additionalMainhandModifiers;
    private final Multimap<Attribute, AttributeModifier> additionalOffhandModifiers;

    public StaffItem() {
        this(MWTiers.STURDY_WOOD, 1, -2.4f, 2.5f, new Properties().tab(MedievalWorlds.combatTab).stacksTo(1));
    }

    public StaffItem(Tier tier, int attackDamage, float attackSpeed, float reachBonus, Properties properties) {
        super(tier, attackDamage, attackSpeed, reachBonus, properties);
        ImmutableMultimap.Builder<Attribute, AttributeModifier> mainhandBuilder = ImmutableMultimap.builder();
        mainhandBuilder.put(Attributes.MOVEMENT_SPEED, new AttributeModifier("Walking speed modifier", 0.01, AttributeModifier.Operation.ADDITION));
        mainhandBuilder.putAll(super.getDefaultAttributeModifiers(EquipmentSlot.MAINHAND));
        this.additionalMainhandModifiers = mainhandBuilder.build();
        ImmutableMultimap.Builder<Attribute, AttributeModifier> offhandBuilder = ImmutableMultimap.builder();
        offhandBuilder.put(Attributes.MOVEMENT_SPEED, new AttributeModifier("Walking speed modifier", 0.01, AttributeModifier.Operation.ADDITION));
        offhandBuilder.putAll(super.getDefaultAttributeModifiers(EquipmentSlot.OFFHAND));
        this.additionalOffhandModifiers = offhandBuilder.build();
    }

    @Override
    public @NotNull Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(@NotNull EquipmentSlot slot) {
        if (slot == EquipmentSlot.MAINHAND) {
            return this.additionalMainhandModifiers;
        } else if (slot == EquipmentSlot.OFFHAND) {
            return this.additionalOffhandModifiers;
        } else {
            return super.getDefaultAttributeModifiers(slot);
        }
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, @NotNull Player player, @NotNull InteractionHand hand) {
        player.startUsingItem(hand);
        return new InteractionResultHolder<>(InteractionResult.SUCCESS, player.getItemInHand(hand));
    }

    public @NotNull UseAnim getUseAnimation(@NotNull ItemStack stack) {
        return UseAnim.CUSTOM;
    }

    public int getUseDuration(@NotNull ItemStack stack) {
        return 72000;
    }

    @Override
    public boolean canPerformAction(@NotNull ItemStack stack, net.minecraftforge.common.@NotNull ToolAction toolAction) {
        return net.minecraftforge.common.ToolActions.DEFAULT_SHIELD_ACTIONS.contains(toolAction);
    }

    @Override
    public void initializeClient(@NotNull Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new ClientExtension());
    }

    static class ClientExtension implements IClientItemExtensions {
        @Override
        public HumanoidModel.@Nullable ArmPose getArmPose(LivingEntity entityLiving, InteractionHand hand, ItemStack itemStack) {
            return ClientSetup.CUSTOM;
        }
    }
}