package net.jmb19905.medievalworlds.common.registries;

import com.google.common.base.Suppliers;
import net.jmb19905.medievalworlds.MedievalWorlds;
import net.minecraft.core.Registry;
import net.minecraft.data.worldgen.features.OreFeatures;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.placement.*;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;
import java.util.function.Supplier;

@SuppressWarnings("OptionalGetWithoutIsPresent")
public class MWFeatures {

    public static final DeferredRegister<ConfiguredFeature<?, ?>> CONFIGURED_FEATURES = DeferredRegister.create(Registry.CONFIGURED_FEATURE_REGISTRY, MedievalWorlds.MOD_ID);

    public static final Supplier<List<OreConfiguration.TargetBlockState>> OVERWORLD_SILVER_ORES = Suppliers.memoize(() -> List.of(
            OreConfiguration.target(OreFeatures.STONE_ORE_REPLACEABLES, MWBlocks.SILVER_ORE.get().defaultBlockState()),
            OreConfiguration.target(OreFeatures.DEEPSLATE_ORE_REPLACEABLES, MWBlocks.DEEPSLATE_SILVER_ORE.get().defaultBlockState())
    ));

    public static final Supplier<List<OreConfiguration.TargetBlockState>> OVERWORLD_TIN_ORES = Suppliers.memoize(() -> List.of(
            OreConfiguration.target(OreFeatures.STONE_ORE_REPLACEABLES, MWBlocks.TIN_ORE.get().defaultBlockState()),
            OreConfiguration.target(OreFeatures.DEEPSLATE_ORE_REPLACEABLES, MWBlocks.DEEPSLATE_TIN_ORE.get().defaultBlockState())
    ));

    public static final RegistryObject<ConfiguredFeature<?, ?>> SILVER_ORE = CONFIGURED_FEATURES.register("silver_ore",
            () -> new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(OVERWORLD_SILVER_ORES.get(), 11))
    );

    public static final RegistryObject<ConfiguredFeature<?, ?>> TIN_ORE = CONFIGURED_FEATURES.register("tin_ore",
            () -> new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(OVERWORLD_TIN_ORES.get(), 7))
    );

    public static final DeferredRegister<PlacedFeature> PLACED_FEATURES = DeferredRegister.create(Registry.PLACED_FEATURE_REGISTRY, MedievalWorlds.MOD_ID);

    public static final RegistryObject<PlacedFeature> SILVER_ORE_PLACED = PLACED_FEATURES.register("silver_ore_placed",
            () -> new PlacedFeature(SILVER_ORE.getHolder().get(),
                    commonOrePlacement(5,
                            HeightRangePlacement.triangle(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(32))
                    )));

    public static final RegistryObject<PlacedFeature> TIN_ORE_PLACED = PLACED_FEATURES.register("tin_ore_placed",
            () -> new PlacedFeature(TIN_ORE.getHolder().get(),
                    commonOrePlacement(12,
                            HeightRangePlacement.triangle(VerticalAnchor.absolute(-16), VerticalAnchor.absolute(112))
                    )));

    public static List<PlacementModifier> orePlacement(PlacementModifier modifier, PlacementModifier modifier2) {
        return List.of(modifier, InSquarePlacement.spread(), modifier2, BiomeFilter.biome());
    }

    public static List<PlacementModifier> commonOrePlacement(int count, PlacementModifier modifier) {
        return orePlacement(CountPlacement.of(count), modifier);
    }

    public static List<PlacementModifier> rareOrePlacement(int count, PlacementModifier modifier) {
        return orePlacement(RarityFilter.onAverageOnceEvery(count), modifier);
    }

}
