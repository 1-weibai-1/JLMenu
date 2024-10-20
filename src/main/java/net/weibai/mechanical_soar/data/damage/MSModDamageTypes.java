package net.weibai.mechanical_soar.data.damage;

import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageEffects;
import net.minecraft.world.damagesource.DamageType;
import net.weibai.mechanical_soar.MechanicalSoarMod;

public class MSModDamageTypes {
    public static final ResourceKey<DamageType> IN_GOD_FIRE = createKey("in_god_fire");

    @SuppressWarnings("SameParameterValue")
    private static ResourceKey<DamageType> createKey(String pName) {
        return ResourceKey.create(Registries.DAMAGE_TYPE, MechanicalSoarMod.prefix(pName));
    }
    public static void bootstrap(BootstapContext<DamageType> pContext) {
        pContext.register(IN_GOD_FIRE, new DamageType("inGodFire", 0.1F, DamageEffects.BURNING));
    }
}
