package net.jmb19905.medievalworlds.common.item.heated;

import net.jmb19905.medievalworlds.MedievalWorlds;
import net.jmb19905.medievalworlds.common.capability.heat.Heat;
import net.jmb19905.medievalworlds.common.capability.heat.HeatCapProvider;
import net.jmb19905.medievalworlds.common.item.IAnvilItem;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@SuppressWarnings("ConstantConditions")
public class HeatedPlateItem extends Item implements IAnvilItem {

    private final ResourceLocation anvilTexture;
    private final Item basePlate;

    public HeatedPlateItem(Item basePlate, String material, Properties properties) {
        super(properties);
        this.basePlate = basePlate;
        this.anvilTexture = new ResourceLocation(MedievalWorlds.MOD_ID, "textures/entity/anvil/" + material + ".png");
    }

    public Item getBaseItem() {
        return basePlate;
    }

    @Override
    public ResourceLocation getTexture() {
        return anvilTexture;
    }

    @Override
    public String getType() {
        return "plate";
    }

    @Override
    public void onCraftedBy(@Nonnull ItemStack stack, Level level, @Nonnull Player player) {
        if(!level.isClientSide) stack.getCapability(Heat.HEAT_CAPABILITY).ifPresent(heat -> heat.setHeat(1200));
    }

    @Override
    public boolean onEntityItemUpdate(ItemStack stack, ItemEntity entity) {
        Level level = entity.level;
        if(!level.isClientSide) {
            stack.getCapability(Heat.HEAT_CAPABILITY).ifPresent(heat -> {
                int heatVal = heat.getHeat();
                if(heatVal > 0) {
                    heat.setHeat(heatVal - 1);
                }else {
                    ItemStack newItemStack = new ItemStack(getBaseItem(), stack.getCount());
                    ItemEntity newEntity = new ItemEntity(level, entity.getX(), entity.getY(), entity.getZ(), newItemStack);
                    entity.kill();
                    level.addFreshEntity(newEntity);
                }
            });
        }
        return false;
    }

    @Override
    public void inventoryTick(@Nonnull ItemStack stack, @Nonnull Level level, @Nonnull Entity entity, int slot, boolean b) {
        if(!level.isClientSide) {
            stack.getCapability(Heat.HEAT_CAPABILITY).ifPresent(heat -> {
                int heatVal = heat.getHeat();
                if(heatVal > 0) {
                    heat.setHeat(heatVal - 1);
                }else if(heatVal == -1){
                    heat.setHeat(200);
                }else {
                    if(entity instanceof Player player) {
                        player.getInventory().setItem(slot, new ItemStack(getBaseItem(), stack.getCount()));
                        Vec3 soundPos = entity.getEyePosition();
                        level.playSound(null, soundPos.x, soundPos.y, soundPos.z, SoundEvents.FIRE_EXTINGUISH, SoundSource.PLAYERS, 1.0F, (1.0F + level.getRandom().nextFloat() * 0.2F) * 0.7F);
                    }
                }
            });
        }
    }

    @Nullable
    @Override
    public CompoundTag getShareTag(ItemStack stack) {
        CompoundTag nbt = stack.getOrCreateTag();
        Heat heat = (Heat) stack.getCapability(Heat.HEAT_CAPABILITY).orElseThrow(() -> new IllegalStateException("Heated Ingot had no heat Capability"));
        nbt.put("heat", heat.serializeNBT());
        return nbt;
    }

    @Override
    public void readShareTag(ItemStack stack, @Nullable CompoundTag nbt) {
        if(nbt != null) {
            CompoundTag heatTag = nbt.getCompound("heat");
            Heat heat = (Heat) stack.getCapability(Heat.HEAT_CAPABILITY).orElseThrow(() -> new IllegalStateException("Heated Ingot had no heat Capability"));
            heat.deserializeNBT(heatTag);
        }
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
        return new HeatCapProvider();
    }

}
