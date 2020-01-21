package net.telepathicgrunt.subterranean.world.biomes;

import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.ProbabilityConfig;
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilder;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.telepathicgrunt.subterranean.features.FeatureInit;
import net.telepathicgrunt.subterranean.world.biome.SubterraneanBiome;


public final class TestBiome extends SubterraneanBiome
{	
	public TestBiome()
	{
		super((new Builder()).surfaceBuilder(new ConfiguredSurfaceBuilder<>(GENERIC_SURFACE_BUILDER, GENERIC_SURFACE_CONFIG)).precipitation(Biome.RainType.NONE).category(Biome.Category.OCEAN).depth(-1.0F).scale(0.00F).temperature(0.3F).downfall(0.0F).waterColor(3093146).waterFogColor(2172035).parent((String) null));

		
		
		//this.addCarver(GenerationStage.Carving.AIR, Biome.createCarver(FeatureInit.STALACTITE_FILLER, new ProbabilityConfig(0.15F)));
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
