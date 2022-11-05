package net.jmb19905.medievalworlds.common.item;

import net.jmb19905.medievalworlds.MedievalWorlds;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Tiers;

public class MWToolPartMaterial {

    public static final MWToolPartMaterial BRONZE = new MWToolPartMaterial("bronze", MWTiers.BRONZE);
    public static final MWToolPartMaterial IRON = new MWToolPartMaterial("iron", Tiers.IRON);
    public static final MWToolPartMaterial STEEL = new MWToolPartMaterial("steel", MWTiers.STEEL);
    public static final MWToolPartMaterial SILVER = new MWToolPartMaterial("silver", MWTiers.SILVER);
    public static final MWToolPartMaterial GOLD = new MWToolPartMaterial("gold", Tiers.GOLD);
    public static final MWToolPartMaterial NETHERITE = new MWToolPartMaterial("netherite", Tiers.NETHERITE) {
        @Override
        public Item.Properties createProperties(boolean stackable) {
            return super.createProperties(stackable).fireResistant();
        }
    };
    private final String name;
    private final Tier tier;

    public MWToolPartMaterial(String name, Tier tier) {
        this.name = name;
        this.tier = tier;
    }

    public String getName() {
        return name;
    }

    public Tier getTier() {
        return tier;
    }

    public Item.Properties createProperties(boolean stackable) {
        Item.Properties properties = new Item.Properties().tab(MedievalWorlds.materialsTab);
        if (stackable) return properties.stacksTo(16);
        else return properties.durability(tier.getUses());
    }

}