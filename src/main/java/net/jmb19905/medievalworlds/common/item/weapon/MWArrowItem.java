package net.jmb19905.medievalworlds.common.item.weapon;

import net.jmb19905.medievalworlds.common.entity.MWArrow;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class MWArrowItem extends ArrowItem {
    private final int damageAddition;
    private final String material;

    public MWArrowItem(int damageAddition, String material, Properties properties) {
        super(properties);
        this.damageAddition = damageAddition;
        this.material = material;
    }

    @Override
    public boolean isInfinite(@NotNull ItemStack stack, @NotNull ItemStack bow, @NotNull Player player) {
        return false;
    }

    @Override
    public @NotNull AbstractArrow createArrow(@NotNull Level level, @NotNull ItemStack stack, @NotNull LivingEntity living) {
        MWArrow arrow = new MWArrow(level, living, material);
        arrow.setBaseDamage(arrow.getBaseDamage() + damageAddition);
        return arrow;
    }
}
