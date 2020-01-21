package net.telepathicgrunt.subterranean.world.dimension;

import java.lang.reflect.Field;
import java.util.function.BiFunction;
import java.util.function.IntSupplier;

import org.apache.commons.lang3.tuple.Pair;

import com.google.common.collect.ImmutableList;

import net.minecraft.network.PacketBuffer;
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
import net.minecraftforge.fml.network.FMLHandshakeMessages;
import net.minecraftforge.fml.network.FMLNetworkConstants;
import net.minecraftforge.fml.network.simple.SimpleChannel;
import net.minecraftforge.registries.ForgeRegistries;
import net.telepathicgrunt.subterranean.Subterranean;


@SuppressWarnings("deprecation")
@Mod.EventBusSubscriber(modid = Subterranean.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class STDimension {

	public static final ModDimension SUBTERRANEAN = new ModDimension() {
        @Override
        public BiFunction<World, DimensionType, ? extends Dimension> getFactory() {
            return STWorldProvider::new;
        }
    };

    private static final ResourceLocation SUBTERRANEAN_ID = new ResourceLocation(Subterranean.MODID, "subterranean");
	
    
    //registers the dimension
    @Mod.EventBusSubscriber(modid = Subterranean.MODID)
    private static class ForgeEvents {
        @SubscribeEvent
        public static void registerDimensions(RegisterDimensionsEvent event) {
            if (DimensionType.byName(SUBTERRANEAN_ID) == null) {
                DimensionManager.registerDimension(SUBTERRANEAN_ID, SUBTERRANEAN, null, true);
            }
        }
    }

    @SubscribeEvent
    public static void registerModDimensions(RegistryEvent.Register<ModDimension> event) {
        RegUtil.generic(event.getRegistry()).add("ultraamplified", SUBTERRANEAN);
    }

    
    //This forge fix is from this open source code: https://github.com/Cryptic-Mushroom/The-Midnight/blob/1.14.4-dev/src/main/java/com/mushroom/midnight/common/registry/MidnightDimensions.java
    //Credit to Corail31 and Gegy
    static {
        // TODO: Temporary hack until Forge fix
        try {
            Field channelField = FMLNetworkConstants.class.getDeclaredField("handshakeChannel");
            channelField.setAccessible(true);

            SimpleChannel handshakeChannel = (SimpleChannel) channelField.get(null);
            handshakeChannel.messageBuilder(S2CDimensionSync.class, 100)
                    .loginIndex(S2CDimensionSync::getLoginIndex, S2CDimensionSync::setLoginIndex)
                    .decoder(S2CDimensionSync::decode)
                    .encoder(S2CDimensionSync::encode)
                    .buildLoginPacketList(isLocal -> {
                        if (isLocal) return ImmutableList.of();
                        return ImmutableList.of(Pair.of("Ultra Amplified Dim Sync", new S2CDimensionSync(STDimension.subterranean())));
                    })
                    .consumer((msg, ctx) -> {
                        if (DimensionManager.getRegistry().getByValue(msg.id) == null) {
                            DimensionManager.registerDimensionInternal(msg.id, msg.name, msg.dimension, null, msg.skyLight);
                        }
                        ctx.get().setPacketHandled(true);
                        handshakeChannel.reply(new FMLHandshakeMessages.C2SAcknowledge(), ctx.get());
                    })
                    .add();
        } catch (ReflectiveOperationException e) {
        	Subterranean.LOGGER.error("Failed to add dimension sync to handshake channel", e);
        }
    }
	

    public static DimensionType subterranean() {
        return DimensionType.byName(SUBTERRANEAN_ID);
    }
    
    
    //This forge fix is from this open source code: https://github.com/Cryptic-Mushroom/The-Midnight/blob/1.14.4-dev/src/main/java/com/mushroom/midnight/common/registry/MidnightDimensions.java
    //Credit to Corail31 and Gegy
    public static class S2CDimensionSync implements IntSupplier{
        final int id;
        final ResourceLocation name;
        final ModDimension dimension;
        final boolean skyLight;

        private int loginIndex;

        public S2CDimensionSync(DimensionType dimensionType) {
            this.id = dimensionType.getId() + 1;
            this.name = DimensionType.getKey(dimensionType);
            this.dimension = dimensionType.getModType();
            this.skyLight = dimensionType.hasSkyLight();
        }

        S2CDimensionSync(int id, ResourceLocation name, ModDimension dimension, boolean skyLight) {
            this.id = id;
            this.name = name;
            this.dimension = dimension;
            this.skyLight = skyLight;
        }

        void setLoginIndex(final int loginIndex) {
            this.loginIndex = loginIndex;
        }

        int getLoginIndex() {
            return this.loginIndex;
        }

        void encode(PacketBuffer buffer) {
            buffer.writeInt(this.id);
            buffer.writeResourceLocation(this.name);
            buffer.writeResourceLocation(this.dimension.getRegistryName());
            buffer.writeBoolean(this.skyLight);
        }

        public static S2CDimensionSync decode(PacketBuffer buffer) {
            int id = buffer.readInt();
            ResourceLocation name = buffer.readResourceLocation();
            ModDimension dimension = ForgeRegistries.MOD_DIMENSIONS.getValue(buffer.readResourceLocation());
            boolean skyLight = buffer.readBoolean();

            return new S2CDimensionSync(id, name, dimension, skyLight);
        }

		@Override
		public int getAsInt() {
	         return this.getLoginIndex();
		}
    }
}