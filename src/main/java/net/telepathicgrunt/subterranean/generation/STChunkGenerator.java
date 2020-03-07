package net.telepathicgrunt.subterranean.generation;

import java.util.List;

import net.minecraft.entity.EntityClassification;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IWorld;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.gen.OctavesNoiseGenerator;
import net.minecraft.world.gen.OverworldGenSettings;
import net.minecraft.world.gen.WorldGenRegion;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.spawner.WorldEntitySpawner;


public class STChunkGenerator extends STNoiseChunkGenerator<OverworldGenSettings>
{
	private static final float[] field_222576_h = Util.make(new float[25], (p_222575_0_) ->
	{
		for (int i = -2; i <= 2; ++i)
		{
			for (int j = -2; j <= 2; ++j)
			{
				float f = 10.0F / MathHelper.sqrt(i * i + j * j + 0.2F);
				p_222575_0_[i + 2 + (j + 2) * 5] = f;
			}
		}

	});
	private final OctavesNoiseGenerator depthNoise;

	public STChunkGenerator(IWorld world, BiomeProvider provider, OverworldGenSettings settingsIn)
	{
		super(world, provider, 4, 8, 256, settingsIn);
		this.randomSeed.skip(2620);
		this.depthNoise = new OctavesNoiseGenerator(this.randomSeed, 15, 0);
	}

	@Override
	public void spawnMobs(WorldGenRegion region)
	{
		int i = region.getMainChunkX();
		int j = region.getMainChunkZ();
		Biome biome = region.getBiome((new ChunkPos(i, j)).asBlockPos());
		SharedSeedRandom sharedseedrandom = new SharedSeedRandom();
		sharedseedrandom.setDecorationSeed(region.getSeed(), i << 4, j << 4);
		WorldEntitySpawner.performWorldGenSpawning(region, biome, i, j, sharedseedrandom);
	}

	@Override
	protected void fillNoiseColumn(double[] areaArrayIn, int x, int z)
	{
		this.setupPerlinNoiseGenerators(areaArrayIn, x, z, 100D, 6000D, 100D, 2.5D, 10D, 3, -10);
	}

	@Override
	protected double biomeHeightSmoother(double nearbyArea1, double nearbyArea2, int y)
	{
		double heightMultipler = (y - (8.5D + nearbyArea1 * 8.5D / 8.0D * 4.0D)) * 12.0D * 128.0D / 256.0D / nearbyArea2;
		if (heightMultipler < 0.0D)
		{
			heightMultipler *= 0.15D; // lower this constant for sharper biome edges and increase for smoother biome edges
		}

		return heightMultipler;
	}

	@Override
	protected double[] getBiomeNoiseColumn(int noiseX, int noiseZ)
	{
		double[] adouble = new double[2];
		float f = 0.0F;
		float f1 = 0.0F;
		float f2 = 0.0F;
		int y = this.getSeaLevel();
		float noiseDepth = this.biomeProvider.getBiomeForNoiseGen(noiseX, y, noiseZ).getDepth();

		for (int j = -2; j <= 2; ++j)
		{
			for (int k = -2; k <= 2; ++k)
			{
				Biome biome = this.biomeProvider.getBiomeForNoiseGen(noiseX + j, y, noiseZ + k);
				float depthWeight = biome.getDepth();
				float scaleWeight = biome.getScale();

				float f6 = field_222576_h[j + 2 + (k + 2) * 5];
				if (biome.getDepth() > noiseDepth)
				{
					f6 /= 2.0F;
				}

				f += scaleWeight * f6;
				f1 += depthWeight * f6;
				f2 += f6;
			}
		}

		f = f / f2;
		f1 = f1 / f2;
		f = f * 1.0F + 0.1F;
		f1 = (f1 * 4.0F - 1.0F) / 8.0F;
		adouble[0] = f1 + this.getNoiseDepthAt(noiseX, noiseZ);
		adouble[1] = f;
		return adouble;
	}


	private double getNoiseDepthAt(int p_222574_1_, int p_222574_2_)
	{
		double noise = this.depthNoise.getValue(p_222574_1_ * 200, 10.0D, p_222574_2_ * 200, 1.0D, 0.0D, true) * 65535.0D / 8000.0D;
		if (noise < 0.0D)
		{
			noise = -noise * 0.3D;
		}

		noise = noise * 3.0D - 2.0D;
		if (noise < 0.0D)
		{
			noise = noise / 28.0D;
		}
		else
		{
			if (noise > 1.0D)
			{
				noise = 1.0D;
			}

			noise = noise / 40.0D;
		}

		return noise;
	}

	@Override
	public List<Biome.SpawnListEntry> getPossibleCreatures(EntityClassification creatureType, BlockPos pos)
	{
		return super.getPossibleCreatures(creatureType, pos);
	}

	@Override
	public void spawnMobs(ServerWorld world, boolean spawnHostileMobs, boolean spawnPeacefulMobs)
	{
	}

	@Override
	public int getGroundHeight()
	{
		return getSeaLevel() + 1;
	}
}