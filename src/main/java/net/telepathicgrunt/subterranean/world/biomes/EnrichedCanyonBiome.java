package net.telepathicgrunt.subterranean.world.biomes;

import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilder;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.telepathicgrunt.subterranean.world.biome.STBiome;


public final class EnrichedCanyonBiome extends STBiome
{
	public EnrichedCanyonBiome()
	{
		super((new Builder()).surfaceBuilder(new ConfiguredSurfaceBuilder<>(STONE_SURFACE_BUILDER, STONE_ANDESITE_GRAVEL_SURFACE_CONFIG)).precipitation(Biome.RainType.NONE).category(Biome.Category.EXTREME_HILLS).depth(2.0F).scale(2.0F).temperature(0.3F).downfall(0.0F).waterColor(3093146).waterFogColor(2172035).parent((String) null));

		//this.addCarver(GenerationStage.Carving.AIR, Biome.createCarver(FeatureInit.STALACTITE_FILLER, new ProbabilityConfig(0.15F)));
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
