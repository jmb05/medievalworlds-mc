package net.jmb19905.medievalworlds.common.entity;

import net.jmb19905.medievalworlds.common.item.MWArrowItem;
import net.jmb19905.medievalworlds.common.registries.MWEntityTypes;
import net.jmb19905.medievalworlds.common.registries.MWItems;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.RegistryObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

public class MWArrow extends AbstractArrow {

    private static final EntityDataAccessor<String> MATERIAL_DATA = SynchedEntityData.defineId(MWArrow.class, EntityDataSerializers.STRING);

    public MWArrow(EntityType<? extends MWArrow> type, Level level) {
        super(type, level);
    }

    public MWArrow(Level level, double x, double y, double z, String material) {
        super(MWEntityTypes.ARROW.get(), x, y, z, level);
        this.entityData.set(MATERIAL_DATA, material);
    }

    public MWArrow(Level level, LivingEntity living, String material) {
        super(MWEntityTypes.ARROW.get(), living, level);
        this.entityData.set(MATERIAL_DATA, material);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(MATERIAL_DATA, "iron");
    }

    public String getMaterial() {
        return this.entityData.get(MATERIAL_DATA);
    }

    @Override
    protected @NotNull ItemStack getPickupItem() {
        RegistryObject<MWArrowItem> regOb = MWItems.ARROWS.get(getMaterial());
        if (regOb == null) {
            LOGGER.warn("Invalid material for medievalworlds:arrow - " + getMaterial());
            return new ItemStack(MWItems.IRON_ARROW.get());
        }
        return new ItemStack(regOb.get());
    }

    private static final Logger LOGGER = LogManager.getLogger();
}
