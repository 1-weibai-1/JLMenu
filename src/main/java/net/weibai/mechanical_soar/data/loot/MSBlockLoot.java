package net.weibai.mechanical_soar.data.loot;


import net.minecraft.data.loot.packs.VanillaBlockLoot;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.ForgeRegistries;
import net.weibai.mechanical_soar.MechanicalSoarMod;

import java.util.stream.Collectors;

public class MSBlockLoot extends VanillaBlockLoot {

    @Override
    protected void generate() {

    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ForgeRegistries.BLOCKS.getValues().stream().filter(block -> ForgeRegistries.BLOCKS.getKey(block).getNamespace().equals(MechanicalSoarMod.MODID)).collect(Collectors.toList());
    }

}
