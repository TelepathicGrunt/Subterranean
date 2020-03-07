package net.telepathicgrunt.subterranean.world.biomes;

import com.google.common.collect.Lists;

import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.DefaultBiomeFeatures;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.feature.SphereReplaceConfig;
import net.minecraft.world.gen.placement.ChanceConfig;
import net.minecraft.world.gen.placement.CountRangeConfig;
import net.minecraft.world.gen.placement.DepthAverageConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilder;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.telepathicgrunt.subterranean.world.biome.STBiome;


public final class CenteralIslesBiome extends STBiome
{
	public CenteralIslesBiome()
	{
		super((new Builder()).surfaceBuilder(new ConfiguredSurfaceBuilder<>(PODZOL_SURFACE_BUILDER, STONE_ANDESITE_GRAVEL_SURFACE_CONFIG)).precipitation(Biome.RainType.NONE).category(Biome.Category.EXTREME_HILLS).depth(-1.40F).scale(0.0F).temperature(0.3F).downfall(0.0F).waterColor(3093146).waterFogColor(2172035).parent((String) null));

		this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.field_227248_z_.configure(DefaultBiomeFeatures.BROWN_MUSHROOM_CONFIG).createDecoratedFeature(Placement.CHANCE_HEIGHTMAP_DOUBLE.configure(new ChanceConfig(2))));
		this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.field_227248_z_.configure(DefaultBiomeFeatures.RED_MUSHROOM_CONFIG).createDecoratedFeature(Placement.CHANCE_HEIGHTMAP_DOUBLE.configure(new ChanceConfig(4))));

		this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.DISK.configure(new SphereReplaceConfig(CLAY, 4, 1, Lists.newArrayList(GRAVEL, ANDESITE))).createDecoratedFeature(Placement.COUNT_RANGE.configure(new CountRangeConfig(1, 0, 0, 50))));
		this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.configure(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, COAL_ORE, 7)).createDecoratedFeature(Placement.COUNT_RANGE.configure(new CountRangeConfig(4, 0, 0, 50))));
		this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.configure(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, IRON_ORE, 5)).createDecoratedFeature(Placement.COUNT_RANGE.configure(new CountRangeConfig(2, 0, 0, 50))));
		this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.configure(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, LAPIS_ORE, 7)).createDecoratedFeature(Placement.COUNT_DEPTH_AVERAGE.configure(new DepthAverageConfig(1, 10, 5))));

		this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.configure(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, GRANITE, 33)).createDecoratedFeature(Placement.COUNT_RANGE.configure(new CountRangeConfig(2, 10, 0, 40))));
		this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.configure(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, DIORITE, 33)).createDecoratedFeature(Placement.COUNT_RANGE.configure(new CountRangeConfig(2, 10, 0, 40))));
		
		this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.SPRING_FEATURE.configure(DefaultBiomeFeatures.WATER_SPRING_CONFIG).createDecoratedFeature(Placement.COUNT_BIASED_RANGE.configure(new CountRangeConfig(5, 15, 0, 35))));
		this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.SPRING_FEATURE.configure(DefaultBiomeFeatures.LAVA_SPRING_CONFIG).createDecoratedFeature(Placement.COUNT_VERY_BIASED_RANGE.configure(new CountRangeConfig(2, 15, 0, 35))));

		this.addSpawn(EntityClassification.AMBIENT, new Biome.SpawnListEntry(EntityType.BAT, 10, 1, 1));
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
