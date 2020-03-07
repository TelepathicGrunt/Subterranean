package net.telepathicgrunt.subterranean.features.carvers;

import java.util.BitSet;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;

import com.mojang.datafixers.Dynamic;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.carver.CaveWorldCarver;
import net.minecraft.world.gen.feature.ProbabilityConfig;


public class ShelfstoneCarver extends CaveWorldCarver
{

	public ShelfstoneCarver(Function<Dynamic<?>, ? extends ProbabilityConfig> deserialize)
	{
		super(deserialize, 256);
	}


	/**
	 * Height of the caves Can be randomized with random param
	 */
	@Override
	protected int generateCaveStartY(Random random)
	{
		return 32 + random.nextInt(6);
	}


	/**
	 * This is what calls carveCave and carveTunnel. Only here, we are doing just carveCave.
	 */
	@Override
	public boolean carve(IChunk chunk, Function<BlockPos, Biome> biomeFunction, Random random, int unusedInt1, int xChunk, int zChunk, int unusedInt2, int unusedInt3, BitSet caveMask, ProbabilityConfig chanceConfig)
	{
		double x = xChunk * 16;// + random.nextInt(16));   // Randomizes spot of each room
		double y = this.generateCaveStartY(random);  // Lowers each room by 20 blocks so they are stacked
		double z = zChunk * 16; //+ random.nextInt(16));   // Randomizes spot of each room

		float caveRadius = 10.0F + random.nextFloat() * 13.0F; // How thick the cave is
		this.carveCave(chunk, biomeFunction, random.nextLong(), unusedInt1, unusedInt2, unusedInt3, x, y, z, caveRadius + random.nextFloat() * 10, caveRadius + random.nextFloat() * 10, random.nextFloat() * 0.4F + 0.6F, caveMask);
	
		return true;
	}


	@Override
	protected boolean carveAtPoint(IChunk chunk, Function<BlockPos, Biome> biomeFunction, BitSet carvingMask, Random random, BlockPos.Mutable posHere, BlockPos.Mutable posAbove, BlockPos.Mutable posBelow, int unusedInt1, int unusedInt2, int unusedInt3, int globalX, int globalZ, int x, int y, int z, AtomicBoolean foundSurface)
	{
		int index = x | z << 4 | y << 8; //Not sure what this specific section is for. I know the mask is used so other features can find caves space.
		carvingMask.set(index);
		posHere.setPos(globalX, y, globalZ);

		BlockState state = chunk.getBlockState(posHere);
		BlockState stateAbove = chunk.getBlockState(posAbove.setPos(posHere).move(Direction.UP));

		if (!this.canCarveBlock(state, stateAbove))  // Makes sure we aren't carving a non terrain or liquid space
		{
			return false;
		}

		if (y > 23) // carves air when above lava level
		{
			chunk.setBlockState(posHere, STCarvers.THIN_AIR, false);
		}
		else // sets water at sea level and below
		{
			chunk.setBlockState(posHere, WATER.getBlockState(), false);
		}

		return true;
	}


	@Override
	protected boolean canCarveBlock(BlockState state, BlockState aboveState)
	{
		if (state.getBlock() == Blocks.BEDROCK)
			return false;

		Material material = state.getMaterial();
		Material aboveMaterial = aboveState.getMaterial();
		return (material == Material.SAND || material == Material.ROCK || material == Material.EARTH || material == Material.ORGANIC) && material != Material.LAVA && aboveMaterial != Material.LAVA;
	}

	
	protected void carveCave(IChunk chunk, Function<BlockPos, Biome> biomeFunction, long seed, int yChunk, int xChunk, int zChunk, double x, double y, double z, double caveRadiusX, double caveRadiusZ, double heightModifier, BitSet caveMask)
	{
		double finalRadiusX = 1.5D + MathHelper.sin(((float) Math.PI / 2F)) * caveRadiusX;
		double finalRadiusZ = 1.5D + MathHelper.sin(((float) Math.PI / 2F)) * caveRadiusZ;
		double finalHeight = ((finalRadiusX + finalRadiusZ) * 0.5D) * heightModifier;
		this.carveRegion(chunk, biomeFunction, seed, yChunk, xChunk, zChunk, x + 1.0D, y, z, finalRadiusX, finalRadiusZ, finalHeight, caveMask);
	}

	protected boolean carveRegion(IChunk chunk, Function<BlockPos, Biome> biomeFunction, long seed, int yChunk, int xChunk, int zChunk, double x, double y, double z, double caveRadiusX, double caveRadiusZ, double heightModifier, BitSet caveMask)
	{
		Random random = new Random(seed + xChunk + zChunk);
		double trueX = xChunk * 16 + 8;
		double trueZ = zChunk * 16 + 8;
		if (!(x < trueX - 16.0D - caveRadiusX * 2.0D) && !(z < trueZ - 16.0D - caveRadiusZ * 2.0D) && !(x > trueX + 16.0D + caveRadiusX * 2.0D) && !(z > trueZ + 16.0D + caveRadiusZ * 2.0D))
		{
			int xMin = Math.max(MathHelper.floor(x - caveRadiusX) - xChunk * 16 - 1, 0);
			int xMax = Math.min(MathHelper.floor(x + caveRadiusX) - xChunk * 16 + 1, 16);
			int yMin = Math.max(MathHelper.floor(y - heightModifier) - 1, 1);
			int yMax = Math.min(MathHelper.floor(y + heightModifier) + 1, this.maxHeight - 8);
			int zMin = Math.max(MathHelper.floor(z - caveRadiusZ) - zChunk * 16 - 1, 0);
			int zMax = Math.min(MathHelper.floor(z + caveRadiusZ) - zChunk * 16 + 1, 16);
			boolean flag = false;
			BlockPos.Mutable blockpos$mutable = new BlockPos.Mutable();
			BlockPos.Mutable blockpos$mutable1 = new BlockPos.Mutable();
			BlockPos.Mutable blockpos$mutable2 = new BlockPos.Mutable();

			for (int xPos = xMin; xPos < xMax; ++xPos)
			{
				int xCord = xPos + xChunk * 16;
				double xInChunk = (xCord + 0.5D - x) / caveRadiusX;

				for (int zPos = zMin; zPos < zMax; ++zPos)
				{
					int zCord = zPos + zChunk * 16;
					double zInChunk = (zCord + 0.5D - z) / caveRadiusZ;
					AtomicBoolean atomicboolean = new AtomicBoolean(false);
					
					for (int yPos = yMax; yPos > yMin; --yPos)
					{
						double yInChunk = (yPos + 0.5D - y) / heightModifier;
						
						if (!(xInChunk * xInChunk + zInChunk * zInChunk + yInChunk * yInChunk >= 1.0D))
						{
							flag |= this.carveAtPoint(chunk, biomeFunction, caveMask, random, blockpos$mutable, blockpos$mutable1, blockpos$mutable2, yChunk, xChunk, zChunk, xCord, zCord, xPos, yPos, zPos, atomicboolean);
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
}