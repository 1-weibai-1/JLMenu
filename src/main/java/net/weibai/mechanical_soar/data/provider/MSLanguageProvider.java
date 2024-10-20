package net.weibai.mechanical_soar.data.provider;

import com.google.gson.JsonObject;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.weibai.mechanical_soar.MechanicalSoarMod;
import org.jetbrains.annotations.NotNull;

import javax.annotation.ParametersAreNonnullByDefault;
import java.nio.file.Path;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

@ParametersAreNonnullByDefault
public class MSLanguageProvider extends net.minecraftforge.common.data.LanguageProvider {
    private final Map<String,String> enData = new TreeMap<>();
    private final Map<String,String> cnData = new TreeMap<>();
    private final PackOutput output;
    private final String locale;
    public MSLanguageProvider(PackOutput output, String locale) {
        super(output, MechanicalSoarMod.MODID, locale);
        this.output = output;
        this.locale = locale;
    }
    @Override
    protected void addTranslations() {

    }
    @Override
    public @NotNull CompletableFuture<?> run(CachedOutput cache) {
        this.addTranslations();
        Path path = this.output.getOutputFolder(PackOutput.Target.RESOURCE_PACK)
                .resolve(MechanicalSoarMod.MODID).resolve("lang");
        if (this.locale.equals("en_us") && !this.enData.isEmpty()) {
            return this.save(this.enData, cache, path.resolve("en_us.json"));
        }
        if (this.locale.equals("zh_cn") && !this.cnData.isEmpty()) {
            return this.save(this.cnData, cache, path.resolve("zh_cn.json"));
        }
        return CompletableFuture.allOf();
    }
    private CompletableFuture<?> save(Map<String, String> data, CachedOutput cache, Path target) {
        JsonObject json = new JsonObject();
        data.forEach(json::addProperty);
        return DataProvider.saveStable(cache, json, target);
    }
    private void addBlock(Supplier<? extends Block> key, String en_us, String zh_cn) {
        this.add(key.get().getDescriptionId(), en_us, zh_cn);
    }
    private void addItem(Supplier<? extends Item> key, String en_us, String zh_cn) {
        this.add(key.get().getDescriptionId(), en_us, zh_cn);
    }
    private void addDamageType(DamageType key, String en_us, String zh_cn) {
        this.add(key.deathMessageType().getSerializedName(), en_us, zh_cn);
    }
    private void addEntityType(Supplier<? extends EntityType<?>> key, String en_us, String zh_cn) {
        this.add(key.get().getDescriptionId(), en_us, zh_cn);
    }
    private void addEffect(Supplier<? extends MobEffect> key, String en_us, String zh_cn) {
        this.add(key.get().getDescriptionId(), en_us, zh_cn);
    }
    private void addBiome(ResourceKey<Biome> biome, String en_us, String zh_cn) {
        this.add("biome." + biome.location().toLanguageKey(), en_us, zh_cn);
    }
    private void addTab(Supplier<CreativeModeTab> tab, String en_us, String zh_cn){
        this.add(tab.get().getDisplayName().getString(), en_us, zh_cn);
    }
    private void addTooltips(String key, String en_us, String zh_cn) {
        this.add("tooltips." + MechanicalSoarMod.MODID + "." + key, en_us, zh_cn);
    }
    private void addAttribute(Supplier<Attribute> attribute, String en_us, String zh_cn) {
        this.add("attribute." + attribute.get().getDescriptionId(), en_us, zh_cn);
    }
    private void addKeyMapping(String name, String en_us, String zh_cn){
        this.add("key." + MechanicalSoarMod.MODID + "." + name, en_us, zh_cn);
    }
    private void add(String key, String en_us, String zh_cn) {
        if (this.locale.equals("en_us") && !this.enData.containsKey(key)) {
            this.enData.put(key, en_us);
        } else if (this.locale.equals("zh_cn") && !this.cnData.containsKey(key)) {
            this.cnData.put(key, zh_cn);
        }
    }
}
