package net.jmb19905.medievalworlds.common.item.silver;

import net.jmb19905.medievalworlds.common.item.weapon.MWAxeWeapon;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SilverLongAxe extends MWAxeWeapon implements ISilverWeapon {
    public SilverLongAxe(Tier tier, int attackDamage, float attackSpeed, float reachBonus, Properties properties) {
        super(tier, attackDamage, attackSpeed, reachBonus, properties);
    }

    @Override
    public boolean onLeftClickEntity(ItemStack stack, Player player, Entity entity) {
        if(entity instanceof LivingEntity living) manageSilverModifier(player, living);
        return super.onLeftClickEntity(stack, player, entity);
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @Nullable Level level, @NotNull List<Component> components, @NotNull TooltipFlag flag) {
        addCleansingTooltip(components);
    }
}