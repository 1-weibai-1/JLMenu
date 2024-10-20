package net.weibai.jl_menu.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fml.loading.FMLPaths;
import net.weibai.jl_menu.JLMenu;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class ReadJsonFile {
    public static File jlmenuDir = new File(FMLPaths.GAMEDIR.get().toFile(), "jl_menu");
    public JsonGuiConfig readJsonFile(String pPath, String pName) {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(ResourceLocation.class, new JsonGuiConfig.ResourceLocationDeserializer());
        Gson gson = builder.create();

        File subFolders = getFolderByPath(pPath);
        File jsonFile = new File(subFolders, pName);
        try (FileReader reader = new FileReader(jsonFile)) {
            return gson.fromJson(reader, JsonGuiConfig.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    private File getFolderByPath(String relativePath) {
        File targetDir = new File(jlmenuDir, relativePath);
        if (!targetDir.exists()) {
            JLMenu.LOGGER.info("Target directory does not exist: " + targetDir);
            System.out.println("Target directory does not exist: " + targetDir.getAbsolutePath());
            return null;
        }
        if (!targetDir.isDirectory()) {
            JLMenu.LOGGER.info("Target path is not a directory: " + targetDir);
            System.out.println("Target path is not a directory: " + targetDir.getAbsolutePath());
            return null;
        }
        return targetDir;
    }
}
