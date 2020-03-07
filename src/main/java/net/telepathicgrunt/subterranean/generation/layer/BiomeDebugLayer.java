package net.telepathicgrunt.subterranean.generation.layer;

import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.INoiseRandom;
import net.minecraft.world.gen.PerlinNoiseGenerator;
import net.minecraft.world.gen.layer.traits.IAreaTransformer0;
import net.telepathicgrunt.subterranean.world.biome.BiomeInit;


@SuppressWarnings("deprecation")
public enum BiomeDebugLayer implements IAreaTransformer0
{
	INSTANCE;

	private static final int TEST_BIOME = Registry.BIOME.getId(BiomeInit.TEST_BIOME);
	private static final int TEST_BIOME2 = Registry.BIOME.getId(BiomeInit.TEST_BIOME2);

	private static PerlinNoiseGenerator perlinGen;

	@Override
	public int apply(INoiseRandom noise, int x, int z)
	{
		//double perlinNoise = perlinGen.noiseAt((double) x * 0.03D, (double)z * 0.03D, false) * 3;

		if(noise.random(2) == 0)
		{
			return TEST_BIOME;
		}else
		{
			return TEST_BIOME2;
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