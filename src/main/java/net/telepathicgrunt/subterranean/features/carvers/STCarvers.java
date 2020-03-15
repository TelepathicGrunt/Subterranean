package net.telepathicgrunt.subterranean.features.carvers;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.world.gen.carver.WorldCarver;
import net.minecraft.world.gen.feature.ProbabilityConfig;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.IForgeRegistry;
import net.telepathicgrunt.subterranean.utils.RegUtil;


public class STCarvers
{
	public static final BlockState AIR = Blocks.AIR.getDefaultState();

	public static WorldCarver<ProbabilityConfig> TEST_CARVER = new ShelfstoneCarver(ProbabilityConfig::deserialize);
	public static WorldCarver<ProbabilityConfig> SHELFSTONE_CARVER = new ShelfstoneCarver(ProbabilityConfig::deserialize);
	public static WorldCarver<ProbabilityConfig> BUBBLE_COLUMN_CARVER = new BubbleColumnCarver(ProbabilityConfig::deserialize);
	public static WorldCarver<ProbabilityConfig> GIANT_CAVERN_CARVER = new GiantCavernCarver(ProbabilityConfig::deserialize);


	public static void registerCarvers(RegistryEvent.Register<WorldCarver<?>> event)
	{
		IForgeRegistry<WorldCarver<?>> registry = event.getRegistry();
		RegUtil.register(registry, TEST_CARVER, "test_carver");
		RegUtil.register(registry, SHELFSTONE_CARVER, "shelf_stone_carver");
		RegUtil.register(registry, BUBBLE_COLUMN_CARVER, "bubble_column_carver");
		RegUtil.register(registry, GIANT_CAVERN_CARVER, "giant_cavern_carver");
	}
}
