package net.telepathicgrunt.subterranean.world.biomes;

import com.google.common.collect.Lists;

import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.DefaultBiomeFeatures;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.feature.ProbabilityConfig;
import net.minecraft.world.gen.feature.SphereReplaceConfig;
import net.minecraft.world.gen.placement.ChanceConfig;
import net.minecraft.world.gen.placement.CountRangeConfig;
import net.minecraft.world.gen.placement.DepthAverageConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilder;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.telepathicgrunt.subterranean.features.carvers.STCarvers;
import net.telepathicgrunt.subterranean.world.biome.STBiome;
import net.telepathicgrunt.subterranean.world.biomes.surfacebuilders.STSurfaceBuilders;


public final class CarvedIslandsBiome extends STBiome
{
	public CarvedIslandsBiome()
	{
		super((new Builder()).surfaceBuilder(new ConfiguredSurfaceBuilder<>(STSurfaceBuilders.STONE_SURFACE_BUILDER, STSurfaceBuilders.STONE_ANDESITE_GRAVEL_SURFACE_CONFIG)).precipitation(Biome.RainType.NONE).category(Biome.Category.PLAINS).depth(0.1F).scale(0.5F).temperature(0.3F).downfall(0.0F).waterColor(3093146).waterFogColor(2172035).parent((String) null));

		this.addCarver(GenerationStage.Carving.AIR, Biome.createCarver(STCarvers.SHELFSTONE_CARVER, new ProbabilityConfig(0.05F)));

		this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.RANDOM_PATCH.withConfiguration(DefaultBiomeFeatures.BROWN_MUSHROOM_CONFIG).withPlacement(Placement.CHANCE_HEIGHTMAP_DOUBLE.configure(new ChanceConfig(2))));
		this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.RANDOM_PATCH.withConfiguration(DefaultBiomeFeatures.RED_MUSHROOM_CONFIG).withPlacement(Placement.CHANCE_HEIGHTMAP_DOUBLE.configure(new ChanceConfig(4))));

		this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.DISK.withConfiguration(new SphereReplaceConfig(LIGHT_GRAY_CONCRETE, 4, 2, Lists.newArrayList(STONE, GRAVEL, ANDESITE))).withPlacement(Placement.COUNT_RANGE.configure(new CountRangeConfig(8, 5, 0, 24))));
		this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.DISK.withConfiguration(new SphereReplaceConfig(CLAY, 4, 1, Lists.newArrayList(STONE, GRAVEL, ANDESITE))).withPlacement(Placement.COUNT_RANGE.configure(new CountRangeConfig(15, 0, 0, 24))));
		this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, COAL_ORE, 7)).withPlacement(Placement.COUNT_RANGE.configure(new CountRangeConfig(8, 0, 0, 50))));
		this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, IRON_ORE, 6)).withPlacement(Placement.COUNT_RANGE.configure(new CountRangeConfig(20, 0, 0, 50))));
		this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, LAPIS_ORE, 7)).withPlacement(Placement.COUNT_DEPTH_AVERAGE.configure(new DepthAverageConfig(1, 10, 5))));

		this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, CLAY, 12)).withPlacement(Placement.COUNT_RANGE.configure(new CountRangeConfig(5, 5, 0, 20))));
		this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, DIRT, 33)).withPlacement(Placement.COUNT_RANGE.configure(new CountRangeConfig(2, 10, 0, 80))));
		this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, GRANITE, 50)).withPlacement(Placement.COUNT_RANGE.configure(new CountRangeConfig(5, 10, 0, 80))));
		this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, DIORITE, 33)).withPlacement(Placement.COUNT_RANGE.configure(new CountRangeConfig(4, 10, 0, 80))));
		this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, ANDESITE, 33)).withPlacement(Placement.COUNT_RANGE.configure(new CountRangeConfig(4, 10, 0, 80))));

		this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.SPRING_FEATURE.withConfiguration(DefaultBiomeFeatures.WATER_SPRING_CONFIG).withPlacement(Placement.COUNT_BIASED_RANGE.configure(new CountRangeConfig(25, 24, 0, 50))));
		this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.SPRING_FEATURE.withConfiguration(DefaultBiomeFeatures.LAVA_SPRING_CONFIG).withPlacement(Placement.COUNT_VERY_BIASED_RANGE.configure(new CountRangeConfig(5, 24, 0, 50))));

	}


	/*
	 * Set sky color
	 */
	@Override
	@OnlyIn(Dist.CLIENT)
	public int getSkyColor()
	{
		return 0;
	}
}
