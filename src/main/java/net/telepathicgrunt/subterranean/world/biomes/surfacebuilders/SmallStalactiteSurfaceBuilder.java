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


public class SmallStalactiteSurfaceBuilder extends SurfaceBuilder<SurfaceBuilderConfig>
{
	protected PerlinNoiseGenerator perlinGen;


	public SmallStalactiteSurfaceBuilder(Function<Dynamic<?>, ? extends SurfaceBuilderConfig> config)
	{
		super(config);
	}


	@Override
	public void buildSurface(Random random, IChunk chunkIn, Biome biomeIn, int x, int z, int startHeight, double inputNoise, BlockState defaultBlock, BlockState defaultFluid, int seaLevel, long seed, SurfaceBuilderConfig config)
	{
		double heightNoise = 0.0D;
		double squareness = 1;  //makes noise more cubic

		//Uses abs to make sharp peaks in the noise. Utilizies inputNoise to make regions that are bare in stalactites
		//this noise makes the stalactites.
		double perlinNoise1 = Math.abs(Math.min(Math.abs(inputNoise), Math.abs(this.perlinGen.noiseAt(Math.floor(x / squareness) * squareness * 0.06D, Math.floor(z / squareness) * squareness * 0.06D, false))));

		double sharpNoise = ((perlinNoise1 * perlinNoise1) * 0.7 + 0.3D) * Math.abs(inputNoise / 3.1); // Makes noise even sharper.
		heightNoise = Math.ceil((1 - sharpNoise) * 140.0D) + 130.0D; // * range of stalactites and + minimum height of stalactites. 

		//Uses abs to make sharp peaks in the noise.
		//This noise covers the Bedrock ceiling so it is not exposed
		double perlinNoise2 = Math.abs(this.perlinGen.noiseAt(Math.floor(x / squareness) * squareness * 0.1D, Math.floor(z / squareness) * squareness * 0.1D, false));
		double maxCap = Math.ceil(perlinNoise2 * 3D) + 248D; // Hovers around y = 248.
		if (heightNoise > maxCap) // heightNoise cannot get to Bedrock Ceiling
		{
			heightNoise = maxCap;
		}

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