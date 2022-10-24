package net.jmb19905.medievalworlds.common.item;

import net.jmb19905.medievalworlds.MedievalWorlds;
import net.jmb19905.medievalworlds.client.renderers.curio.QuiverRenderer;
import net.jmb19905.medievalworlds.common.capability.MWCapabilityManager;
import net.jmb19905.medievalworlds.common.capability.QuiverInvProvider;
import net.jmb19905.medievalworlds.common.capability.quiverInv.QuiverInv;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.inventory.tooltip.BundleTooltip;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.theillusivec4.curios.api.client.CuriosRendererRegistry;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.util.List;
import java.util.Optional;

public class QuiverItem extends Item implements ICurioItem {

    private static final String CAP_TAG_NAME = "QuiverInvTag";
    private static final String BASE_TAG_NAME = "QuiverBaseTag";

    private static final int BAR_COLOR = Mth.color(0.4F, 0.4F, 1.0F);
    private final int capacity;

    public QuiverItem(int capacity, Properties properties) {
        super(properties);
        this.capacity = capacity;
        CuriosRendererRegistry.register(this, QuiverRenderer::new);
    }

    @Override
    public @Nullable CompoundTag getShareTag(ItemStack stack) {
        CompoundTag tag = stack.getTag();
        QuiverInv inv = MWCapabilityManager.retrieveCapability(stack, QuiverInv.QUIVER_INV_CAPABILITY);
        CompoundTag combinedTag = new CompoundTag();
        if (tag != null) {
            combinedTag.put(BASE_TAG_NAME, tag);
        }
        if (inv != null) {
            CompoundTag capTag = inv.serializeNBT();
            if (capTag == null) capTag = new CompoundTag();
            combinedTag.put(CAP_TAG_NAME, capTag);
        }
        return combinedTag;
    }

    @Override
    public void readShareTag(ItemStack stack, @Nullable CompoundTag nbt) {
        if (nbt == null) {
            stack.setTag(null);
            return;
        }

        CompoundTag baseTag = nbt.getCompound(BASE_TAG_NAME);
        stack.setTag(baseTag);

        CompoundTag capTag = nbt.getCompound(CAP_TAG_NAME);
        QuiverInv inv = MWCapabilityManager.retrieveCapability(stack, QuiverInv.QUIVER_INV_CAPABILITY);
        if (inv != null) inv.deserializeNBT(capTag);
    }

