package net.weibai.jl_menu.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.loading.FMLPaths;
import net.minecraftforge.registries.ForgeRegistries;
import net.weibai.jl_menu.JLMenu;
import net.weibai.jl_menu.json.JsonGuiConfig;
import net.weibai.jl_menu.json.ReadJsonFile;
import net.weibai.jl_menu.json.TextureLoader;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

@OnlyIn(Dist.CLIENT)
public class GuiManager extends Screen {
    private final Minecraft minecraft = Minecraft.getInstance();
    public static int imageWidth;
    public static int imageHeight;
    public static int leftPos;
    public static int topPos;
    private final File jlmenuDir = ReadJsonFile.jlmenuDir;
    private final File[] jlmenuJsonFiles = jlmenuDir.listFiles((dir, name) -> name.endsWith(".json"));
    public ResourceLocation texture ;
    private final TextureManager textureManager;
    public Item item;
    public Player player;
    private TextureLoader textureLoader;
    public GuiManager(Item item, Player player) {
        super(Component.literal("JLMenu"));
        this.textureManager = Minecraft.getInstance().getTextureManager();
        this.item = item;
        this.player = player;
        textureLoader =new TextureLoader(textureManager);
    }
    @Override
    protected void init() {
        super.init();
        leftPos = (this.width - imageWidth) / 2;
        topPos = (this.height - imageHeight) / 2;
        loadGuiForItem(item);
    }
    private void loadGuiForItem(Item item) {
        if (jlmenuJsonFiles != null) {
            for (File jsonFile : jlmenuJsonFiles) {
                JsonGuiConfig guiConfig = new ReadJsonFile().readJsonFile("", jsonFile.getName());
                if (guiConfig != null && guiConfig.getItem() != null ) {
                    Item itemObject = ForgeRegistries.ITEMS.getValue(guiConfig.getItem());
                    if (item == itemObject) {
                        createWidgets(guiConfig);
                        imageHeight = guiConfig.getImage().getHeight();
                        imageWidth = guiConfig.getImage().getWidth();

                        // 获取配置目录下的 jl_menu 文件夹
                        File jlmenuDir = new File(FMLPaths.CONFIGDIR.get().toFile(), "jl_menu");
                        File imageFile = new File(jlmenuDir, guiConfig.getImage().getTexture());
                        // 加载图片
                        texture = textureLoader.loadExternalTexture(imageFile);
                        break;
                    }
                }
            }
        }
    }
    private void createWidgets(JsonGuiConfig guiConfig) {
        for (JsonGuiConfig.Widget widget : guiConfig.getWidgets()) {
            JsonGuiConfig.Type type = widget.getType();
            if (type == null) {
                // 处理类型为 null 的情况，例如记录日志或跳过该 widget
                System.err.println("Widget type is null, skipping widget: " + widget);
                continue;
            }
            switch (widget.getType()) {
                case TEXT:
                    addRenderableOnly(new TextWidget(widget.getText()));
                    break;
                case BUTTON:
                    addRenderableWidget(new ButtonWidget(widget.getButton()));
                    break;
                case BUTTON_IMAGE:
                    addRenderableWidget(new ButtonImageWidget(widget.getButtonImage()));
                    break;
                case ITEM:
                    addRenderableWidget(new ItemWidget(widget.getItem_button()));
                    break;
                case IMAGE:
                    addRenderableOnly(new ImageWidget(widget.getImage()));
                    break;
                default:
                    JLMenu.LOGGER.warn("Unknown widget type: " + widget.getType());
            }
        }
    }

