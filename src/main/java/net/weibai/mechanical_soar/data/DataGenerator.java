package net.weibai.mechanical_soar.data;


import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.weibai.mechanical_soar.MechanicalSoarMod;
import net.weibai.mechanical_soar.data.loot.MSLootTableProvider;
import net.weibai.mechanical_soar.data.provider.MSBlockStateProvider;
import net.weibai.mechanical_soar.data.provider.MSItemModelProvider;
import net.weibai.mechanical_soar.data.provider.MSLanguageProvider;
import net.weibai.mechanical_soar.data.provider.MSRecipeProvider;

import java.util.concurrent.CompletableFuture;

@Mod.EventBusSubscriber(modid = MechanicalSoarMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerator {

    @SubscribeEvent
    public static void onGatherData(GatherDataEvent event) {
        net.minecraft.data.DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();

        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> provider = event.getLookupProvider();



//        MSModBlockTagProvider blockTagsProvider = new MSModBlockTagProvider(output, provider, existingFileHelper);
//        generator.addProvider(event.includeServer(), blockTagsProvider);
//        generator.addProvider(event.includeServer(), new MSModItemTagsProvider(
//                output, provider, blockTagsProvider.contentsGetter(), existingFileHelper));

        generator.addProvider(event.includeClient(),  new RegistryDataGenerator(output, provider));
        generator.addProvider(event.includeServer(), new MSBlockStateProvider(output, existingFileHelper));
        generator.addProvider(event.includeServer(), new MSItemModelProvider(output, existingFileHelper));
        generator.addProvider(event.includeServer(), new MSLootTableProvider(output));
        generator.addProvider(event.includeServer(), new MSRecipeProvider(output));
        generator.addProvider(event.includeClient(), new MSLanguageProvider(output, "en_us"));
        generator.addProvider(event.includeClient(), new MSLanguageProvider(output, "zh_cn"));
    }

}