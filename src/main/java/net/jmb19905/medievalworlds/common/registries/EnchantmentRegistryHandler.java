package net.jmb19905.medievalworlds.common.registries;

import net.jmb19905.medievalworlds.MedievalWorlds;
import net.jmb19905.medievalworlds.common.item.HammerItem;
import net.jmb19905.medievalworlds.common.item.enchantment.LightningStrikeEnchantment;
import net.jmb19905.medievalworlds.common.item.enchantment.MegaMinerEnchantment;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class EnchantmentRegistryHandler {

    public static final EnchantmentCategory HAMMER = EnchantmentCategory.create("hammer", (item) -> item instanceof HammerItem);

    public static final DeferredRegister<Enchantment> ENCHANTMENTS = DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, MedievalWorlds.MOD_ID);

    public static final RegistryObject<Enchantment> LIGHTNING_STRIKE = ENCHANTMENTS.register("lightning_strike", () -> new LightningStrikeEnchantment(Enchantment.Rarity.RARE, HAMMER, new EquipmentSlot[]{EquipmentSlot.MAINHAND}));
    public static final RegistryObject<Enchantment> MEGA_MINER = ENCHANTMENTS.register("mega_miner", () -> new MegaMinerEnchantment(Enchantment.Rarity.RARE, HAMMER, new EquipmentSlot[]{EquipmentSlot.MAINHAND}));

}
