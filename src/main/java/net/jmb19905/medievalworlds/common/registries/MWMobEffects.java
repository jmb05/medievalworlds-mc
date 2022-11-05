package net.jmb19905.medievalworlds.common.registries;

import net.jmb19905.medievalworlds.MedievalWorlds;
import net.jmb19905.medievalworlds.common.potion.CleansingEffect;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class MWMobEffects {

    public static final DeferredRegister<MobEffect> MOB_EFFECTS =  DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, MedievalWorlds.MOD_ID);

    public static final RegistryObject<MobEffect> CLEANSING = MOB_EFFECTS.register("cleansing", () -> new CleansingEffect(MobEffectCategory.NEUTRAL, 0xffff98));

}
