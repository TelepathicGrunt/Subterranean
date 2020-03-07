package net.telepathicgrunt.subterranean.generation.layer;

import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.INoiseRandom;
import net.minecraft.world.gen.PerlinNoiseGenerator;
import net.minecraft.world.gen.layer.traits.IAreaTransformer0;
import net.telepathicgrunt.subterranean.world.biome.BiomeInit;


@SuppressWarnings("deprecation")
public enum EnrichedCanyonLayer implements IAreaTransformer0
{
	INSTANCE;

	private static final int ENRICHED_CANYON_BIOME = Registry.BIOME.getId(BiomeInit.ENRICHED_CANYON_BIOME);
	private static final int WATER_FLOOR_BIOME = Registry.BIOME.getId(BiomeInit.WATER_FLOOR_BIOME);

	private static PerlinNoiseGenerator perlinGen;

	@Override
	public int apply(INoiseRandom noise, int x, int z)
	{
		double perlinNoise = perlinGen.noiseAt((double) x * 0.03D, (double)z * 0.03D, false) * 3;

		if(Math.abs(perlinNoise)%1 < 0.25)
		{
			return WATER_FLOOR_BIOME;
		}
		else
		{
			return ENRICHED_CANYON_BIOME;
		}
		
	}

	public static void setSeed(long seed)
	{
		if (perlinGen == null)
		{
			SharedSeedRandom sharedseedrandom = new SharedSeedRandom(seed);
			perlinGen = new PerlinNoiseGenerator(sharedseedrandom, 0, 0);
		}
	}
}