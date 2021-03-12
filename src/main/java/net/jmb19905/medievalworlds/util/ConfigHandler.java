package net.jmb19905.medievalworlds.util;

import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class ConfigHandler {

    public static class Client{
        public Client(ForgeConfigSpec.Builder builder){
            builder.build();
        }
    }

    public static final Client CLIENT;
    public static final ForgeConfigSpec CLIENT_SPEC;
    static {
        final Pair<Client, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(Client::new);
        CLIENT_SPEC = specPair.getRight();
        CLIENT = specPair.getLeft();
    }

    public static class Common{
        public final ForgeConfigSpec.IntValue ironLongswordAttackDamage;
        public final ForgeConfigSpec.IntValue silverLongswordAttackDamage;
        public final ForgeConfigSpec.IntValue steelLongswordAttackDamage;
        public final ForgeConfigSpec.IntValue diamondLongswordAttackDamage;
        public final ForgeConfigSpec.IntValue netheriteLongswordAttackDamage;

        public final ForgeConfigSpec.IntValue lanceBaseAttackDamage;

        //public final ForgeConfigSpec.BooleanValue cheapNetheriteRecipe;

        public Common(ForgeConfigSpec.Builder builder){
            builder.push("weapon specs");
            ironLongswordAttackDamage = builder.comment("Attack Damage of the Iron Longsword").defineInRange("ironLongswordAttackDamage", 10, 0, 100);
            silverLongswordAttackDamage = builder.comment("Attack Damage of the Silver Longsword").defineInRange("silverLongswordAttackDamage", 11, 0, 100);
            steelLongswordAttackDamage = builder.comment("Attack Damage of the Steel Longsword").defineInRange("steelLongswordAttackDamage", 12, 0, 100);
            diamondLongswordAttackDamage = builder.comment("Attack Damage of the Diamond Longsword").defineInRange("diamondLongswordAttackDamage", 13, 0, 100);
            netheriteLongswordAttackDamage = builder.comment("Attack Damage of the Netherite Longsword").defineInRange("netheriteLongswordAttackDamage", 14, 0, 100);
            lanceBaseAttackDamage = builder.comment("Base attack damage of the lance", "Different materials change the base with a factor").defineInRange("lanceBaseAttackDamage", 7, 0, 100);

            //cheapNetheriteRecipe = builder.comment("Use cheap alloy netherite ingot recipe").define("cheapNetheriteRecipe", true);

            builder.pop();
            builder.build();
        }
    }

    public static final Common COMMON;
    public static final ForgeConfigSpec COMMON_SPEC;
    static {
        final Pair<Common, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(Common::new);
        COMMON_SPEC = specPair.getRight();
        COMMON = specPair.getLeft();
    }

}
