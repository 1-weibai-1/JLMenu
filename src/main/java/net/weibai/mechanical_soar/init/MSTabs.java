package net.weibai.mechanical_soar.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.weibai.mechanical_soar.MechanicalSoarMod;


@Mod.EventBusSubscriber(modid = MechanicalSoarMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class MSTabs {
    public static final DeferredRegister<CreativeModeTab> REGISTER = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MechanicalSoarMod.MODID);

    public static final RegistryObject<CreativeModeTab> EXAMPLE_TAB = REGISTER
            .register("Mechanical materials",
                    () -> CreativeModeTab.builder()
                            .title(Component.translatable("item_group."+MechanicalSoarMod.MODID+"mechanical_materials"))
                            .withTabsBefore(CreativeModeTabs.COMBAT)
                            .icon(() -> MSItems.EXAMPLE_ITEM.get().getDefaultInstance())
                            .displayItems((parameters, output) -> {
                                output.accept(MSItems.EXAMPLE_ITEM.get()); // Add the example item to the tab. For your own tabs, this method is preferred over the event





                            }).build());
    @SubscribeEvent
    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS)
            event.accept(MSItems.EXAMPLE_ITEM.get());
    }

}
