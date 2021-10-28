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
public class HeatedIngotItem extends Item implements IAnvilItem {

    private final Item baseIngot;
    private final ResourceLocation anvilIngotTexture;

    public HeatedIngotItem(Item baseIngot, String material, Properties properties) {
        super(properties);
        this.baseIngot = baseIngot;
        this.anvilIngotTexture = new ResourceLocation(MedievalWorlds.MOD_ID, "textures/entity/anvil/" + material + ".png");
    }

    public Item getBaseItem() {
        return baseIngot;
    }

    @Override
    public ResourceLocation getTexture() {
        return anvilIngotTexture;
    }

    @Override
    public String getType() {
        return "ingot";
    }

    @Override
    public void onCraftedBy(@Nonnull ItemStack stack, @Nonnull Level level, @Nonnull Player player) {
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
                    ItemStack newItemStack = new ItemStack(((HeatedIngotItem) stack.getItem()).getBaseItem(), stack.getCount());
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

    /*@Nullable
    @Override
    public CompoundTag getShareTag(ItemStack stack) {
        CompoundTag nbt = stack.getOrCreateTag();
        CustomItemHandler itemHandler = (CustomItemHandler) stack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
                .orElseThrow(() -> new IllegalStateException("Heated Ingot had no ItemHandler Capability"));
        ContainerHelper.saveAllItems(nbt, itemHandler.toNonNullList());
        return nbt;
    }

    @Override
    public void readShareTag(ItemStack stack, @Nullable CompoundTag nbt) {
        if(nbt != null) {
            stack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(itemHandler -> {
                NonNullList<ItemStack> inventoryAsList = NonNullList.withSize(itemHandler.getSlots(), ItemStack.EMPTY);
                ContainerHelper.loadAllItems(nbt, inventoryAsList);
                ((CustomItemHandler) itemHandler).setNonNullList(inventoryAsList);
            });
        }
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
        return new ICapabilityProvider() {
            private CustomItemHandler inventory;

            @Nonnull
            @Override
            public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
                if(cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
                    if(inventory == null){
                        inventory = new CustomItemHandler(1);
                    }
                    System.out.println("Got ItemStack Capability: " + inventory);
                    return LazyOptional.of(() -> inventory).cast();
                }
                return LazyOptional.empty();
            }
        };
    }*/

}
