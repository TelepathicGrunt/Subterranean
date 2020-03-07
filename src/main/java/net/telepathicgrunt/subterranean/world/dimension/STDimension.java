package net.telepathicgrunt.subterranean.world.dimension;

import java.util.function.BiFunction;

import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.dimension.Dimension;
import net.minecraft.world.dimension.DimensionType;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.ModDimension;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.world.RegisterDimensionsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.telepathicgrunt.subterranean.RegUtil;
import net.telepathicgrunt.subterranean.Subterranean;


@Mod.EventBusSubscriber(modid = Subterranean.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class STDimension
{

	public static final ModDimension SUBTERRANEAN = new ModDimension()
	{
		@Override
		public BiFunction<World, DimensionType, ? extends Dimension> getFactory()
		{
			return STWorldProvider::new;
		}
	};

	private static final ResourceLocation SUBTERRANEAN_ID = new ResourceLocation(Subterranean.MODID, "subterranean");

	//registers the dimension
	@Mod.EventBusSubscriber(modid = Subterranean.MODID)
	private static class ForgeEvents
	{
		@SubscribeEvent
		public static void registerDimensions(RegisterDimensionsEvent event)
		{
			if (DimensionType.byName(SUBTERRANEAN_ID) == null)
			{
				DimensionManager.registerDimension(SUBTERRANEAN_ID, SUBTERRANEAN, null, true);
			}
		}
	}


	@SubscribeEvent
	public static void registerModDimensions(RegistryEvent.Register<ModDimension> event)
	{
		RegUtil.generic(event.getRegistry()).add("ultraamplified", SUBTERRANEAN);
	}


	public static DimensionType subterranean()
	{
		return DimensionType.byName(SUBTERRANEAN_ID);
	}
}
