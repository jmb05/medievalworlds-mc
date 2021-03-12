package net.jmb19905.medievalworlds.item;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.jmb19905.medievalworlds.item.lance.EntityMessageToServer;
import net.jmb19905.medievalworlds.networking.NetworkStartupCommon;
import net.jmb19905.medievalworlds.util.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.passive.horse.AbstractHorseEntity;
import net.minecraft.entity.passive.horse.LlamaEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public class LanceItem extends Item {

    private final int attackDamageFactor;
    private final int fullCharge = 20;

    public LanceItem(int attackDamageFactor, Properties properties) {
        super(properties);
        this.attackDamageFactor = attackDamageFactor;
    }

    public int getAttackDamageFactor() {
        return attackDamageFactor;
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
        ItemStack itemStackIn = playerIn.getHeldItem(hand);
        playerIn.setActiveHand(hand);
        return new ActionResult<>(ActionResultType.SUCCESS, itemStackIn);
    }

    @Override
    public void onUsingTick(ItemStack stack, LivingEntity player, int count) {
        if(player.getRidingEntity() instanceof AbstractHorseEntity && !(player.getRidingEntity() instanceof LlamaEntity)) {
            if (player.world.isRemote) {
                RayTraceResult rayTrace = Minecraft.getInstance().objectMouseOver;
                if (rayTrace != null) {
                    if (rayTrace.getType().equals(RayTraceResult.Type.ENTITY)) {
                        EntityRayTraceResult entityRayTrace = (EntityRayTraceResult) rayTrace;
                        sendEntityIDtoServer(entityRayTrace.getEntity().getEntityId());
                    }
                }
            }
        }
    }

    private void sendEntityIDtoServer(int entityID){
        EntityMessageToServer message = new EntityMessageToServer(entityID, attackDamageFactor >= 1);
        NetworkStartupCommon.simpleChannel.sendToServer(message);
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