package net.weibai.mechanical_soar.data.provider;


import net.minecraft.core.Direction;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.SlabType;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.weibai.mechanical_soar.MechanicalSoarMod;

import java.util.Objects;
import java.util.function.Supplier;

public class MSBlockStateProvider extends net.minecraftforge.client.model.generators.BlockStateProvider {

    public MSBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, MechanicalSoarMod.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {

    }






    public void blockNull(Supplier<Block> block){
        ModelFile modelFile = this.models().withExistingParent(
                        this.name(block.get()),
                        this.mcLoc("block/cube_all"))
                .texture("all", this.modLoc("block/" + this.name(block.get())));
        this.getVariantBuilder(block.get())
                .partialState().modelForState().modelFile(modelFile).addModel();
    }
    public void craftsmanAnvil(Supplier<Block> block, DirectionProperty property){
        ModelFile modelFile = this.models().withExistingParent(
                        this.name(block.get()),
                        this.mcLoc("block/template_anvil"))
                .texture("body", this.modLoc("block/" + this.name(block.get())))
                .texture("top", this.modLoc("block/" + this.name(block.get()) + "_top"))
                .texture("particle", this.modLoc("block/" + this.name(block.get()) + "_top"))
                .renderType("solid");
        for (Direction direction : property.getPossibleValues()) {
            this.getVariantBuilder(block.get())
                    .partialState()
                    .with(property, direction).modelForState().modelFile(modelFile).rotationY((int) direction.toYRot()).addModel();
        }
    }
    public void slab(Supplier<Block> block, EnumProperty<SlabType> property) {
        ModelFile BOTTOM = this.models().withExistingParent(
                        this.name(block.get()) + "_slab",
                        this.mcLoc("block/slab"))
                .texture("particle", this.modLoc("block/" + this.name(block.get())))
                .texture("bottom", this.modLoc("block/" + this.name(block.get())))
                .texture("top", this.modLoc("block/" + this.name(block.get())))
                .texture("side", this.modLoc("block/" + this.name(block.get())))
                .renderType("solid");
        ModelFile DOUBLE = this.models().withExistingParent(
                        this.name(block.get()) + "_cube_bottom_top",
                        this.mcLoc("block/cube_bottom_top"))
                .texture("particle", this.modLoc("block/" + this.name(block.get())))
                .texture("bottom", this.modLoc("block/" + this.name(block.get())))
                .texture("top", this.modLoc("block/" + this.name(block.get())))
                .texture("side", this.modLoc("block/" + this.name(block.get())))
                .renderType("solid");
        ModelFile TOP = this.models().withExistingParent(
                        this.name(block.get()) + "_slab_top",
                        this.mcLoc("block/slab_top"))
                .texture("particle", this.modLoc("block/" + this.name(block.get())))
                .texture("bottom", this.modLoc("block/" + this.name(block.get())))
                .texture("top", this.modLoc("block/" + this.name(block.get())))
                .texture("side", this.modLoc("block/" + this.name(block.get())))
                .renderType("solid");
        this.getVariantBuilder(block.get())
                .partialState()
                .with(property, SlabType.BOTTOM).modelForState().modelFile(BOTTOM).addModel();
        this.getVariantBuilder(block.get())
                .partialState()
                .with(property, SlabType.TOP).modelForState().modelFile(TOP).addModel();
        this.getVariantBuilder(block.get())
                .partialState()
                .with(property, SlabType.DOUBLE).modelForState().modelFile(DOUBLE).addModel();
    }

    public void cushion(Supplier<Block> block, Supplier<Item> item, IntegerProperty property, int value){
        ModelFile model = this.models().withExistingParent(
                        this.name(item.get()),
                        this.modLoc("custom/cushionmodl")
                )
                .texture("all", this.modLoc("block/" + this.name(item.get())))
                .texture("particle", this.modLoc("block/" + this.name(item.get())))
                .texture("1", this.modLoc("block/" + this.name(item.get())))
                .renderType("solid");

        this.getVariantBuilder(block.get())
                .partialState()
                .with(property, value).modelForState().modelFile(model).addModel()
        ;
    }

    private String name(Block block) {
        return Objects.requireNonNull(ForgeRegistries.BLOCKS.getKey(block)).getPath();
    }
    private String name(Item item) {
        return Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(item)).getPath();
    }

}