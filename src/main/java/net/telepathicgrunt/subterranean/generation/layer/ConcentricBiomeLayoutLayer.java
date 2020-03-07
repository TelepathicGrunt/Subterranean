package net.telepathicgrunt.subterranean.generation.layer;

import net.minecraft.util.SharedSeedRandom;
import net.minecraft.world.gen.INoiseRandom;
import net.minecraft.world.gen.PerlinNoiseGenerator;
import net.minecraft.world.gen.layer.traits.IAreaTransformer0;


public enum ConcentricBiomeLayoutLayer implements IAreaTransformer0
{
	INSTANCE;

	private static PerlinNoiseGenerator perlinGen;


	@Override
	public int apply(INoiseRandom noise, int x, int z)
	{
		double perlinNoise = perlinGen.noiseAt(x * 0.06D, z * 0.06D, false) * 50;
		float distanceSquared = x * x + z * z;
		double scale = 0.5D;

		//how far out in blocks * scale so it matches in reality + noise to make edge wavy

		//0-400
		if (distanceSquared <= 400 * scale + perlinNoise)
		{
			return StartingBiomesLayer.INSTANCE.apply(noise, x, z);
		}

		//400-800
		if (distanceSquared <= 800 * scale + perlinNoise)
		{
			return WaterFloorLayer.INSTANCE.apply(noise, x, z);
		}

		//800-1600
		if (distanceSquared <= 1600 * scale + perlinNoise)
		{
			return WaywardWallsLayer.INSTANCE.apply(noise, x, z);
		}

		return BiomeDebugLayer.INSTANCE.apply(noise, x, z);

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