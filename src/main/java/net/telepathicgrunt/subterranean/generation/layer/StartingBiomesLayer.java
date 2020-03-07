package net.telepathicgrunt.subterranean.generation.layer;

import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.INoiseRandom;
import net.minecraft.world.gen.layer.traits.IAreaTransformer0;
import net.telepathicgrunt.subterranean.world.biome.BiomeInit;


@SuppressWarnings("deprecation")
public enum StartingBiomesLayer implements IAreaTransformer0
{
	INSTANCE;

	private static final int CENTERAL_ISLES_BIOME = Registry.BIOME.getId(BiomeInit.CENTERAL_ISLES_BIOME);
	private static final int WATER_FLOOR_BIOME = Registry.BIOME.getId(BiomeInit.WATER_FLOOR_BIOME);

	@Override
	public int apply(INoiseRandom noise, int x, int z)
	{
		if(noise.random(5) == 0)
		{
			return CENTERAL_ISLES_BIOME;
		}else
		{
			return WATER_FLOOR_BIOME;
		}
	}

}