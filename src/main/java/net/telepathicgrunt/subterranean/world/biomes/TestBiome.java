package net.telepathicgrunt.subterranean.world.biomes;

import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.ProbabilityConfig;
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilder;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.telepathicgrunt.subterranean.features.carvers.STCarvers;
import net.telepathicgrunt.subterranean.world.biome.STBiome;
import net.telepathicgrunt.subterranean.world.biomes.surfacebuilders.STSurfaceBuilders;


public final class TestBiome extends STBiome
{
	public TestBiome()
	{
		super((new Builder()).surfaceBuilder(new ConfiguredSurfaceBuilder<>(STSurfaceBuilders.STONE_SURFACE_BUILDER, STSurfaceBuilders.STONE_ANDESITE_GRAVEL_SURFACE_CONFIG)).precipitation(Biome.RainType.NONE).category(Biome.Category.OCEAN).depth(0.1F).scale(0.5F).temperature(0.3F).downfall(0.0F).waterColor(3093146).waterFogColor(2172035).parent((String) null));

		this.addCarver(GenerationStage.Carving.AIR, Biome.createCarver(STCarvers.TEST_CARVER, new ProbabilityConfig(0.05F)));
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
