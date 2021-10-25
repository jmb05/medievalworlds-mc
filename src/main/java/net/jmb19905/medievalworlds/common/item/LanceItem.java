package net.jmb19905.medievalworlds.common.item;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.jmb19905.medievalworlds.common.capability.Motion;
import net.jmb19905.medievalworlds.common.item.lance.EntityMessageToServer;
import net.jmb19905.medievalworlds.common.networking.NetworkStartupCommon;
import net.jmb19905.medievalworlds.util.ConfigHandler;
import net.jmb19905.medievalworlds.util.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.entity.animal.horse.Llama;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class LanceItem extends Item {

    private final float attackDamageFactor;
    private final int fullCharge;

    public LanceItem(float attackDamageFactor, int fullCharge, Properties properties) {
        super(properties);
        this.attackDamageFactor = attackDamageFactor;
        this.fullCharge = fullCharge;
    }

    public float getAttackDamageFactor() {
        return attackDamageFactor;
    }

    public int getFullCharge() {
        return fullCharge;
    }

    @Override
    public int getUseDuration(@Nonnull ItemStack stack) {
        return 72000;
    }

    @Nonnull
    @Override
    public UseAnim getUseAnimation(@Nonnull ItemStack stack) {
        return UseAnim.NONE;
    }

    @Nonnull
    @Override
    public InteractionResultHolder<ItemStack> use(@Nonnull Level level, Player player, @Nonnull InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);
        if(hand == InteractionHand.MAIN_HAND) {
            player.swing(hand);

            return new InteractionResultHolder<>(InteractionResult.SUCCESS, itemStack);
        } else {
            return new InteractionResultHolder<>(InteractionResult.FAIL, itemStack);
        }
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public void onUseTick(@Nonnull Level level, LivingEntity living, @Nonnull ItemStack stack, int useTime) {
        if(living.getRootVehicle() instanceof AbstractHorse && !(living.getRootVehicle() instanceof Llama)) {
            if(living.level.isClientSide) {
                Player player = (Player) living;
                if(!player.getCooldowns().isOnCooldown(this)) {
                    HitResult hitResult = Minecraft.getInstance().hitResult;
                    if(hitResult != null) {
                        if(hitResult.getType().equals(HitResult.Type.ENTITY)) {
                            EntityHitResult entityHitResult = (EntityHitResult) hitResult;
                            sendDataToServer(entityHitResult.getEntity().getId(), useTime >= fullCharge);
                        }
                    }
                }
            } else {
                living.getCapability(Motion.MOTION_CAPABILITY).ifPresent(iMotion -> {
                    Motion motion = (Motion) iMotion;
                    motion.setPrevPos(motion.getPos());
                    motion.setPos(new Vec3(living.getX(), living.getY(), living.getZ()));
                });
            }
        }
    }

    private void sendDataToServer(int entityID, boolean critical){
        EntityMessageToServer message = new EntityMessageToServer(entityID, critical);
        NetworkStartupCommon.simpleChannel.sendToServer(message);
    }

    @Override
    public void appendHoverText(@Nonnull ItemStack stack, @Nullable Level level, List<Component> components, @Nonnull TooltipFlag tooltipFlag) {
        components.add(new TranslatableComponent("tooltip.usedOnHorse"));
        double minimum = roundToHalf(ConfigHandler.COMMON.lanceBaseAttackDamage.get() * 0.237 * attackDamageFactor);
        components.add(new TranslatableComponent("tooltip.minimumAttack").append(new TextComponent(": " + minimum)));
        double maximum = roundToHalf(ConfigHandler.COMMON.lanceBaseAttackDamage.get() * 0.7115 * attackDamageFactor);
        components.add(new TranslatableComponent("tooltip.maximumAttack").append(new TextComponent(": " + maximum)));
        components.add(new TranslatableComponent("tooltip.chargeTime").append(new TextComponent(": " + (fullCharge / 2))));
    }

    private double roundToHalf(double d) {
        return Math.round(d * 2) / 2.0;
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot equipmentSlot, ItemStack stack) {
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        return builder.build();
    }

}