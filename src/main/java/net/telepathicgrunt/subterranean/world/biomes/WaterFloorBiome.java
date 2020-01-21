package net.telepathicgrunt.subterranean.world.biomes;

import com.google.common.collect.Lists;

import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.feature.SphereReplaceConfig;
import net.minecraft.world.gen.placement.CountRangeConfig;
import net.minecraft.world.gen.placement.DepthAverageConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilder;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.telepathicgrunt.subterranean.world.biome.STBiome;


public final class WaterFloorBiome extends STBiome
{
	public WaterFloorBiome()
	{
		super((new Builder()).surfaceBuilder(new ConfiguredSurfaceBuilder<>(GENERIC_SURFACE_BUILDER, GENERIC_SURFACE_CONFIG)).precipitation(Biome.RainType.NONE).category(Biome.Category.OCEAN).depth(-3.1F).scale(0.05F).temperature(0.3F).downfall(0.0F).waterColor(3093146).waterFogColor(2172035).parent((String) null));

		this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.DISK.configure(new SphereReplaceConfig(COAL_ORE, 4, 2, Lists.newArrayList(GRAVEL, ANDESITE))).createDecoratedFeature(Placement.COUNT_RANGE.configure(new CountRangeConfig(6, 5, 0, 24))));
		this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.DISK.configure(new SphereReplaceConfig(GRAY_CONCRETE, 4, 2, Lists.newArrayList(GRAVEL, ANDESITE))).createDecoratedFeature(Placement.COUNT_RANGE.configure(new CountRangeConfig(4, 5, 0, 24))));
		this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.DISK.configure(new SphereReplaceConfig(CLAY, 4, 1, Lists.newArrayList(GRAVEL, ANDESITE))).createDecoratedFeature(Placement.COUNT_RANGE.configure(new CountRangeConfig(15, 5, 0, 24))));
		this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.DISK.configure(new SphereReplaceConfig(COBBLESTONE, 4, 2, Lists.newArrayList(GRAVEL, ANDESITE))).createDecoratedFeature(Placement.COUNT_RANGE.configure(new CountRangeConfig(6, 5, 0, 24))));
		this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.DISK.configure(new SphereReplaceConfig(DEAD_BRAIN_CORAL, 4, 2, Lists.newArrayList(GRAVEL, ANDESITE))).createDecoratedFeature(Placement.COUNT_RANGE.configure(new CountRangeConfig(8, 5, 0, 24))));
		this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.DISK.configure(new SphereReplaceConfig(DEAD_BUBBLE_CORAL, 4, 2, Lists.newArrayList(GRAVEL, ANDESITE))).createDecoratedFeature(Placement.COUNT_RANGE.configure(new CountRangeConfig(8, 5, 0, 24))));
		this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.DISK.configure(new SphereReplaceConfig(DEAD_TUBE_CORAL, 5, 2, Lists.newArrayList(GRAVEL, ANDESITE))).createDecoratedFeature(Placement.COUNT_RANGE.configure(new CountRangeConfig(12, 5, 0, 24))));
		this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.DISK.configure(new SphereReplaceConfig(DEAD_FIRE_CORAL, 6, 2, Lists.newArrayList(GRAVEL, ANDESITE))).createDecoratedFeature(Placement.COUNT_RANGE.configure(new CountRangeConfig(16, 5, 0, 24))));
		this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.DISK.configure(new SphereReplaceConfig(DEAD_HORN_CORAL, 6, 2, Lists.newArrayList(GRAVEL, ANDESITE))).createDecoratedFeature(Placement.COUNT_RANGE.configure(new CountRangeConfig(16, 5, 0, 24))));
		this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.configure(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, COAL_ORE, 7)).createDecoratedFeature(Placement.COUNT_RANGE.configure(new CountRangeConfig(8, 0, 0, 50))));
		this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.configure(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, IRON_ORE, 5)).createDecoratedFeature(Placement.COUNT_RANGE.configure(new CountRangeConfig(6, 0, 0, 50))));
		this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.configure(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, LAPIS_ORE, 7)).createDecoratedFeature(Placement.COUNT_DEPTH_AVERAGE.configure(new DepthAverageConfig(4, 10, 5))));

		this.addSpawn(EntityClassification.WATER_CREATURE, new Biome.SpawnListEntry(EntityType.SALMON, 10, 1, 1));
	}


	/*
	 * Set sky color
	 */
	@OnlyIn(Dist.CLIENT)
	public int getSkyColor()
	{
		return 0;
	}
}
