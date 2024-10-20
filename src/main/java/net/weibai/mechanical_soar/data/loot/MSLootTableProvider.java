package net.weibai.mechanical_soar.data.loot;


import net.minecraft.data.PackOutput;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

import java.util.List;
import java.util.Set;

public class MSLootTableProvider extends net.minecraft.data.loot.LootTableProvider {

    public MSLootTableProvider(PackOutput output) {
        super(output, Set.of(), List.of(new SubProviderEntry(MSBlockLoot::new, LootContextParamSets.BLOCK)));
    }

}