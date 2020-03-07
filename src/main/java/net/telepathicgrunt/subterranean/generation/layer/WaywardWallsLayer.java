package net.telepathicgrunt.subterranean.generation.layer;

import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.INoiseRandom;
import net.minecraft.world.gen.layer.traits.IAreaTransformer0;
import net.telepathicgrunt.subterranean.world.biome.BiomeInit;


@SuppressWarnings("deprecation")
public enum WaywardWallsLayer implements IAreaTransformer0
{
	INSTANCE;

	private static final int WAYWARD_WALLS_BIOME = Registry.BIOME.getId(BiomeInit.WAYWARD_WALLS_BIOME);
	private static final int WATER_FLOOR_BIOME = Registry.BIOME.getId(BiomeInit.WATER_FLOOR_BIOME);


	@Override
	public int apply(INoiseRandom noise, int x, int z)
	{
		if (noise.random(3) == 0)
		{
			return WAYWARD_WALLS_BIOME;
		}
		else
		{
			return WATER_FLOOR_BIOME;
		}
	}

}