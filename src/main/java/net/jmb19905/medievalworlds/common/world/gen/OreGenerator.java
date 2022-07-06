package net.jmb19905.medievalworlds.common.world.gen;

import com.google.common.collect.ImmutableList;
import net.jmb19905.medievalworlds.core.registries.MWBlocks;
import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.features.OreFeatures;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.placement.*;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;
import net.minecraftforge.common.world.BiomeGenerationSettingsBuilder;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.ArrayList;
import java.util.List;

import static net.minecraft.data.worldgen.features.OreFeatures.DEEPSLATE_ORE_REPLACEABLES;
import static net.minecraft.data.worldgen.features.OreFeatures.STONE_ORE_REPLACEABLES;

public class OreGenerator {

    public static final ArrayList<Holder<PlacedFeature>> overworldOres = new ArrayList<>();
    public static final ArrayList<Holder<PlacedFeature>> mountainOres = new ArrayList<>();
    public static final RuleTest NATURAL_STONE = new TagMatchTest(BlockTags.BASE_STONE_OVERWORLD);

    public static final Holder<ConfiguredFeature<OreConfiguration,?>> TIN_ORE = FeatureUtils.register("ore_tin", Feature.ORE, new OreConfiguration(ImmutableList.of(OreConfiguration.target(STONE_ORE_REPLACEABLES, MWBlocks.TIN_ORE_BLOCK.get().defaultBlockState()), OreConfiguration.target(DEEPSLATE_ORE_REPLACEABLES, MWBlocks.DEEPSLATE_TIN_ORE_BLOCK.get().defaultBlockState())), 7));
    public static final Holder<ConfiguredFeature<OreConfiguration,?>> SILVER_ORE = FeatureUtils.register("ore_silver", Feature.ORE, new OreConfiguration(ImmutableList.of(OreConfiguration.target(STONE_ORE_REPLACEABLES, MWBlocks.SILVER_ORE_BLOCK.get().defaultBlockState()), OreConfiguration.target(DEEPSLATE_ORE_REPLACEABLES, MWBlocks.DEEPSLATE_SILVER_ORE_BLOCK.get().defaultBlockState())), 11));
    public static final Holder<ConfiguredFeature<OreConfiguration,?>> RUBY_ORE = FeatureUtils.register("ore_ruby", Feature.ORE, new OreConfiguration(List.of(OreConfiguration.target(STONE_ORE_REPLACEABLES, MWBlocks.RUBY_ORE_BLOCK.get().defaultBlockState()), OreConfiguration.target(DEEPSLATE_ORE_REPLACEABLES, MWBlocks.DEEPSLATE_RUBY_ORE_BLOCK.get().defaultBlockState())), 3));
    public static final Holder<ConfiguredFeature<OreConfiguration,?>> LIMESTONE_ORE = FeatureUtils.register("ore_limestone", Feature.ORE, new OreConfiguration(NATURAL_STONE, MWBlocks.LIMESTONE_BLOCK.get().defaultBlockState(), 64));

    public static final Holder<PlacedFeature> ORE_TIN_PLACEMENT = PlacementUtils.register("ore_tin", TIN_ORE, commonOrePlacement(12, HeightRangePlacement.triangle(VerticalAnchor.absolute(-16), VerticalAnchor.absolute(112))));
    public static final Holder<PlacedFeature> ORE_SILVER_PLACEMENT = PlacementUtils.register("ore_silver", SILVER_ORE, commonOrePlacement(5, HeightRangePlacement.triangle(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(32))));
    public static final Holder<PlacedFeature> ORE_RUBY_PLACEMENT = PlacementUtils.register("ore_ruby", RUBY_ORE, commonOrePlacement(100, HeightRangePlacement.triangle(VerticalAnchor.absolute(-16), VerticalAnchor.absolute(480))));
    public static final Holder<PlacedFeature> ORE_LIMESTONE_UPPER = PlacementUtils.register("ore_limestone_upper", LIMESTONE_ORE, rareOrePlacement(6, HeightRangePlacement.uniform(VerticalAnchor.absolute(64), VerticalAnchor.absolute(128))));
    public static final Holder<PlacedFeature> ORE_LIMESTONE_LOWER = PlacementUtils.register("ore_limestone_lower", LIMESTONE_ORE, commonOrePlacement(2, HeightRangePlacement.uniform(VerticalAnchor.absolute(0), VerticalAnchor.absolute(60))));

    private static List<PlacementModifier> orePlacement(PlacementModifier countModifier, PlacementModifier modifier) {
        return List.of(countModifier, InSquarePlacement.spread(), modifier, BiomeFilter.biome());
    }

    private static List<PlacementModifier> commonOrePlacement(int count, PlacementModifier modifier) {
        return orePlacement(CountPlacement.of(count), modifier);
    }

    @SuppressWarnings("SameParameterValue")
    private static List<PlacementModifier> rareOrePlacement(int count, PlacementModifier modifier) {
        return orePlacement(RarityFilter.onAverageOnceEvery(count), modifier);
    }

    public static void registerOres() {
        overworldOres.add(ORE_TIN_PLACEMENT);
        overworldOres.add(ORE_SILVER_PLACEMENT);
        overworldOres.add(ORE_LIMESTONE_LOWER);
        overworldOres.add(ORE_LIMESTONE_UPPER);

        mountainOres.add(ORE_RUBY_PLACEMENT);
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
