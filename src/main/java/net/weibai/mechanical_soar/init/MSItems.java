package net.weibai.mechanical_soar.init;

import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.weibai.mechanical_soar.MechanicalSoarMod;

public class MSItems {
    public static final DeferredRegister<Item> REGISTER = DeferredRegister.create(ForgeRegistries.ITEMS, MechanicalSoarMod.MODID);
    public static final RegistryObject<Item> EXAMPLE_BLOCK_ITEM = REGISTER.register("example_block", () -> new BlockItem(MSBlocks.EXAMPLE_BLOCK.get(), new Item.Properties()));

    public static final RegistryObject<Item> EXAMPLE_ITEM = REGISTER.register("example_item", () -> new Item(new Item.Properties().food(new FoodProperties.Builder()
            .alwaysEat().nutrition(1).saturationMod(2f).build())));


}
