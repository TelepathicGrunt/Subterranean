package net.telepathicgrunt.subterranean.features;

import net.minecraft.world.gen.feature.Feature;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.IForgeRegistry;


public class FeatureInit
{

	public static void registerFeatures(RegistryEvent.Register<Feature<?>> event)
	{
		IForgeRegistry<Feature<?>> registry = event.getRegistry();

		//Subterranean.LOGGER.debug("FEATURE REGISTER");
	}
}
