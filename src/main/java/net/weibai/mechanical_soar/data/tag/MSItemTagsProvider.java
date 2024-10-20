package net.weibai.mechanical_soar.data.tag;


import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.weibai.mechanical_soar.MechanicalSoarMod;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class MSItemTagsProvider extends net.minecraft.data.tags.ItemTagsProvider {

    public MSItemTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, CompletableFuture<TagLookup<Block>> blockTags, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, blockTags, MechanicalSoarMod.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
//        this.tag(ItemTags.SLABS).add(MSModItems.DIRT_STEPS.get());
    }

}