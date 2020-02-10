package net.telepathicgrunt.subterranean.world.biomes.surfacebuilders;

import java.util.Random;
import java.util.function.Function;

import com.mojang.datafixers.Dynamic;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;
import net.telepathicgrunt.subterranean.world.biome.STBiome;


public class StoneSurfaceBuilder extends SurfaceBuilder<SurfaceBuilderConfig>
{
	public StoneSurfaceBuilder(Function<Dynamic<?>, ? extends SurfaceBuilderConfig> p_i51310_1_)
	{
		super(p_i51310_1_);
	}


	public void buildSurface(Random random, IChunk chunkIn, Biome biomeIn, int x, int z, int startHeight, double noise, BlockState defaultBlock, BlockState defaultFluid, int seaLevel, long seed, SurfaceBuilderConfig config)
	{
		int xpos = x & 15;
		int zpos = z & 15;
		BlockPos.Mutable blockpos$Mutable = new BlockPos.Mutable();
		int depthOfGravel = -1;

		int heightOfLand = 0;
		for (int ypos = 15; ypos <= 250; ++ypos)
		{
			blockpos$Mutable.setPos(xpos, ypos, zpos);
			BlockState iblockstate2 = chunkIn.getBlockState(blockpos$Mutable);
			
			if(iblockstate2.getMaterial() == Material.AIR) 
			{
				heightOfLand = ypos - 1;
				break;
			}
		}
		
		double noisemod = Math.abs(noise%1.0D);
		if(heightOfLand > 22)
		{
			if (noisemod < 0.2D)
			{
				SurfaceBuilder.DEFAULT.buildSurface(random, chunkIn, biomeIn, x, z, startHeight, noise, defaultBlock, defaultFluid, seaLevel, seed, STBiome.STONE_ANDESITE_GRAVEL_SURFACE_CONFIG);
			}
			else if (noisemod < 0.35D || noisemod > 0.8)
			{
				SurfaceBuilder.DEFAULT.buildSurface(random, chunkIn, biomeIn, x, z, startHeight, noise, defaultBlock, defaultFluid, seaLevel, seed, STBiome.GRAVEL_GRAVEL_STONE_SURFACE_CONFIG);
			}
			else if (noisemod < 0.7)
			{
				SurfaceBuilder.DEFAULT.buildSurface(random, chunkIn, biomeIn, x, z, startHeight, noise, defaultBlock, defaultFluid, seaLevel, seed, STBiome.ANDESITE_GRAVEL_STONE_SURFACE_CONFIG);
			}
			else
			{
				SurfaceBuilder.DEFAULT.buildSurface(random, chunkIn, biomeIn, x, z, startHeight, noise, defaultBlock, defaultFluid, seaLevel, seed, STBiome.LIGHT_GRAY_CONCRETE_POWDER_ANDESITE_STONE_SURFACE_CONFIG);
			}
		}
		else if(heightOfLand > 17)
		{
			SurfaceBuilder.DEFAULT.buildSurface(random, chunkIn, biomeIn, x, z, startHeight, noise, defaultBlock, defaultFluid, seaLevel, seed, STBiome.COARSE_DIRT_ANDESITE_GRAVEL_CONFIG);
		}
		else
		{
			SurfaceBuilder.DEFAULT.buildSurface(random, chunkIn, biomeIn, x, z, startHeight, noise, defaultBlock, defaultFluid, seaLevel, seed, STBiome.GRAVEL_GRAVEL_STONE_SURFACE_CONFIG);
		}


		//makes stone below sea level - 3 into third config
		for (int ypos = seaLevel-2; ypos >= 0; --ypos)
		{
			blockpos$Mutable.setPos(xpos, ypos, zpos);
			BlockState iblockstate2 = chunkIn.getBlockState(blockpos$Mutable);

			if(iblockstate2.getBlock() != null)
			{
				if (ypos < seaLevel-5) {
					if (iblockstate2.isSolid())
					{
						if (depthOfGravel == -1)
						{
							depthOfGravel = 0;
							chunkIn.setBlockState(blockpos$Mutable, config.getUnderWaterMaterial(), false);
						}
						else if (depthOfGravel > 0)
						{
							--depthOfGravel;
							chunkIn.setBlockState(blockpos$Mutable, config.getUnderWaterMaterial(), false);
						}
					}
					else
					{
						depthOfGravel = -1;
					}
				}
				
				if((iblockstate2 == Blocks.DIRT.getDefaultState() || iblockstate2 == Blocks.COARSE_DIRT.getDefaultState()))
				{
					chunkIn.setBlockState(blockpos$Mutable, config.getUnderWaterMaterial(), false);
				}
			}
		}

		//do stalactite last
		STBiome.LARGE_STALACTITE_SURFACE_BUILDER.buildSurface(random, chunkIn, biomeIn, x, z, startHeight, noise, defaultBlock, defaultFluid, seaLevel, seed, STBiome.STONE_STONE_STONE_SURFACE_CONFIG);
		STBiome.SMALL_STALACTITE_SURFACE_BUILDER.buildSurface(random, chunkIn, biomeIn, x, z, startHeight, noise, defaultBlock, defaultFluid, seaLevel, seed, STBiome.STONE_STONE_STONE_SURFACE_CONFIG);
	}

	public void setSeed(long seed)
	{
		STBiome.LARGE_STALACTITE_SURFACE_BUILDER.setSeed(seed);
		STBiome.SMALL_STALACTITE_SURFACE_BUILDER.setSeed(seed);
	}
}