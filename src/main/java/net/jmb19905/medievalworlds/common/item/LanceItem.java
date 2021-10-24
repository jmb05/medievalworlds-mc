package net.jmb19905.medievalworlds.common.item;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.jmb19905.medievalworlds.common.capability.MotionCapability;
import net.jmb19905.medievalworlds.common.item.lance.EntityMessageToServer;
import net.jmb19905.medievalworlds.common.networking.NetworkStartupCommon;
import net.jmb19905.medievalworlds.util.ConfigHandler;
import net.jmb19905.medievalworlds.util.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.passive.horse.AbstractHorseEntity;
import net.minecraft.entity.passive.horse.LlamaEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

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
    public boolean onLeftClickEntity(ItemStack stack, PlayerEntity player, Entity entity) {
        Util.attackWithStack(player, entity, 1);
        return true;
    }

    @Override
    public int getUseDuration(@Nonnull ItemStack stack) {
        return 72000;
    }

    @Override
    @Nonnull
    public UseAction getUseAction(@Nonnull ItemStack stack) {
        return UseAction.NONE;
    }

    @Override
    @Nonnull
    public ActionResult<ItemStack> onItemRightClick(@Nonnull World worldIn, PlayerEntity playerIn,@Nonnull  Hand hand) {
        ItemStack itemStack = playerIn.getHeldItem(hand);
        if(hand == Hand.MAIN_HAND) {
            playerIn.setActiveHand(hand);
            return new ActionResult<>(ActionResultType.SUCCESS, itemStack);
        } else {
            return new ActionResult<>(ActionResultType.FAIL, itemStack);
        }
    }

    @Override
    public void onUsingTick(ItemStack stack, LivingEntity player, int count) {
        if(player.getRidingEntity() instanceof AbstractHorseEntity && !(player.getRidingEntity() instanceof LlamaEntity)) {
            if (player.world.isRemote) {
                PlayerEntity playerEntity = (PlayerEntity) player;
                if(!playerEntity.getCooldownTracker().hasCooldown(this)) {
                    RayTraceResult rayTrace = Minecraft.getInstance().objectMouseOver;
                    if (rayTrace != null) {
                        if (rayTrace.getType().equals(RayTraceResult.Type.ENTITY)) {
                            EntityRayTraceResult entityRayTrace = (EntityRayTraceResult) rayTrace;
                            sendDataToServer(entityRayTrace.getEntity().getEntityId(), count >= fullCharge);
                        }
                    }
                }
            }else {
                player.getCapability(MotionCapability.CAPABILITY_MOTION).ifPresent(motion -> {
                    motion.setPrevPos(motion.getPos());
                    motion.setPos(player.getPositionVec());
                });
            }
        }
    }

    private void sendDataToServer(int entityID, boolean critical){
        EntityMessageToServer message = new EntityMessageToServer(entityID, critical);
        NetworkStartupCommon.simpleChannel.sendToServer(message);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World world, List<ITextComponent> textComponents, ITooltipFlag tooltipFlag) {
        textComponents.add(new TranslationTextComponent("tooltip.usedOnHorse"));
        double minimum = roundToHalf(ConfigHandler.COMMON.lanceBaseAttackDamage.get() * 0.237 * attackDamageFactor);
        textComponents.add(new TranslationTextComponent("tooltip.minimumAttack").append(new StringTextComponent(": " + minimum)));
        double maximum = roundToHalf(ConfigHandler.COMMON.lanceBaseAttackDamage.get() * 0.7115 * attackDamageFactor);
        textComponents.add(new TranslationTextComponent("tooltip.maximumAttack").append(new StringTextComponent(": " + maximum)));
        textComponents.add(new TranslationTextComponent("tooltip.chargeTime").append(new StringTextComponent(": " + (fullCharge / 2))));
    }

    private double roundToHalf(double d) {
        return Math.round(d * 2) / 2.0;
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlotType equipmentSlot, ItemStack stack) {
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();

        /*if (equipmentSlot == EquipmentSlotType.MAINHAND) {
            builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", this.attackDamageFactor, AttributeModifier.Operation.ADDITION));
        }*/
        return builder.build();
    }

}