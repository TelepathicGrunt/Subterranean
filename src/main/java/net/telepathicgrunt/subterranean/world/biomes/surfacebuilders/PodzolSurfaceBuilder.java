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


public class PodzolSurfaceBuilder extends SurfaceBuilder<SurfaceBuilderConfig>
{
	public PodzolSurfaceBuilder(Function<Dynamic<?>, ? extends SurfaceBuilderConfig> p_i51310_1_)
	{
		super(p_i51310_1_);
	}


	@Override
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

			if (iblockstate2.getMaterial() == Material.AIR)
			{
				heightOfLand = ypos - 1;
				break;
			}
		}

		double noisemod = Math.abs(noise % 2.0D);
		if (heightOfLand > 22)
		{
			if (noisemod < 0.3D)
			{
				SurfaceBuilder.DEFAULT.buildSurface(random, chunkIn, biomeIn, x, z, startHeight, noise, defaultBlock, defaultFluid, seaLevel, seed, PODZOL_DIRT_GRAVEL_CONFIG);
			}
			else if (noisemod < 0.6D || noisemod > 1.7)
			{
				SurfaceBuilder.DEFAULT.buildSurface(random, chunkIn, biomeIn, x, z, startHeight, noise, defaultBlock, defaultFluid, seaLevel, seed, STSurfaceBuilders.COARSE_DIRT_ANDESITE_GRAVEL_CONFIG);
			}
			else if (noisemod < 0.9 || noisemod > 1.4)
			{
				SurfaceBuilder.DEFAULT.buildSurface(random, chunkIn, biomeIn, x, z, startHeight, noise, defaultBlock, defaultFluid, seaLevel, seed, DIRT_DIRT_GRAVEL_CONFIG);
			}
			else
			{
				SurfaceBuilder.DEFAULT.buildSurface(random, chunkIn, biomeIn, x, z, startHeight, noise, defaultBlock, defaultFluid, seaLevel, seed, config);
			}
		}
		else if (heightOfLand > 17)
		{
			SurfaceBuilder.DEFAULT.buildSurface(random, chunkIn, biomeIn, x, z, startHeight, noise, defaultBlock, defaultFluid, seaLevel, seed, STSurfaceBuilders.COARSE_DIRT_ANDESITE_GRAVEL_CONFIG);
		}
		else
		{
			SurfaceBuilder.DEFAULT.buildSurface(random, chunkIn, biomeIn, x, z, startHeight, noise, defaultBlock, defaultFluid, seaLevel, seed, config);
		}

		//makes stone below sea level - 3 into third config
		for (int ypos = seaLevel - 2; ypos >= 0; --ypos)
		{
			blockpos$Mutable.setPos(xpos, ypos, zpos);
			BlockState iblockstate2 = chunkIn.getBlockState(blockpos$Mutable);

			if (iblockstate2.getBlock() != null)
			{
				if (ypos < seaLevel - 5)
				{
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

				if ((iblockstate2 == Blocks.DIRT.getDefaultState() || iblockstate2 == Blocks.COARSE_DIRT.getDefaultState()))
				{
					chunkIn.setBlockState(blockpos$Mutable, config.getUnderWaterMaterial(), false);
				}
			}
		}

		//do stalactite last
		STSurfaceBuilders.LARGE_STALACTITE_SURFACE_BUILDER.buildSurface(random, chunkIn, biomeIn, x, z, startHeight, noise, defaultBlock, defaultFluid, seaLevel, seed, STSurfaceBuilders.STONE_STONE_STONE_SURFACE_CONFIG);
		STSurfaceBuilders.SMALL_STALACTITE_SURFACE_BUILDER.buildSurface(random, chunkIn, biomeIn, x, z, startHeight, noise, defaultBlock, defaultFluid, seaLevel, seed, STSurfaceBuilders.STONE_STONE_STONE_SURFACE_CONFIG);
	}


	@Override
	public void setSeed(long seed)
	{
		STSurfaceBuilders.LARGE_STALACTITE_SURFACE_BUILDER.setSeed(seed);
		STSurfaceBuilders.SMALL_STALACTITE_SURFACE_BUILDER.setSeed(seed);
	}
}