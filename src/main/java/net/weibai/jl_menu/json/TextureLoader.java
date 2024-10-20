package net.weibai.jl_menu.json;

import com.mojang.blaze3d.platform.NativeImage;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@OnlyIn(Dist.CLIENT)
public class TextureLoader {

    private final TextureManager textureManager;

    public TextureLoader(TextureManager textureManager) {
        this.textureManager = textureManager;
    }

    public ResourceLocation loadExternalTexture(File file) {
        try {
            // 检查文件是否存在
            if (!file.exists()) {
                System.err.println("文件未找到: " + file.getAbsolutePath());
                return null;
            }

            // 读取文件
            FileInputStream inputStream = new FileInputStream(file);
            byte[] imageData = inputStream.readAllBytes();
            NativeImage nativeImage = NativeImage.read(inputStream);
            DynamicTexture dynamicTexture = new DynamicTexture(nativeImage);
            ResourceLocation resourceLocation = new ResourceLocation("jl_menu", "external_texture_" + System.currentTimeMillis());
            textureManager.register(resourceLocation, dynamicTexture);
            return resourceLocation;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
