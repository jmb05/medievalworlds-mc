package net.jmb19905.medievalworlds.registries;

import net.jmb19905.medievalworlds.MedievalWorlds;
import net.jmb19905.medievalworlds.item.HammerItem;
import net.jmb19905.medievalworlds.item.enchantment.LightningStrikeEnchantment;
import net.jmb19905.medievalworlds.item.enchantment.MegaMinerEnchantment;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class EnchantmentRegistryHandler {

    public static final EnchantmentType HAMMER = EnchantmentType.create("hammer", (item) -> item instanceof HammerItem);

    public static final DeferredRegister<Enchantment> ENCHANTMENTS = DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, MedievalWorlds.MOD_ID);

    public static final RegistryObject<Enchantment> LIGHTNING_STRIKE = ENCHANTMENTS.register("lightning_strike", () -> new LightningStrikeEnchantment(Enchantment.Rarity.RARE, HAMMER, new EquipmentSlotType[]{EquipmentSlotType.MAINHAND}));
    public static final RegistryObject<Enchantment> MEGA_MINER = ENCHANTMENTS.register("mega_miner", () -> new MegaMinerEnchantment(Enchantment.Rarity.RARE, HAMMER, new EquipmentSlotType[]{EquipmentSlotType.MAINHAND}));

}