    @Override
    public @Nullable ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
        return new QuiverInvProvider(capacity);
    }

    @Override
    public boolean overrideStackedOnOther(@NotNull ItemStack quiverStack, @NotNull Slot slot, @NotNull ClickAction clickAction, @NotNull Player player) {
        if(clickAction != ClickAction.SECONDARY) {
            return false;
        } else {
            ItemStack slotStack = slot.getItem();
            QuiverInv inv = MWCapabilityManager.retrieveCapability(quiverStack, QuiverInv.QUIVER_INV_CAPABILITY);
            if (inv == null) return false;
            if (slotStack.isEmpty() && inv.getFillLevel() > 0) {
                this.playRemoveOneSound(player);
                inv.removeOne().ifPresent(slot::safeInsert);
            } else if (slotStack.getItem().canFitInsideContainerItems() && slotStack.getItem() instanceof ArrowItem) {
                ItemStack addStack = slot.safeTake(slotStack.getCount(), inv.getFreeSpace(slotStack), player);
                int addCount = inv.add(addStack);
                if (addCount > 0) {
                    this.playInsertSound(player);
                }
            }
            return true;
        }
    }

    @Override
    public boolean overrideOtherStackedOnMe(@NotNull ItemStack quiverStack, @NotNull ItemStack otherStack, @NotNull Slot slot, @NotNull ClickAction clickAction, @NotNull Player player, @NotNull SlotAccess slotAccess) {
        if (clickAction == ClickAction.SECONDARY && slot.allowModification(player)) {
            QuiverInv inv = MWCapabilityManager.retrieveCapability(quiverStack, QuiverInv.QUIVER_INV_CAPABILITY);
            if (inv == null) return false;

            if (otherStack.isEmpty()) {
                inv.removeOne().ifPresent(arrowStack -> {
                    this.playRemoveOneSound(player);
                    slotAccess.set(arrowStack);
                });
            } else {
                int addCount = inv.add(otherStack);
                if (addCount > 0) {
                    this.playInsertSound(player);
                    otherStack.shrink(addCount);
                }
            }
            return true;
        } else {
            return false;
        }
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, @NotNull Player player, @NotNull InteractionHand hand) {
        ItemStack quiverStack = player.getItemInHand(hand);
        QuiverInv inv = MWCapabilityManager.retrieveCapability(quiverStack, QuiverInv.QUIVER_INV_CAPABILITY);
        if (inv != null && inv.dropAll(player)) {
            this.playDropContentsSound(player);
            player.awardStat(Stats.ITEM_USED.get(this));
            return InteractionResultHolder.sidedSuccess(quiverStack, level.isClientSide);
        }
        return InteractionResultHolder.fail(quiverStack);
    }

    public @NotNull Optional<TooltipComponent> getTooltipImage(@NotNull ItemStack quiverStack) {
        if (!Minecraft.getInstance().hasSingleplayerServer()) return Optional.empty();
        QuiverInv inv = MWCapabilityManager.retrieveCapability(quiverStack, QuiverInv.QUIVER_INV_CAPABILITY);
        if (inv == null) return Optional.empty();
        return Optional.of(new BundleTooltip(inv.toNonNullList(), 1));
    }

    public void appendHoverText(@NotNull ItemStack quiverStack, @Nullable Level level, @NotNull List<Component> components, @NotNull TooltipFlag flag) {
        if (level != null && level.isClientSide && !Minecraft.getInstance().hasSingleplayerServer()) {
            components.add(Component.translatable("item." + MedievalWorlds.MOD_ID + ".quiver.creative_broken").withStyle(ChatFormatting.RED));
            return;
        }
        QuiverInv inv = MWCapabilityManager.retrieveCapability(quiverStack, QuiverInv.QUIVER_INV_CAPABILITY);
        if (inv != null) components.add(Component.translatable("item." + MedievalWorlds.MOD_ID + ".quiver.fullness", (int) (inv.getFillLevel() * (this.capacity * 64)), this.capacity * 64).withStyle(ChatFormatting.GRAY));
    }

    public void onDestroyed(@NotNull ItemEntity quiverItemEntity) {
        ItemStack quiverStack = quiverItemEntity.getItem();
        QuiverInv inv = MWCapabilityManager.retrieveCapability(quiverStack, QuiverInv.QUIVER_INV_CAPABILITY);
        if (inv != null) {
            ItemUtils.onContainerDestroyed(quiverItemEntity, inv.toNonNullList().stream());
        }
    }

    public boolean isBarVisible(@NotNull ItemStack quiverStack) {
        QuiverInv inv = MWCapabilityManager.retrieveCapability(quiverStack, QuiverInv.QUIVER_INV_CAPABILITY);
        if (inv == null) return false;
        return inv.getFillLevel() > 0;
    }

    public int getBarWidth(@NotNull ItemStack quiverStack) {
        QuiverInv inv = MWCapabilityManager.retrieveCapability(quiverStack, QuiverInv.QUIVER_INV_CAPABILITY);
        if (inv == null) return 1;
        return (int) Math.min(1 + 12 * inv.getFillLevel(), 13);
    }

    public int getBarColor(@NotNull ItemStack quiverStack) {
        return BAR_COLOR;
    }

    private void playRemoveOneSound(Entity entity) {
        entity.playSound(SoundEvents.BUNDLE_REMOVE_ONE, 0.8F, 0.8F + entity.getLevel().getRandom().nextFloat() * 0.4F);
    }

    private void playInsertSound(Entity entity) {
        entity.playSound(SoundEvents.BUNDLE_INSERT, 0.8F, 0.8F + entity.getLevel().getRandom().nextFloat() * 0.4F);
    }

    private void playDropContentsSound(Entity entity) {
        entity.playSound(SoundEvents.BUNDLE_DROP_CONTENTS, 0.8F, 0.8F + entity.getLevel().getRandom().nextFloat() * 0.4F);
    }

}