    @Override
    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        this.renderBackground(pGuiGraphics);
        this.renderBg(pGuiGraphics, pPartialTick, pMouseX, pMouseY);
        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
    }

    public void renderBg(GuiGraphics pGuiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
        RenderSystem.setShaderColor(1, 1, 1, 1);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();

        if(texture != null){
            Minecraft.getInstance().getTextureManager().getTexture(texture);

            pGuiGraphics.blit(texture, leftPos, topPos, 0, 0, imageWidth, imageHeight, imageWidth, imageHeight);
        }else {
            System.err.println("纹理加载失败");
        }

    }

    // 内部类定义
    private class TextWidget implements Renderable{
        private final JsonGuiConfig.Text text;

        public TextWidget(JsonGuiConfig.Text text) {
            this.text = text;
        }
        @Override
        public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
            pGuiGraphics.drawString(minecraft.font, Component.literal(text.getText()),text.getX(), text.getY(), text.getColor());
        }
    }

    private class ButtonWidget extends Button {
        private final JsonGuiConfig.Button button;

        public ButtonWidget(JsonGuiConfig.Button button) {
            super(Button.builder(Component.literal(button.getText()), e -> {
                        if(button.isBoolean_command()){
                            if (button.getCommand().getType() == JsonGuiConfig.Command.Type.SERVER_COMMAND) {
                                if (player.level() instanceof ServerLevel _level)
                                    _level.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, new Vec3(player.getX(), player.getY(), player.getZ()), Vec2.ZERO, _level, 4, "", Component.literal(""), _level.getServer(), null).withSuppressedOutput(),
                                            button.getCommand().getCommand());

                            } else if (button.getCommand().getType() == JsonGuiConfig.Command.Type.PLAYER_COMMAND) {
                                if (!player.level().isClientSide() && player.getServer() != null) {
                                    player.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, player.position(), player.getRotationVector(), player.level() instanceof ServerLevel ? (ServerLevel) player.level() : null, 4,
                                            player.getName().getString(), player.getDisplayName(), player.level().getServer(), player), button.getCommand().getCommand());
                                }

                            }
                        }else {
                            return;
                        }
            }).bounds(leftPos + button.getX(), topPos + button.getY(), 60, 20)
            );
            this.button = button;
        }
    }

    private class ButtonImageWidget extends ImageButton {
        private final JsonGuiConfig.ButtonImage buttonImage;

        public ButtonImageWidget(JsonGuiConfig.ButtonImage buttonImage) {
            super(leftPos + buttonImage.getX(), topPos + buttonImage.getY(),
                    buttonImage.getWidth(), buttonImage.getWidth() / 2, 0, 0, buttonImage.getWidth() / 2,
                    textureLoader.loadExternalTexture(new File(jlmenuDir, buttonImage.getTexture())), buttonImage.getWidth(), buttonImage.getWidth(), e -> {
                        if(buttonImage.isBoolean_command()){
                            if (buttonImage.getCommand().getType() == JsonGuiConfig.Command.Type.SERVER_COMMAND) {
                                if (player.level() instanceof ServerLevel _level)
                                    _level.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, new Vec3(player.getX(), player.getY(), player.getZ()), Vec2.ZERO, _level, 4, "", Component.literal(""), _level.getServer(), null).withSuppressedOutput(),
                                            buttonImage.getCommand().getCommand());

                            } else if (buttonImage.getCommand().getType() == JsonGuiConfig.Command.Type.PLAYER_COMMAND) {
                                if (!player.level().isClientSide() && player.getServer() != null) {
                                    player.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, player.position(), player.getRotationVector(), player.level() instanceof ServerLevel ? (ServerLevel) player.level() : null, 4,
                                            player.getName().getString(), player.getDisplayName(), player.level().getServer(), player), buttonImage.getCommand().getCommand());
                                }

                            }
                        }else {
                            return;
                        }
                    }
            );
            this.buttonImage = buttonImage;
        }
    }

    private class ItemWidget extends ImageButton implements Renderable{
        private final JsonGuiConfig.Item item;

        public ItemWidget(JsonGuiConfig.Item item) {
            super(leftPos + item.getX(), topPos + item.getY(),
                    16, 16, 0, 0, 0,
                    new ResourceLocation(JLMenu.MODID,"textures/block/air.png"), 16, 16, e -> {
                        if(item.isBoolean_command()){
                            if (item.getCommand().getType() == JsonGuiConfig.Command.Type.SERVER_COMMAND) {
                                if (player.level() instanceof ServerLevel _level)
                                    _level.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, new Vec3(player.getX(), player.getY(), player.getZ()), Vec2.ZERO, _level, 4, "", Component.literal(""), _level.getServer(), null).withSuppressedOutput(),
                                            item.getCommand().getCommand());

                            } else if (item.getCommand().getType() == JsonGuiConfig.Command.Type.PLAYER_COMMAND) {
                                if (!player.level().isClientSide() && player.getServer() != null) {
                                    player.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, player.position(), player.getRotationVector(), player.level() instanceof ServerLevel ? (ServerLevel) player.level() : null, 4,
                                            player.getName().getString(), player.getDisplayName(), player.level().getServer(), player), item.getCommand().getCommand());
                                }

                            }
                        }else {
                            return;
                        }
                    }
            );
           this.item = item;
        }

        @Override
        public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
            Item itemObject = ForgeRegistries.ITEMS.getValue(this.item.getItem());
            ItemStack stack = new ItemStack(itemObject);
            List<JsonGuiConfig.Item.Text> lore = item.getLore();
            if (item.getName() != null) {
                stack.setHoverName(Component.literal(item.getName().getText())
                        .withStyle(style -> style.withColor(item.getName().getColor())));
            }
            if (lore != null) {
                List<Component> formattedLore = lore.stream()
                        .map(text -> Component.literal(text.getText()).withStyle(style -> style.withColor(text.getColor())))
                        .collect(Collectors.toList());
                String loreString = formattedLore.stream()
                        .map(Component::getString)
                        .collect(Collectors.joining("\n"));
                stack.getOrCreateTagElement("display").putString("Lore", loreString);
            }
            pGuiGraphics.renderItem(stack, leftPos + item.getX(), topPos + item.getY());
            pGuiGraphics.renderItemDecorations(minecraft.font, stack, leftPos + item.getX(), topPos + item.getY());
        }
    }

    private class ImageWidget implements Renderable {
        private final JsonGuiConfig.Image image;
        private final ResourceLocation texture;
        public ImageWidget(JsonGuiConfig.Image image) {
            this.image = image;
            this.texture = textureLoader.loadExternalTexture(new File(jlmenuDir, image.getTexture()));
        }
        @Override
        public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
            if (texture != null) {
                Minecraft.getInstance().getTextureManager().getTexture(texture);
                pGuiGraphics.blit(texture, leftPos + image.getX(), topPos + image.getY(), 0, 0, image.getWidth(), image.getHeight(), image.getWidth(), image.getHeight());
            }
        }
    }
}