package net.telepathicgrunt.subterranean.features.carvers;

import java.util.BitSet;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;

import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.Dynamic;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.carver.CaveWorldCarver;
import net.minecraft.world.gen.feature.ProbabilityConfig;


public class GiantCavernCarver extends CaveWorldCarver
{
	protected Set<Fluid> carvableFluids = ImmutableSet.of(Fluids.WATER, Fluids.LAVA, Fluids.FLOWING_WATER);


	public GiantCavernCarver(Function<Dynamic<?>, ? extends ProbabilityConfig> deserialize)
	{
		super(deserialize, 256);
	}


	/**
	 * Height of the caves Can be randomized with random param
	 */
	@Override
	protected int generateCaveStartY(Random random)
	{
		return 114 + random.nextInt(9);
	}


	/**
	 * This is what calls carveCave and carveTunnel. Only here, we are doing just carveCave.
	 */
	@Override
	public boolean carve(IChunk chunk, Function<BlockPos, Biome> biomeFunction, Random random, int yChunk, int xChunk, int zChunk, int xChunk2, int zChunk2, BitSet caveMask, ProbabilityConfig chanceConfig)
	{
		double x = xChunk * 16;// + random.nextInt(16));   // Randomizes spot of each room
		double y = this.generateCaveStartY(random);  // 
		double z = zChunk * 16; //+ random.nextInt(16));   // Randomizes spot of each room

		float caveRadius = 31.5F + random.nextFloat() * 15.0F; // How thick the cave is
		boolean lavaBottom = random.nextFloat() < 0.4F;
		this.carveCave(chunk, biomeFunction, random.nextLong(), yChunk, xChunk2, zChunk2, x, y, z, caveRadius, 2.2D, caveMask, lavaBottom);

		return true;
	}

	
	protected void carveCave(IChunk chunk, Function<BlockPos, Biome> biomeFunction, long seed, int yChunk, int xChunk, int zChunk, double x, double y, double z, float caveRadius, double heightModifier, BitSet caveMask, boolean lavaBottom)
	{
		double finalRadius = 1.5D + MathHelper.sin(((float) Math.PI / 2F)) * caveRadius;
		double finalHeight = finalRadius * heightModifier;
		this.carveRegion(chunk, biomeFunction, seed, yChunk, xChunk, zChunk, x + 1.0D, y, z, finalRadius, finalHeight, caveMask, lavaBottom);
	}


	protected boolean carveRegion(IChunk chunk, Function<BlockPos, Biome> biomeFunction, long seed, int yChunk, int xChunk, int zChunk, double x, double y, double z, double caveRadius, double heightModifier, BitSet caveMask, boolean lavaBottom)
	{
		Random random = new Random(seed + xChunk + zChunk);
		double trueX = xChunk * 16 + 8;
		double trueZ = zChunk * 16 + 8;
		boolean bottom = false;
		if (!(x < trueX - 16.0D - caveRadius * 2.0D) && !(z < trueZ - 16.0D - caveRadius * 2.0D) && !(x > trueX + 16.0D + caveRadius * 2.0D) && !(z > trueZ + 16.0D + caveRadius * 2.0D))
		{
			int xMin = Math.max(MathHelper.floor(x - caveRadius) - xChunk * 16 - 1, 0);
			int xMax = Math.min(MathHelper.floor(x + caveRadius) - xChunk * 16 + 1, 16);
			int yMin = Math.max(MathHelper.floor(y - heightModifier) - 1, 1);
			int yMax = Math.min(MathHelper.floor(y + heightModifier) + 1, this.maxHeight - 8);
			int zMin = Math.max(MathHelper.floor(z - caveRadius) - zChunk * 16 - 1, 0);
			int zMax = Math.min(MathHelper.floor(z + caveRadius) - zChunk * 16 + 1, 16);
			boolean flag = false;
			BlockPos.Mutable blockpos$mutable = new BlockPos.Mutable();
			BlockPos.Mutable blockpos$mutable1 = new BlockPos.Mutable();
			BlockPos.Mutable blockpos$mutable2 = new BlockPos.Mutable();

			for (int xPos = xMin; xPos < xMax; ++xPos)
			{
				int xCord = xPos + xChunk * 16;
				double xInChunk = (xCord + 0.5D - x) / caveRadius;

				for (int zPos = zMin; zPos < zMax; ++zPos)
				{
					int zCord = zPos + zChunk * 16;
					double zInChunk = (zCord + 0.5D - z) / caveRadius;
					AtomicBoolean atomicboolean = new AtomicBoolean(false);
					bottom = false;
					
					for (int yPos = yMax; yPos > yMin; --yPos)
					{
						double yInChunk = (yPos + 0.5D - y) / heightModifier;
						
						if (!(xInChunk * xInChunk + zInChunk * zInChunk + yInChunk * yInChunk >= 1.0D))
						{
							if(yPos == yMin + 2) 
							{
								if(lavaBottom) 
								{
									bottom = true;
								}
							}
							flag |= this.carveAtPoint(chunk, biomeFunction, caveMask, random, blockpos$mutable, blockpos$mutable1, blockpos$mutable2, yChunk, xChunk, zChunk, xCord, zCord, xPos, yPos, zPos, atomicboolean, bottom);
						}
					}
				}
			}

			return flag;
		}
		else
		{
			return false;
		}
	}


	protected boolean carveAtPoint(IChunk chunk, Function<BlockPos, Biome> biomeFunction, BitSet carvingMask, Random random, BlockPos.Mutable posHere, BlockPos.Mutable posAbove, BlockPos.Mutable posBelow, int yChunk, int xChunk, int zChunk, int globalX, int globalZ, int x, int y, int z, AtomicBoolean foundSurface, boolean bottom)
	{
		int index = x | z << 4 | y << 8; //Not sure what this specific section is for. I know the mask is used so other features can find caves space.
		//		if (carvingMask.get(index))
		//		{
		//			return false;
		//		}

		carvingMask.set(index);
		posHere.setPos(globalX, y, globalZ);

		BlockState state = chunk.getBlockState(posHere);
		BlockState stateAbove = chunk.getBlockState(posAbove.setPos(posHere).move(Direction.UP));
		if (!this.canCarveBlock(state, stateAbove))  // Makes sure we aren't carving a non terrain or liquid space
		{
			return false;
		}

		if ((bottom && stateAbove.getMaterial() != Material.WATER) || stateAbove.getMaterial() == Material.LAVA)
		{
			chunk.setBlockState(posHere, LAVA.getBlockState(), false);
		}
		else if (y <= 35) // sets lava below lava level
		{
			chunk.setBlockState(posHere, WATER.getBlockState(), false);
		}
		else  // carves air when above lava level
		{
			chunk.setBlockState(posHere, STCarvers.THIN_AIR, false);
		}

		return true;
	}


	@Override
	protected boolean canCarveBlock(BlockState state, BlockState aboveState)
	{
		if (state.getBlock() == Blocks.BEDROCK)
			return false;

		Material material = state.getMaterial();
		return (material == Material.LAVA || material == Material.ROCK || material == Material.EARTH || material == Material.ORGANIC || material == Material.SAND);
	}

}