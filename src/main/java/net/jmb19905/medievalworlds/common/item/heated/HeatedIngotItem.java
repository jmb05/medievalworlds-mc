package net.jmb19905.medievalworlds.common.item.heated;

import net.jmb19905.medievalworlds.MedievalWorlds;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

public class HeatedIngotItem extends AbstractHeatedItem {

    private final ResourceLocation anvilIngotTexture;

    public HeatedIngotItem(Item baseIngot, String material, Properties properties) {
        super(baseIngot, properties);
        this.anvilIngotTexture = new ResourceLocation(MedievalWorlds.MOD_ID, "textures/entity/anvil/" + material + ".png");
    }

    @Override
    public ResourceLocation getTexture() {
        return anvilIngotTexture;
    }

    @Override
    public String getType() {
        return "ingot";
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
