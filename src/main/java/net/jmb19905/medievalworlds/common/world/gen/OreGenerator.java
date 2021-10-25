package net.jmb19905.medievalworlds.common.world.gen;

import com.google.common.collect.ImmutableList;
import net.jmb19905.medievalworlds.MedievalWorlds;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.ReplaceBlockConfiguration;
import net.minecraftforge.common.world.BiomeGenerationSettingsBuilder;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.ArrayList;

import static net.minecraft.data.worldgen.Features.*;

public class OreGenerator {

    public static ImmutableList<OreConfiguration.TargetBlockState> ORE_TARGET_LIST;
    public static final ArrayList<ConfiguredFeature<?, ?>> overworldOres = new ArrayList<>();

    public static void registerOres() {
        ORE_TARGET_LIST = ImmutableList.of(OreConfiguration.target(OreConfiguration.Predicates.NATURAL_STONE, Blocks.STONE.defaultBlockState()));
        overworldOres.add(register("tin_ore", Feature.ORE.configured(new OreConfiguration(ORE_COPPER_TARGET_LIST, 7))
                .rangeTriangle(VerticalAnchor.absolute(0), VerticalAnchor.absolute(100))
                .squared()
                .count(3)
        ));
        overworldOres.add(register("silver_ore", Feature.ORE.configured(new OreConfiguration(ORE_GOLD_TARGET_LIST, 11))
                .rangeUniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(31))
                .squared()
                .count(3)
        ));
        overworldOres.add(register("ruby_ore", Feature.REPLACE_SINGLE_BLOCK.configured(new ReplaceBlockConfiguration(ORE_EMERALD_TARGET_LIST))
                .rangeUniform(VerticalAnchor.absolute(4), VerticalAnchor.absolute(31))
                .squared()
                .count(UniformInt.of(3, 8))
        ));
    }

    private static <FC extends FeatureConfiguration> ConfiguredFeature<FC,?> register(String name, ConfiguredFeature<FC,?> configuredFeature) {
        return Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, new ResourceLocation(MedievalWorlds.MOD_ID, name), configuredFeature);
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void registerBiomeModification(final BiomeLoadingEvent evt){
        BiomeGenerationSettingsBuilder generation = evt.getGeneration();
        for(ConfiguredFeature<?,?> ore : overworldOres) {
            if(ore != null) {
                generation.getFeatures(GenerationStep.Decoration.UNDERGROUND_ORES).add(() -> ore);
            }
        }
    }

}
