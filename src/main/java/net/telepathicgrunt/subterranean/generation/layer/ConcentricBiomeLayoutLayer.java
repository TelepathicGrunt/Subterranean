package net.telepathicgrunt.subterranean.generation.layer;

import net.minecraft.util.SharedSeedRandom;
import net.minecraft.world.gen.INoiseRandom;
import net.minecraft.world.gen.PerlinNoiseGenerator;
import net.minecraft.world.gen.layer.traits.IAreaTransformer0;


public enum ConcentricBiomeLayoutLayer implements IAreaTransformer0
{
	INSTANCE;
	
	private static PerlinNoiseGenerator perlinGen;
	
	public int apply(INoiseRandom noise, int x, int z)
	{
		int biomeID = 0;
		double perlinNoise = perlinGen.noiseAt((double) x * 0.06D, (double)z * 0.06D, false) * 20;
		float distanceSquared = x * x + z * z;
		
//		if(distanceSquared <= 1600 + perlinNoise)
//		{
//			biomeID = StartingBiomesLayer.INSTANCE.apply(noise, x, z);
//		}
//		else 
//		{
//			biomeID = BiomeDebugLayer.INSTANCE.apply(noise, x, z);
//		}

		biomeID = StartingBiomesLayer.INSTANCE.apply(noise, x, z);
		return biomeID;
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