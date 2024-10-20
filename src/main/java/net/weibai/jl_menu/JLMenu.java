package net.weibai.jl_menu;

import com.mojang.logging.LogUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
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
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.weibai.jl_menu.config.MSInitConfig;
import net.weibai.jl_menu.item.JLMenuItem;
import net.weibai.jl_menu.json.ReadJsonFile;
import org.slf4j.Logger;

import java.io.File;
import java.util.Locale;


@Mod(JLMenu.MODID)
public class JLMenu {
    public static final String MODID = "jl_menu";
    public static final Logger LOGGER = LogUtils.getLogger();
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);
    public JLMenu(FMLJavaModLoadingContext context) {
        IEventBus bus = context.getModEventBus();
        bus.addListener(this::commonSetup);
        bus.addListener(this::serverStarting);
        ITEMS.register(bus);


        MinecraftForge.EVENT_BUS.register(this);
        context.registerConfig(ModConfig.Type.SERVER, MSInitConfig.SPEC);
    }
    public void serverStarting(FMLCommonSetupEvent event) {
        File jlmenuDir = ReadJsonFile.jlmenuDir;
        if (!jlmenuDir.exists()) {
            jlmenuDir.mkdirs();
        }
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
    public static final RegistryObject<Item> MENU_ITEM = ITEMS.register("menu_item",
            () -> new JLMenuItem(new Item.Properties()));

}
