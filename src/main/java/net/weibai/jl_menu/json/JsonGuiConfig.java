package net.weibai.jl_menu.json;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.resources.ResourceLocation;

import java.util.List;
@Getter
@Setter
public class JsonGuiConfig {
    private List<Widget> widgets;
    @SerializedName("item")
    private ResourceLocation item;
    private Image image;
    @Getter
    @Setter
    public static class Widget {
        private Type type;
        private Text text;
        private Button button;
        private Item item_button;
        private Image image;
        private ButtonImage buttonImage;

    }
    @Getter
    @Setter
    public static class Text {
        private int x;
        private int y;
        private String text;
        private int color;
    }
    @Getter
    @Setter
    public static class Button {
        private int x;
        private int y;
        private int width;
        private int height;
        private String text;
        private Command command;
        private boolean boolean_command;
    }
    @Getter
    @Setter
    public static class ButtonImage {
        private int x;
        private int y;
        private int width;
        private int height;
        private String text;
        private String texture;
        private Command command;
        private boolean boolean_command;
    }
    @Getter
    @Setter
    public static class Item {
        private int x;
        private int y;
        private int index;
        @SerializedName("item")
        private ResourceLocation item;
        private Name name;
        private List<Text> lore;
        private Command command;
        private boolean boolean_command;
        @Getter
        @Setter
        public static class Name {
            private String text;
            private int color;
        }
        @Getter
        @Setter
        public static class Text {
            private String text;
            private int color;
        }
    }
    @Getter
    @Setter
    public static class Image {
        private int x;
        private int y;
        private int width;
        private int height;
        private String texture;
    }
    @Getter
    public static class Command {
        private String command;
        private Type type;
        public  enum Type {
            SERVER_COMMAND("server_command"),
            PLAYER_COMMAND("player_command");
            private final String name;
            Type(String name) {
                this.name = name;
            }
            public static Type fromName(String name) {
                for (Type type : values()) {
                    if (type.name.equals(name)) {
                        return type;
                    }
                }
                throw new IllegalArgumentException("Unknown type: " + name);
            }
        }
    }
    @Getter
    public enum Type {
        TEXT("text"),
        BUTTON("button"),
        BUTTON_IMAGE("button_image"),
        ITEM("item"),
        IMAGE("image");
        private final String name;
        Type(String name) {
            this.name = name;
        }
        public static Type fromName(String name) {
            for (Type type : values()) {
                if (type.name.equals(name)) {
                    return type;
                }
            }
            throw new IllegalArgumentException("Unknown type: " + name);
        }
    }
    public static class ResourceLocationDeserializer implements JsonDeserializer<ResourceLocation> {
        @Override
        public ResourceLocation deserialize(JsonElement json, java.lang.reflect.Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            String resourceLocationString = json.getAsString();
            return new ResourceLocation(resourceLocationString);
        }

    }
}