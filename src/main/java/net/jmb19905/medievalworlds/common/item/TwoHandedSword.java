package net.jmb19905.medievalworlds.common.item;

import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public class TwoHandedSword extends SwordItem implements ITwoHandedWeapon {
    public TwoHandedSword(Tier tier, int attackDamage, float attackSpeed, Properties properties) {
        super(tier, attackDamage, attackSpeed, properties);
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, @NotNull Player player, @NotNull InteractionHand hand) {
        EntityHitResult hitResult = getEntityHitResult(player);
        if(hitResult != null) System.out.println(hitResult.getEntity());
        return InteractionResultHolder.success(player.getItemInHand(hand));
    }

    @Override
    public boolean onLeftClickEntity(ItemStack stack, Player player, Entity entity) {
        manageTwoHandedModifier(player);
        return super.onLeftClickEntity(stack, player, entity);
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @Nullable Level level, @NotNull List<Component> components, @NotNull TooltipFlag flag) {
        addTwoHandedTooltip(components);
    }

    public static EntityHitResult getEntityHitResult(Player player) {
        Level level = player.level;
        Vec3 eyePos = player.getEyePosition();
        Vec3 scaledViewVec = player.getViewVector(1.0F).scale(8);
        Vec3 sum = eyePos.add(scaledViewVec);
        AABB box = player.getBoundingBox().expandTowards(scaledViewVec).inflate(1.0D);
        double d0 = 64;
        Entity entity = null;
        Vec3 vec3 = null;

        for(Entity entity1 : level.getEntities(player, box, entity2 -> true)) {
            AABB aabb = entity1.getBoundingBox().inflate(entity1.getPickRadius());
            Optional<Vec3> optional = aabb.clip(eyePos, sum);
            if (aabb.contains(eyePos)) {
                if (d0 >= 0.0D) {
                    entity = entity1;
                    vec3 = optional.orElse(eyePos);
                    d0 = 0.0D;
                }
            } else if (optional.isPresent()) {
                Vec3 vec31 = optional.get();
                double d1 = eyePos.distanceToSqr(vec31);
                if (d1 < d0 || d0 == 0.0D) {
                    if (entity1.getRootVehicle() == player.getRootVehicle() && !entity1.canRiderInteract()) {
                        if (d0 == 0.0D) {
                            entity = entity1;
                            vec3 = vec31;
                        }
                    } else {
                        entity = entity1;
                        vec3 = vec31;
                        d0 = d1;
                    }
                }
            }
        }

        return entity == null ? null : new EntityHitResult(entity, vec3);
    }

}