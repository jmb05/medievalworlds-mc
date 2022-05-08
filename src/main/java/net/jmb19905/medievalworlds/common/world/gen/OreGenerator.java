package net.jmb19905.medievalworlds.common.world.gen;

import com.google.common.collect.ImmutableList;
import net.jmb19905.medievalworlds.core.registries.MWBlocks;
import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.features.OreFeatures;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.placement.*;
import net.minecraftforge.common.world.BiomeGenerationSettingsBuilder;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.ArrayList;
import java.util.List;

public class OreGenerator {

    public static final ArrayList<Holder<PlacedFeature>> overworldOres = new ArrayList<>();
    public static final ArrayList<Holder<PlacedFeature>> mountainOres = new ArrayList<>();

    public static final ImmutableList<OreConfiguration.TargetBlockState> ORE_RUBY_TARGET_LIST = ImmutableList.of(OreConfiguration.target(OreFeatures.STONE_ORE_REPLACEABLES, MWBlocks.RUBY_ORE_BLOCK.get().defaultBlockState()), OreConfiguration.target(OreFeatures.DEEPSLATE_ORE_REPLACEABLES, MWBlocks.DEEPSLATE_RUBY_ORE_BLOCK.get().defaultBlockState()));
    public static final ImmutableList<OreConfiguration.TargetBlockState> ORE_SILVER_TARGET_LIST = ImmutableList.of(OreConfiguration.target(OreFeatures.STONE_ORE_REPLACEABLES, MWBlocks.SILVER_ORE_BLOCK.get().defaultBlockState()), OreConfiguration.target(OreFeatures.DEEPSLATE_ORE_REPLACEABLES, MWBlocks.DEEPSLATE_SILVER_ORE_BLOCK.get().defaultBlockState()));
    public static final ImmutableList<OreConfiguration.TargetBlockState> ORE_TIN_TARGET_LIST = ImmutableList.of(OreConfiguration.target(OreFeatures.STONE_ORE_REPLACEABLES, MWBlocks.TIN_ORE_BLOCK.get().defaultBlockState()), OreConfiguration.target(OreFeatures.DEEPSLATE_ORE_REPLACEABLES, MWBlocks.DEEPSLATE_TIN_ORE_BLOCK.get().defaultBlockState()));

    public static final Holder<ConfiguredFeature<OreConfiguration,?>> TIN_ORE = FeatureUtils.register("ore_tin", Feature.ORE, new OreConfiguration(ORE_TIN_TARGET_LIST, 7));
    public static final Holder<ConfiguredFeature<OreConfiguration,?>> SILVER_ORE = FeatureUtils.register("ore_silver", Feature.ORE, new OreConfiguration(ORE_SILVER_TARGET_LIST, 11));
    public static final Holder<ConfiguredFeature<OreConfiguration,?>> RUBY_ORE = FeatureUtils.register("ore_ruby", Feature.ORE, new OreConfiguration(ORE_RUBY_TARGET_LIST, 3));

    public static final Holder<PlacedFeature> ORE_TIN_PLACEMENT = PlacementUtils.register("ore_tin", TIN_ORE, commonOrePlacement(12, HeightRangePlacement.triangle(VerticalAnchor.absolute(-16), VerticalAnchor.absolute(112))));
    public static final Holder<PlacedFeature> ORE_SILVER_PLACEMENT = PlacementUtils.register("ore_silver", SILVER_ORE, commonOrePlacement(5, HeightRangePlacement.triangle(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(32))));
    public static final Holder<PlacedFeature> ORE_RUBY_PLACEMENT = PlacementUtils.register("ore_ruby", RUBY_ORE, commonOrePlacement(100, HeightRangePlacement.triangle(VerticalAnchor.absolute(-16), VerticalAnchor.absolute(480))));

    private static <C extends FeatureConfiguration, F extends Feature<C>> Holder<PlacedFeature> registerPlacedOreFeature(String id, ConfiguredFeature<C, F> feature, PlacementModifier... placementModifiers) {
        return PlacementUtils.register(id, Holder.direct(feature), placementModifiers);
    }

    private static List<PlacementModifier> orePlacement(PlacementModifier countModifier, PlacementModifier modifier) {
        return List.of(countModifier, InSquarePlacement.spread(), modifier, BiomeFilter.biome());
    }

    private static List<PlacementModifier> commonOrePlacement(int count, PlacementModifier modifier) {
        return orePlacement(CountPlacement.of(count), modifier);
    }

    public static void registerOres() {
        overworldOres.add(ORE_TIN_PLACEMENT);
        overworldOres.add(ORE_RUBY_PLACEMENT);
        overworldOres.add(ORE_SILVER_PLACEMENT);
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void registerBiomeModification(final BiomeLoadingEvent evt){
        BiomeGenerationSettingsBuilder generation = evt.getGeneration();
        for(Holder<PlacedFeature> ore : overworldOres) {
            if(ore != null) {
                generation.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, ore);
            }
        }
        if(evt.getCategory() == Biome.BiomeCategory.EXTREME_HILLS) {
            for (Holder<PlacedFeature> ore : mountainOres) {
                if (ore != null) {
                    generation.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, ore);
                }
            }
        }
    }

}
