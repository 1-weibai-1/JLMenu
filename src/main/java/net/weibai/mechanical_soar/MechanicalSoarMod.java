package net.weibai.mechanical_soar;

import com.mojang.logging.LogUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.weibai.mechanical_soar.config.MSInitConfig;
import net.weibai.mechanical_soar.init.MSBlocks;
import org.slf4j.Logger;

import java.util.Locale;


@Mod(MechanicalSoarMod.MODID)
public class MechanicalSoarMod {
    public static final String MODID = "mechanical_soar";
    private static final Logger LOGGER = LogUtils.getLogger();
    public MechanicalSoarMod(FMLJavaModLoadingContext context) {
        IEventBus bus = context.getModEventBus();
        bus.addListener(this::commonSetup);

        MSBlocks.REGISTER.register(bus);


        MinecraftForge.EVENT_BUS.register(this);
        context.registerConfig(ModConfig.Type.CLIENT, MSInitConfig.SPEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
    }



    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        LOGGER.info("HELLO from server starting");
    }

    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            // Some client setup code
            LOGGER.info("HELLO FROM CLIENT SETUP");
            LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());
        }
    }
    public static ResourceLocation prefix(String name) {
        return new ResourceLocation(MODID, name.toLowerCase(Locale.ROOT));
    }
}
