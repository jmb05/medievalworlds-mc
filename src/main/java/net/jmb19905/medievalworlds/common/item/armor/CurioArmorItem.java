package net.jmb19905.medievalworlds.common.item.armor;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.CuriosCapability;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;

public class CurioArmorItem extends ArmorItem implements ICurioItem {

    protected final Multimap<Attribute, AttributeModifier> curioModifiers;

    protected final String curioSlot;

    public CurioArmorItem(ArmorMaterial material, EquipmentSlot slot, Properties properties) {
        super(material, slot, properties);
        curioSlot = getCurioSlot(slot);
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        UUID attributeId = UUID.randomUUID();
        builder.put(Attributes.ARMOR, new AttributeModifier(attributeId, "Curio Armor modifier", this.getDefense(), AttributeModifier.Operation.ADDITION));
        builder.put(Attributes.ARMOR_TOUGHNESS, new AttributeModifier(attributeId, "Curio Armor toughness", this.getToughness(), AttributeModifier.Operation.ADDITION));
        if (this.getMaterial().getKnockbackResistance() > 0) {
            builder.put(Attributes.KNOCKBACK_RESISTANCE, new AttributeModifier(attributeId, "Curio Armor knockback resistance", this.getMaterial().getKnockbackResistance(), AttributeModifier.Operation.ADDITION));
        }
        curioModifiers = builder.build();
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
        AtomicBoolean flag = new AtomicBoolean(false);
        CuriosApi.getSlotHelper().getSlotType(curioSlot).ifPresent(
                slotType -> {
                    if(slotContext.entity() != null) {
                        slotContext.entity().getCapability(CuriosCapability.INVENTORY).ifPresent(
                                inv -> inv.getStacksHandler(curioSlot).ifPresent(
                                        stacksHandler -> flag.set(true)
                                )
                        );
                    }
                }
        );
        return flag.get() ? curioModifiers : ICurioItem.super.getAttributeModifiers(slotContext, uuid, stack);
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        if(slotContext.entity() instanceof Player player) {
            if (stack.getCount() > 0 && slotContext.entity().getItemBySlot(slot).getItem() == stack.getItem()) {
                stack.shrink(1);
                player.drop(stack, true);
            }
        }
    }

    @Override
    public boolean canEquip(SlotContext slotContext, ItemStack stack) {
        return slotContext.entity().getItemBySlot(slot).getItem() != stack.getItem();
    }

    private static String getCurioSlot(EquipmentSlot slot) {
        String curioSlot = "";
        switch (slot) {
            case HEAD -> curioSlot = "head";
            case CHEST -> curioSlot = "body";
            case LEGS -> curioSlot = "legs";
            case FEET -> curioSlot = "feet";
        }
        if(curioSlot.isBlank()) throw new IllegalStateException("Unknown Armor Slot");
        return curioSlot;
    }

}
