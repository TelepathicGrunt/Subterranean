package net.telepathicgrunt.subterranean.generation.layer;

import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.INoiseRandom;
import net.minecraft.world.gen.layer.traits.IAreaTransformer0;
import net.telepathicgrunt.subterranean.world.biome.BiomeInit;


@SuppressWarnings("deprecation")
public enum BiomeDebugLayer implements IAreaTransformer0
{
	INSTANCE;

	private static final int CENTERAL_ISLES_BIOME = Registry.BIOME.getId(BiomeInit.CENTERAL_ISLES_BIOME);
	private static final int WATER_FLOOR_BIOME = Registry.BIOME.getId(BiomeInit.WATER_FLOOR_BIOME);
	private static final int TEST_BIOME = Registry.BIOME.getId(BiomeInit.TEST_BIOME);


	public int apply(INoiseRandom noise, int x, int z)
	{

		return TEST_BIOME;
		
//		if(noise.getNoiseGenerator().func_215459_a(x, 0, z, 1, 0, 1, 1, 1, 1) > 0)
//		{
//			return TEST_BIOME;
//		}
//		if(noise.random(5) == 0)
//		{
//			return CENTERAL_ISLES_BIOME;
//		}else
//		{
//			return WATER_FLOOR_BIOME;
//		}
	}

}