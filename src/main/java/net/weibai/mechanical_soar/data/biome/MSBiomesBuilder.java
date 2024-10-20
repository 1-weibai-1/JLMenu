package net.weibai.mechanical_soar.data.biome;

import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.weibai.mechanical_soar.MechanicalSoarMod;


public class MSBiomesBuilder {
    private static ResourceKey<Biome> register(String name) {
        return ResourceKey.create(Registries.BIOME, MechanicalSoarMod.prefix(name));
    }

    public static void bootstrap(BootstapContext<Biome> context) {

    }
}
