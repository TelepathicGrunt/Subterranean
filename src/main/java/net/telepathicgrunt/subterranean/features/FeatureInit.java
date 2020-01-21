package net.telepathicgrunt.subterranean.features;

import net.minecraft.world.gen.carver.WorldCarver;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.ProbabilityConfig;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.IForgeRegistry;
import net.telepathicgrunt.subterranean.Subterranean;
import net.telepathicgrunt.subterranean.features.carvers.TestCarver;

public class FeatureInit
{

    public static void registerFeatures(RegistryEvent.Register<Feature<?>> event)
    {
    	IForgeRegistry<Feature<?>> registry = event.getRegistry();

        Subterranean.LOGGER.debug("FEATURE REGISTER");
    }
    
    public static WorldCarver<ProbabilityConfig> STALACTITE_FILLER = new TestCarver(ProbabilityConfig::deserialize);
}
