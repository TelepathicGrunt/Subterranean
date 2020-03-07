package net.telepathicgrunt.subterranean.generation.layer;

import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.INoiseRandom;
import net.minecraft.world.gen.layer.traits.IAreaTransformer0;
import net.telepathicgrunt.subterranean.world.biome.BiomeInit;


@SuppressWarnings("deprecation")
public enum WaterFloorLayer implements IAreaTransformer0
{
	INSTANCE;

	private static final int WATER_FLOOR_BIOME = Registry.BIOME.getId(BiomeInit.WATER_FLOOR_BIOME);

	@Override
	public int apply(INoiseRandom noise, int x, int z)
	{
		return WATER_FLOOR_BIOME;
	}

}