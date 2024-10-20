package net.weibai.jl_menu.item;

import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.fml.loading.FMLPaths;
import net.minecraftforge.registries.ForgeRegistries;
import net.weibai.jl_menu.JLMenu;
import net.weibai.jl_menu.gui.GuiManager;
import net.weibai.jl_menu.json.JsonGuiConfig;
import net.weibai.jl_menu.json.ReadJsonFile;

import java.io.File;

public class JLMenuItem extends Item {

    public JLMenuItem(Properties properties) {
        super(properties);
    }
    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pHand) {
        ItemStack itemStack = pPlayer.getItemInHand(pHand);
        Minecraft mc = Minecraft.getInstance();

        // 获取存储的物品
        ItemStack storedItemStack = getStoredItemFromNBT(itemStack);

        if (!storedItemStack.isEmpty()) {
            File jlmenuDir = new File(FMLPaths.CONFIGDIR.get().toFile(), "jl_menu");
            File[] jlmenuJsonFiles = jlmenuDir.listFiles((dir, name) -> name.endsWith(".json"));

            if (jlmenuJsonFiles != null) {
                for (File jsonFile : jlmenuJsonFiles) {
                    JsonGuiConfig guiConfig = new ReadJsonFile().readJsonFile("", jsonFile.getName());
                    if (guiConfig != null && guiConfig.getItem() != null) {
                        Item configItem = ForgeRegistries.ITEMS.getValue(guiConfig.getItem());
                        if (configItem != null && configItem == storedItemStack.getItem()) {
                            mc.setScreen(new GuiManager(storedItemStack.getItem(), pPlayer));
                        }
                    } else {
                        JLMenu.LOGGER.error("Failed to load GUI configuration from file: " + jsonFile.getAbsolutePath());
                    }
                }
            }
        }

        pPlayer.awardStat(Stats.ITEM_USED.get(this));
        return InteractionResultHolder.sidedSuccess(itemStack, pLevel.isClientSide());
    }
    /**
     * 从 NBT 中获取存储的物品
     *
     * @param itemStack 包含 NBT 数据的物品堆
     * @return 存储的物品堆
     */
    public static ItemStack getStoredItemFromNBT(ItemStack itemStack) {
        if (itemStack.hasTag()) {
            CompoundTag tag = itemStack.getTag();
            if (tag.contains("StoredItem", Tag.TAG_COMPOUND)) {
                CompoundTag storedItemTag = tag.getCompound("StoredItem");
                return ItemStack.of(storedItemTag);
            }
        }
        return ItemStack.EMPTY;
    }

    /**
     * 将物品存储到 NBT 中
     *
     * @param itemStack 要存储 NBT 数据的物品堆
     * @param storedItem 要存储的物品堆
     */
    public static void setStoredItemToNBT(ItemStack itemStack, ItemStack storedItem) {
        CompoundTag tag = itemStack.getOrCreateTag();
        tag.put("StoredItem", storedItem.save(new CompoundTag()));
    }
}