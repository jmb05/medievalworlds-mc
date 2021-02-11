package net.jmb19905.medievalworlds.world.gen;

import net.jmb19905.medievalworlds.registries.BlockRegistryHandler;
import net.minecraft.block.Blocks;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.placement.ConfiguredPlacement;
import net.minecraft.world.gen.placement.IPlacementConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import static net.jmb19905.medievalworlds.MedievalWorlds.MOD_ID;

@Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class OreGenerator {

    private static void removeIronOre(Biome biome){
        /*List<ConfiguredFeature<?, ?>> features = new ArrayList<>();

        for (Supplier<ConfiguredFeature<?, ?>> : biome.getGenerationSettings().getFeatures()) {
            if (((DecoratedFeatureConfig)f.config).feature instanceof OreFeature) {
                if (((OreFeatureConfig)((DecoratedFeatureConfig)f.config).feature).state.getBlock() == Blocks.IRON_ORE) {
                    features.add(f);
                }
            }
        }

        biome.getFeatures(GenerationStage.Decoration.UNDERGROUND_ORES).removeAll(features);*/
    }

    @SubscribeEvent
    public static void generateOre(FMLLoadCompleteEvent evt){
        /*for(Biome biome : ForgeRegistries.BIOMES){
            removeIronOre(biome);
            ConfiguredPlacement<CountRangeConfig> ironConfig = Placement.COUNT_RANGE.configure(new CountRangeConfig(15, 0, 0, 32));
            biome.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, Blocks.IRON_ORE.getDefaultState(), 9)).withPlacement(ironConfig));
            if(biome == Biomes.MOUNTAINS || biome == Biomes.GRAVELLY_MOUNTAINS || biome == Biomes.WOODED_MOUNTAINS || biome == Biomes.MODIFIED_GRAVELLY_MOUNTAINS ||biome == Biomes.MOUNTAIN_EDGE){
                biome.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.EMERALD_ORE.withConfiguration(new ReplaceBlockConfig(Blocks.STONE.getDefaultState(), BlockRegistryHandler.RUBY_ORE_BLOCK.get().getDefaultState())).withPlacement(Placement.EMERALD_ORE.configure(IPlacementConfig.NO_PLACEMENT_CONFIG)));
            }
            ConfiguredPlacement<CountRangeConfig> copperTinConfig = Placement.COUNT_RANGE.configure(new CountRangeConfig(15, 5, 5, 255));
            biome.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, BlockRegistryHandler.COPPER_ORE_BLOCK.get().getDefaultState(), 13)).withPlacement(copperTinConfig));
            biome.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, BlockRegistryHandler.TIN_ORE_BLOCK.get().getDefaultState(), 13)).withPlacement(copperTinConfig));
            ConfiguredPlacement<CountRangeConfig> silverConfig = Placement.COUNT_RANGE.configure(new CountRangeConfig(3, 5, 5, 36));
            biome.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, BlockRegistryHandler.SILVER_ORE_BLOCK.get().getDefaultState(), 8)).withPlacement(silverConfig));
        }*/
    }

}
