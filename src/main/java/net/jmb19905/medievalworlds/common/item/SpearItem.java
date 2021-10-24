package net.jmb19905.medievalworlds.common.item;

import com.google.common.collect.Multimap;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.entity.projectile.TridentEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public class SpearItem extends Item {

    private float attackDamage;
    private float attackSpeed;

    public SpearItem(float attackDamage, float attackSpeed, Properties properties) {
        super(properties);
        this.attackDamage = attackDamage;
        this.attackSpeed = attackSpeed;
    }

    /**
     * returns the action that specifies what animation to play when the items is being used
     */
    @Nonnull
    public UseAction getUseAction(@Nonnull ItemStack stack) {
        return UseAction.SPEAR;
    }

    /**
     * How long it takes to use or consume an item
     */
    public int getUseDuration(@Nonnull ItemStack stack) {
        return 72000;
    }

    /**
     * Called when the player stops using an Item (stops holding the right mouse button).
     */
    public void onPlayerStoppedUsing(@Nonnull ItemStack stack, @Nonnull World worldIn, @Nonnull LivingEntity entityLiving, int timeLeft) {
        if (entityLiving instanceof PlayerEntity) {
            PlayerEntity playerentity = (PlayerEntity)entityLiving;
            int i = this.getUseDuration(stack) - timeLeft;
            if (i >= 10) {
                if (!worldIn.isRemote) {
                    stack.damageItem(1, playerentity, (p_220047_1_) -> p_220047_1_.sendBreakAnimation(entityLiving.getActiveHand()));
                    TridentEntity tridententity = new TridentEntity(worldIn, playerentity, stack);
                    Vector3d lookVector = playerentity.getLookVec();
                    tridententity.shoot(lookVector.x, lookVector.y, lookVector.z, 0.0F, 1.0F);
                    if (playerentity.abilities.isCreativeMode) {
                        tridententity.pickupStatus = AbstractArrowEntity.PickupStatus.CREATIVE_ONLY;
                    }

                    worldIn.addEntity(tridententity);
                    //Need to add spear sound
                    //worldIn.playMovingSound(null, tridententity, SoundEvents.ITEM_TRIDENT_THROW, SoundCategory.PLAYERS, 1.0F, 1.0F);
                    if (!playerentity.abilities.isCreativeMode) {
                        playerentity.inventory.deleteStack(stack);
                    }
                }

                playerentity.addStat(Stats.ITEM_USED.get(this));
            }
        }
    }

    /**
     * Called to trigger the item's "innate" right click behavior. To handle when this item is used on a Block, see
     * {@link #onItemUse}.
     */
    @Nonnull
    public ActionResult<ItemStack> onItemRightClick(@Nonnull World worldIn, PlayerEntity playerIn, @Nonnull Hand handIn) {
        ItemStack itemstack = playerIn.getHeldItem(handIn);
        if (itemstack.getDamage() >= itemstack.getMaxDamage() - 1) {
            return ActionResult.resultFail(itemstack);
        }else {
            playerIn.setActiveHand(handIn);
            return ActionResult.resultConsume(itemstack);
        }
    }

    /**
     * Current implementations of this method in child classes do not use the entry argument beside ev. They just raise
     * the damage on the stack.
     */
    public boolean hitEntity(ItemStack stack, @Nonnull LivingEntity target, @Nonnull LivingEntity attacker) {
        stack.damageItem(1, attacker, (living) -> living.sendBreakAnimation(EquipmentSlotType.MAINHAND));
        return true;
    }

    /**
     * Called when a Block is destroyed using this Item. Return true to trigger the "Use Item" statistic.
     */
    public boolean onBlockDestroyed(@Nonnull ItemStack stack, @Nonnull World worldIn, BlockState state, @Nonnull BlockPos pos, @Nonnull LivingEntity entityLiving) {
        if ((double)state.getBlockHardness(worldIn, pos) != 0.0D) {
            stack.damageItem(2, entityLiving, (living) -> living.sendBreakAnimation(EquipmentSlotType.MAINHAND));
        }
        return true;
    }

    /**
     * Gets a map of item attribute modifiers, used by ItemSword to increase hit damage.
     */
    @Nonnull
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(@Nonnull EquipmentSlotType equipmentSlot) {
        Multimap<Attribute, AttributeModifier> multimap = super.getAttributeModifiers(equipmentSlot);
        if (equipmentSlot == EquipmentSlotType.MAINHAND) {
            multimap.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Tool modifier", attackDamage, AttributeModifier.Operation.ADDITION));
            multimap.put(Attributes.ATTACK_SPEED, new AttributeModifier(ATTACK_SPEED_MODIFIER, "Tool modifier", attackSpeed, AttributeModifier.Operation.ADDITION));
        }

        return multimap;
    }

    /**
     * Return the enchantability factor of the item, most of the time is based on material.
     */
    public int getItemEnchantability() {
        return 1;
    }

}
