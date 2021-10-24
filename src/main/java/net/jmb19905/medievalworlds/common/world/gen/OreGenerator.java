package net.jmb19905.medievalworlds.common.world.gen;

import net.jmb19905.medievalworlds.common.registries.BlockRegistryHandler;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.feature.template.RuleTest;
import net.minecraft.world.gen.placement.IPlacementConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.gen.placement.TopSolidRangeConfig;
import net.minecraftforge.common.world.BiomeGenerationSettingsBuilder;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class OreGenerator {

    public static DeferredRegister<Feature<?>> ORE_FEATURES = DeferredRegister.create(ForgeRegistries.FEATURES, "medievalworlds");

    public static final RegistryObject<Feature<ReplaceBlockConfig>> RUBY_ORE = ORE_FEATURES.register("ruby_ore", () -> new ReplaceBlockFeature(ReplaceBlockConfig.field_236604_a_));

    @SubscribeEvent
    public static void generateOres(final BiomeLoadingEvent evt){
        if(!evt.getCategory().equals(Biome.Category.NETHER) && !evt.getCategory().equals(Biome.Category.THEEND)) {
            if (evt.getCategory().equals(Biome.Category.EXTREME_HILLS)) {
                generateGem(evt.getGeneration(), BlockRegistryHandler.RUBY_ORE_BLOCK.get().getDefaultState());
            }
            generateOre(evt.getGeneration(), OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, BlockRegistryHandler.COPPER_ORE_BLOCK.get().getDefaultState(), 15, 0, 100,20);
            generateOre(evt.getGeneration(), OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, BlockRegistryHandler.TIN_ORE_BLOCK.get().getDefaultState(), 14, 0, 120,19);
            generateOre(evt.getGeneration(), OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, BlockRegistryHandler.SILVER_ORE_BLOCK.get().getDefaultState(), 10, 0, 40,10);
        }
    }

    private static void generateOre(BiomeGenerationSettingsBuilder generationSettings, RuleTest fillerBlockType, BlockState state, int veinSize, int minY, int maxY, int veinsPerChunk){
        generationSettings.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.withConfiguration(new OreFeatureConfig(fillerBlockType, state, veinSize)).withPlacement(Placement.RANGE.configure(new TopSolidRangeConfig(minY, 0, maxY))).square().func_242731_b(veinsPerChunk));
    }

    private static void generateGem(BiomeGenerationSettingsBuilder generationSettings, BlockState gemBlockState){
        generationSettings.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, RUBY_ORE.get().withConfiguration(new ReplaceBlockConfig(Blocks.STONE.getDefaultState(), gemBlockState)).withPlacement(Placement.EMERALD_ORE.configure(IPlacementConfig.NO_PLACEMENT_CONFIG)));
    }

}
