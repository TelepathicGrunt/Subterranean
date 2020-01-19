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
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.carver.CaveWorldCarver;
import net.minecraft.world.gen.feature.ProbabilityConfig;


public class BubbleColumnCarver extends CaveWorldCarver
{

	public BubbleColumnCarver(Function<Dynamic<?>, ? extends ProbabilityConfig> deserialize)
	{
		super(deserialize, 256);
	}


	/**
	 * Height of the caves Can be randomized with random param
	 */
	@Override
	protected int generateCaveStartY(Random random)
	{
		return 230;
	}


	/**
	 * This is what calls carveCave and carveTunnel. Only here, we are doing just carveCave.
	 */
	public boolean carve(IChunk chunk, Function<BlockPos, Biome> biomeFunction, Random random, int unusedInt1, int xChunk, int zChunk, int unusedInt2, int unusedInt3, BitSet caveMask, ProbabilityConfig chanceConfig)
	{
		int numberOfRooms = 12;

		for (int roomCount = 0; roomCount < numberOfRooms; ++roomCount)
		{
			double x = (double) (xChunk * 16);// + random.nextInt(16));   // Randomizes spot of each room
			double y = (double) this.generateCaveStartY(random) - roomCount * 20;  // Lowers each room by 20 blocks so they are stacked
			double z = (double) (zChunk * 16); //+ random.nextInt(16));   // Randomizes spot of each room
			
			float caveRadius = 20.0F + random.nextFloat() * 10.0F; // How thick the cave is
			this.carveCave(chunk, biomeFunction, random.nextLong(), unusedInt1, unusedInt2, unusedInt3, x, y, z, caveRadius, 0.5D, caveMask);
		}

		return true;
	}


	@Override
	protected boolean carveAtPoint(IChunk chunk, Function<BlockPos, Biome> biomeFunction, BitSet carvingMask, Random random, BlockPos.Mutable posHere, BlockPos.Mutable posAbove, BlockPos.Mutable posBelow, int unusedInt1, int unusedInt2, int unusedInt3, int globalX, int globalZ, int x, int y, int z, AtomicBoolean foundSurface)
	{
		int index = x | z << 4 | y << 8; //Not sure what this specific section is for. I know the mask is used so other features can find caves space.
		if (carvingMask.get(index))
		{
			return false;
		}

		carvingMask.set(index);
		posHere.setPos(globalX, y, globalZ);

		BlockState state = chunk.getBlockState(posHere);
		BlockState stateAbove = chunk.getBlockState(posAbove.setPos(posHere).move(Direction.UP));
		if (!this.canCarveBlock(state, stateAbove))  // Makes sure we aren't carving a non terrain or liquid space
		{
			return false;
		}

		if (y > 10) // carves air when above lava level
		{
			chunk.setBlockState(posHere, CAVE_AIR, false);
		}
		else // sets lava below lava level
		{
			chunk.setBlockState(posHere, LAVA.getBlockState(), false);
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
		return (material == Material.ROCK || material == Material.EARTH || material == Material.ORGANIC) && material != Material.WATER && material != Material.LAVA && aboveMaterial != Material.WATER && aboveMaterial != Material.LAVA;
	}

}