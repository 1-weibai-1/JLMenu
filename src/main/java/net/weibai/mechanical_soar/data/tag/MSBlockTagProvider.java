package net.weibai.mechanical_soar.data.tag;


import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.weibai.mechanical_soar.MechanicalSoarMod;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class MSBlockTagProvider extends BlockTagsProvider {

    public MSBlockTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, MechanicalSoarMod.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
//        this.tag(BlockTags.SLABS).add(MSModBlocks.DIRT_STEPS.get());

    }

}