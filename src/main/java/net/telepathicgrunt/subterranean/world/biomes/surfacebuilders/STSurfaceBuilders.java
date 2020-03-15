package net.telepathicgrunt.subterranean.world.biomes.surfacebuilders;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.IForgeRegistry;
import net.telepathicgrunt.subterranean.utils.RegUtil;


public class STSurfaceBuilders
{

	protected static final BlockState							STONE														= Blocks.STONE.getDefaultState();
	protected static final BlockState							COBBLESTONE													= Blocks.COBBLESTONE.getDefaultState();
	protected static final BlockState							DIRT														= Blocks.DIRT.getDefaultState();
	protected static final BlockState							CLAY														= Blocks.CLAY.getDefaultState();
	protected static final BlockState							COARSE_DIRT													= Blocks.COARSE_DIRT.getDefaultState();
	protected static final BlockState							GRAVEL														= Blocks.GRAVEL.getDefaultState();
	protected static final BlockState							LIGHT_GRAY_CONCRETE											= Blocks.LIGHT_GRAY_CONCRETE.getDefaultState();
	protected static final BlockState							LIGHT_GRAY_CONCRETE_POWDER									= Blocks.LIGHT_GRAY_CONCRETE_POWDER.getDefaultState();
	protected static final BlockState							GRAY_TERRACOTTA												= Blocks.GRAY_TERRACOTTA.getDefaultState();
	protected static final BlockState							DEAD_BRAIN_CORAL											= Blocks.DEAD_BRAIN_CORAL_BLOCK.getDefaultState();
	protected static final BlockState							DEAD_BUBBLE_CORAL											= Blocks.DEAD_BUBBLE_CORAL_BLOCK.getDefaultState();
	protected static final BlockState							DEAD_HORN_CORAL												= Blocks.DEAD_HORN_CORAL_BLOCK.getDefaultState();
	protected static final BlockState							DEAD_TUBE_CORAL												= Blocks.DEAD_TUBE_CORAL_BLOCK.getDefaultState();
	protected static final BlockState							DEAD_FIRE_CORAL												= Blocks.DEAD_FIRE_CORAL_BLOCK.getDefaultState();
	protected static final BlockState							GRANITE														= Blocks.GRANITE.getDefaultState();
	protected static final BlockState							DIORITE														= Blocks.DIORITE.getDefaultState();
	protected static final BlockState							ANDESITE													= Blocks.ANDESITE.getDefaultState();

	public static final SurfaceBuilderConfig					STONE_ANDESITE_GRAVEL_SURFACE_CONFIG						= new SurfaceBuilderConfig(STONE, ANDESITE, GRAVEL);
	public static final SurfaceBuilderConfig					COARSE_DIRT_ANDESITE_GRAVEL_CONFIG							= new SurfaceBuilderConfig(COARSE_DIRT, ANDESITE, GRAVEL);
	public static final SurfaceBuilderConfig					STONE_STONE_STONE_SURFACE_CONFIG							= new SurfaceBuilderConfig(STONE, STONE, STONE);
	public static final SurfaceBuilderConfig					ANDESITE_GRAVEL_STONE_SURFACE_CONFIG						= new SurfaceBuilderConfig(ANDESITE, GRAVEL, STONE);
	public static final SurfaceBuilderConfig					GRAVEL_GRAVEL_STONE_SURFACE_CONFIG							= new SurfaceBuilderConfig(GRAVEL, GRAVEL, STONE);
	public static final SurfaceBuilderConfig					LIGHT_GRAY_CONCRETE_POWDER_ANDESITE_STONE_SURFACE_CONFIG	= new SurfaceBuilderConfig(LIGHT_GRAY_CONCRETE_POWDER, ANDESITE, STONE);

	public static final SurfaceBuilder<SurfaceBuilderConfig>	STONE_SURFACE_BUILDER										= new StoneSurfaceBuilder(SurfaceBuilderConfig::deserialize);
	public static final SurfaceBuilder<SurfaceBuilderConfig>	PODZOL_SURFACE_BUILDER										= new PodzolSurfaceBuilder(SurfaceBuilderConfig::deserialize);
	public static final SurfaceBuilder<SurfaceBuilderConfig>	LARGE_STALACTITE_SURFACE_BUILDER							= new LargeStalactiteSurfaceBuilder(SurfaceBuilderConfig::deserialize);
	public static final SurfaceBuilder<SurfaceBuilderConfig>	SMALL_STALACTITE_SURFACE_BUILDER							= new SmallStalactiteSurfaceBuilder(SurfaceBuilderConfig::deserialize);


	public static void registerSurfaceBuilders(RegistryEvent.Register<SurfaceBuilder<?>> event)
	{
		IForgeRegistry<SurfaceBuilder<?>> registry = event.getRegistry();

		RegUtil.register(registry, STONE_SURFACE_BUILDER, "stone_surface_builder");
		RegUtil.register(registry, PODZOL_SURFACE_BUILDER, "podzol_surface_builder");
		RegUtil.register(registry, LARGE_STALACTITE_SURFACE_BUILDER, "large_stalactite_surface_builder");
		RegUtil.register(registry, SMALL_STALACTITE_SURFACE_BUILDER, "small_stalactite_surface_builder");
	}
}
