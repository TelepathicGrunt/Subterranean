package net.telepathicgrunt.subterranean.generation.layer;

import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.INoiseRandom;
import net.minecraft.world.gen.layer.traits.IAreaTransformer0;
import net.telepathicgrunt.subterranean.world.biome.BiomeInit;


@SuppressWarnings("deprecation")
public enum CarvedIslandsLayer implements IAreaTransformer0
{
	INSTANCE;

	private static final int CARVED_ISLANDS_BIOME = Registry.BIOME.getId(BiomeInit.CARVED_ISLANDS_BIOME);
	private static final int CARVED_ISLANDS_WATER_FLOOR_BIOME = Registry.BIOME.getId(BiomeInit.CARVED_ISLANDS_WATER_FLOOR_BIOME);

	public int apply(INoiseRandom noise, int x, int z)
	{
		if(noise.random(2) == 0)
		{
			return CARVED_ISLANDS_BIOME;
		}else
		{
			return CARVED_ISLANDS_WATER_FLOOR_BIOME;
		}
	}
}