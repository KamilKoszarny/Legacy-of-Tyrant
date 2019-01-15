package model.items;

import javafx.scene.image.Image;
import main.App;
import model.character.Character;
import model.items.armor.*;
import model.items.weapon.Weapon;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.*;

public class ItemsLoader {


    public static final Map<Class, String> SPRITE_FOLDER_NAMES = new HashMap<>();
//    public static final List<String> SPRITE_FOLDER_NAMES = Arrays.asList("base", "armors", "heads", "hands", "feets", "shields", "weapons");

    static {
        SPRITE_FOLDER_NAMES.put(null, "base");
        SPRITE_FOLDER_NAMES.put(Weapon.class, "weapons");
        SPRITE_FOLDER_NAMES.put(Shield.class, "shields");
        SPRITE_FOLDER_NAMES.put(BodyArmor.class, "armors");
        SPRITE_FOLDER_NAMES.put(Helmet.class, "heads");
        SPRITE_FOLDER_NAMES.put(Gloves.class, "hands");
        SPRITE_FOLDER_NAMES.put(Boots.class, "feets");
    }

    public static Image loadItemImage(String path) {
        Image image;
        try {
            image = new Image(path);
        } catch (Exception e) {
            System.out.println("Item not found: " + path);
            image = null;
        }
        return image;
    }

    public static String setItemName(String enumName) {
        String name = enumName.replace('_', ' ');
        return name;
    }

    public static List<BufferedImage> loadItemSprites(Character character) {
        long startTime = System.nanoTime();
        long time = startTime;
        List<BufferedImage> itemsSprites = new ArrayList<>();
        String sex = character.isMale() ? "/" : "F/";

        for (String spriteFolderName : SPRITE_FOLDER_NAMES.values()) {
            BufferedImage itemSprite = character.getItems().getItemsSprites().get(spriteFolderName);
            if (itemSprite == null) {
                itemSprite = loadSpriteSheet(spriteFolderName, sex, getSpriteNameByFolder(spriteFolderName, character));
                character.getItems().getItemsSprites().put(spriteFolderName, itemSprite);

                System.out.println(" " + spriteFolderName + ": " + (System.nanoTime() - time) / 1000000. + " ms");
                time = System.nanoTime();
            }
            itemsSprites.add(itemSprite);
        }

        System.out.println(" TOTAL item load: " + (System.nanoTime() - startTime)/1000000. + " ms");

        return itemsSprites;
    }

    public static String getSpriteNameByFolder(String folderName, Character character) {
        switch (folderName){
            case "base": return "base.png";
            case "armors": return character.getItems().getBodyArmorItem().getSpriteName();
            case "heads": return character.getItems().getHelmet().getSpriteName();
            case "hands": return character.getItems().getGloves().getSpriteName();
            case "feets": return character.getItems().getBoots().getSpriteName();
            case "shields": return character.getItems().getShield().getSpriteName();
            case "weapons": return character.getItems().getWeapon().getSpriteName();
        }
        return null;
    }

    public static BufferedImage loadSpriteSheetForItemOnly(ItemWithSprite item) {
        String folder = SPRITE_FOLDER_NAMES.get(item.getClass());
        String sex = new Random().nextInt(2) > 0 ? "/" : "F/";

        return loadSpriteSheet(folder, sex, item.getSpriteName());
    }

    private static BufferedImage loadSpriteSheet(String folder, String sex, String spriteName){
        if (spriteName != null) {
            String path = "/sprites/chars/" + folder + sex + spriteName;
            try {
                return ImageIO.read(App.class.getResource(path));
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println(path);
            }
        }
        return null;
    }
}
