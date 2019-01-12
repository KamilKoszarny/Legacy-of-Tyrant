package model.items;

import javafx.scene.image.Image;
import main.App;
import model.character.Character;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ItemsLoader {

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
        List<String> spriteFolderNames = Arrays.asList("base", "armors", "heads", "hands", "feets", "shields", "weapons");
        String sex = character.isMale() ? "/" : "F/";

        for (String spriteFolderName : spriteFolderNames) {
            BufferedImage itemSprite = character.getItemsSprites().get(spriteFolderName);
            if (itemSprite == null) {
                itemSprite = loadSpriteSheet(spriteFolderName, sex, getSpriteNameByFolder(spriteFolderName, character));
                character.getItemsSprites().put(spriteFolderName, itemSprite);

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
            case "armors": return character.getBodyArmorItem().getSpriteName();
            case "heads": return character.getHelmet().getSpriteName();
            case "hands": return character.getGloves().getSpriteName();
            case "feets": return character.getBoots().getSpriteName();
            case "shields": return character.getShield().getSpriteName();
            case "weapons": return character.getWeapon().getSpriteName();
        }
        return null;
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
