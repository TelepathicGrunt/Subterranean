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
	 * Height of the caves Can be randomized with random parameter
	 */
	@Override
	protected int generateCaveStartY(Random random)
	{
		return 100;
	}


	/**
	 * This is what calls carveCave and carveTunnel. Here, we are doing just carveCave as we don't need any tunnels. Just
	 * the cave room.
	 */
	public boolean carve(IChunk chunk, Function<BlockPos, Biome> biomeFunction, Random random, int seaLevel, int xChunk1, int zChunk1, int xChunk2, int zChunk2, BitSet caveMask, ProbabilityConfig chanceConfig)
	{
		int numberOfRooms = 4; // 4 sphere will be carved out.

		for (int roomCount = 0; roomCount < numberOfRooms; ++roomCount)
		{
			double x = (double) (xChunk1 * 16);// + random.nextInt(16));   // Uncomment to randomizes spot of each room
			double y = (double) this.generateCaveStartY(random) - roomCount * 20;  // Lowers each room/sphere by 20 blocks so they are stacked
			double z = (double) (zChunk1 * 16); //+ random.nextInt(16));   // Uncomment to randomizes spot of each room

			float caveRadius = 20.0F + random.nextFloat() * 10.0F; // How big the cave sphere is (radius)

			// The 0.5D is multiplied to the radius for the y direction. So this sphere will be squished vertically by half of the full radius.  
			this.carveCave(chunk, biomeFunction, random.nextLong(), seaLevel, xChunk2, zChunk2, x, y, z, caveRadius, 0.5D, caveMask);
		}

		return true;
	}


	/**
	 * Does the actual carving. Replacing any valid stone with cave air. Though the carver could be customized to place
	 * blocks instead which would be interesting.
	 */
	@Override
	protected boolean carveAtPoint(IChunk chunk, Function<BlockPos, Biome> biomeFunction, BitSet carvingMask, Random random, BlockPos.Mutable posHere, BlockPos.Mutable posAbove, BlockPos.Mutable posBelow, int seaLevel, int xChunk, int zChunk, int globalX, int globalZ, int x, int y, int z, AtomicBoolean foundSurface)
	{
		/*
		 * Not sure what this specific section is doing. I know this mask is used so other features can find caves space. Used
		 * by SeaGrass to generate at cave openings underwater
		 */
		int index = x | z << 4 | y << 8;
		if (carvingMask.get(index))
		{
			return false;
		}
		carvingMask.set(index);

		posHere.setPos(globalX, y, globalZ);
		BlockState blockState = chunk.getBlockState(posHere);
		BlockState blockStateAbove = chunk.getBlockState(posAbove.setPos(posHere).move(Direction.UP));
		if (!this.canCarveBlock(blockState, blockStateAbove))  // Makes sure we aren't carving a non terrain or liquid space
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


	/**
	 * Used to determine what blocks the carver can carve through. Can be highly customized.
	 */
	@Override
	protected boolean canCarveBlock(BlockState blockState, BlockState aboveBlockState)
	{
		if (blockState.getBlock() == Blocks.BEDROCK)
			return false;

		Material material = blockState.getMaterial();
		return ((material == Material.ROCK || material == Material.EARTH || material == Material.ORGANIC) && 
				material != Material.WATER && 
				material != Material.LAVA);
	}

}