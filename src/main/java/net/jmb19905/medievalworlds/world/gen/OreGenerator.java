package net.jmb19905.medievalworlds.world.gen;

import net.jmb19905.medievalworlds.registries.BlockRegistryHandler;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.placement.ConfiguredPlacement;
import net.minecraft.world.gen.placement.CountRangeConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.registries.ForgeRegistries;

public class OreGenerator {

    public static void generateOre(){
        for(Biome biome : ForgeRegistries.BIOMES){
            if(biome == Biomes.MOUNTAINS || biome == Biomes.GRAVELLY_MOUNTAINS || biome == Biomes.WOODED_MOUNTAINS || biome == Biomes.MODIFIED_GRAVELLY_MOUNTAINS ||biome == Biomes.MOUNTAIN_EDGE){
                ConfiguredPlacement<CountRangeConfig> rubyConfig = Placement.COUNT_RANGE.configure(new CountRangeConfig(11, 5, 5, 37));
                biome.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, BlockRegistryHandler.RUBY_ORE_BLOCK.get().getDefaultState(), 3)).withPlacement(rubyConfig));
            }
            ConfiguredPlacement<CountRangeConfig> copperTinConfig = Placement.COUNT_RANGE.configure(new CountRangeConfig(15, 5, 5, 255));
            biome.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, BlockRegistryHandler.COPPER_ORE_BLOCK.get().getDefaultState(), 13)).withPlacement(copperTinConfig));
            biome.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, BlockRegistryHandler.TIN_ORE_BLOCK.get().getDefaultState(), 13)).withPlacement(copperTinConfig));
            ConfiguredPlacement<CountRangeConfig> silverConfig = Placement.COUNT_RANGE.configure(new CountRangeConfig(4, 5, 5, 36));
            biome.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, BlockRegistryHandler.SILVER_ORE_BLOCK.get().getDefaultState(), 9)).withPlacement(silverConfig));
        }
    }

}
