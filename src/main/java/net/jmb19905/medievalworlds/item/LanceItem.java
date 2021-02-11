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
import net.minecraft.entity.ai.attributes.Attributes;
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

    private final int attackDamage;
    private final int fullCharge = 20;

    public LanceItem(int attackDamage, Properties properties) {
        super(properties);
        this.attackDamage = attackDamage;
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
    public void onPlayerStoppedUsing(@Nonnull ItemStack stack, World worldIn,@Nonnull  LivingEntity entityLiving, int timeLeft) {
        if(worldIn.isRemote){
            RayTraceResult rayTrace = Minecraft.getInstance().objectMouseOver;
            if(rayTrace != null) {
                if (rayTrace.getType().equals(RayTraceResult.Type.ENTITY)) {
                    float percentage = ((float) (getUseDuration(stack) - timeLeft))/fullCharge;
                    if(percentage > 0.1f) {
                        if(percentage > 1){
                            percentage = 1;
                        }
                        EntityRayTraceResult entityRayTrace = (EntityRayTraceResult) rayTrace;
                        sendEntityIDtoServer(entityRayTrace.getEntity().getEntityId(), percentage);
                    }
                }
            }
        }
    }

    private void sendEntityIDtoServer(int entityID, float attackDamageFactor){
        EntityMessageToServer message = new EntityMessageToServer(entityID, (int) (attackDamage * attackDamageFactor), attackDamageFactor >= 1);
        NetworkStartupCommon.simpleChannel.sendToServer(message);
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlotType equipmentSlot, ItemStack stack) {
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();

        if (equipmentSlot == EquipmentSlotType.MAINHAND) {
            builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", this.attackDamage, AttributeModifier.Operation.ADDITION));
        }
        return builder.build();
    }

}