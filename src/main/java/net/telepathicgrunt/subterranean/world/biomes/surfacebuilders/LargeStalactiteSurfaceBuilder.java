package net.telepathicgrunt.subterranean.world.biomes.surfacebuilders;

import java.util.Random;
import java.util.function.Function;

import com.mojang.datafixers.Dynamic;

import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.PerlinNoiseGenerator;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;


public class LargeStalactiteSurfaceBuilder extends SurfaceBuilder<SurfaceBuilderConfig>
{
	protected PerlinNoiseGenerator perlinGen;


	public LargeStalactiteSurfaceBuilder(Function<Dynamic<?>, ? extends SurfaceBuilderConfig> config)
	{
		super(config);
	}


	@Override
	public void buildSurface(Random random, IChunk chunkIn, Biome biomeIn, int x, int z, int startHeight, double inputNoise, BlockState defaultBlock, BlockState defaultFluid, int seaLevel, long seed, SurfaceBuilderConfig config)
	{
		double heightNoise = 0.0D;
		double squareness = 1; //makes noise more cubic
		double noiseScale = 0.0175D; //how much stepping in x and z we do at start. (lower number makes stalactites more stretched out horizontally) 
		double differenceInScale = 0.0006D; //how much difference in scale between all the perlin generators.

		//how many iterations of perlin we do with scale for each offsetted by differenceInScale.
		for (int i = 0; i < 5; i++)
		{
			double perlinNoise = Math.abs(this.perlinGen.noiseAt(Math.floor(x / squareness) * squareness * (noiseScale - differenceInScale * i), Math.floor(z / squareness) * squareness * (noiseScale - differenceInScale * i), false));
			heightNoise = Math.max(perlinNoise, heightNoise);
		}
		heightNoise = Math.ceil(heightNoise * 140.0D) + 130.0D; // * range of stalactites and + minimum height of stalactites. 

		int xInChunk = x & 15;
		int zInChunk = z & 15;
		BlockPos.Mutable blockpos$mutable = new BlockPos.Mutable();

		for (int y = 254; y >= heightNoise; --y)
		{
			blockpos$mutable.setPos(xInChunk, y, zInChunk);

			//only add out stalactites in air.
			if (chunkIn.getBlockState(blockpos$mutable).getMaterial() == Material.AIR)
			{
				chunkIn.setBlockState(blockpos$mutable, defaultBlock, false);
			}
		}
	}


	/*
	 * Sets up the perlin generator with the world seed.
	 */
	@Override
	public void setSeed(long seed)
	{
		if (this.perlinGen == null)
		{
			SharedSeedRandom sharedseedrandom = new SharedSeedRandom(seed);
			this.perlinGen = new PerlinNoiseGenerator(sharedseedrandom, 0, 0);
		}
	}
}