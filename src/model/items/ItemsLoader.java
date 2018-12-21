package model.items;

import javafx.scene.image.Image;
import main.App;
import model.character.Character;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
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
        List<BufferedImage> itemsSprites = new ArrayList<>();
        String path = "";
        try {
            String sex;
            if (character.isMale()) {
                path = "/sprites/chars/baseM.png";
                sex = "/";
            }
            else {
                path = "/sprites/chars/baseF.png";
                sex = "F/";
            }
            itemsSprites.add(ImageIO.read(App.class.getResource(path)));

            String spriteName = character.getWeapon().getSpriteName();
            if (spriteName != null) {
                path = "/sprites/chars/weapons" + sex + spriteName;
                itemsSprites.add(ImageIO.read(App.class.getResource(path)));
            }
            spriteName = character.getBodyArmorItem().getSpriteName();
            if (spriteName != null) {
                path = "/sprites/chars/armors" + sex + spriteName;
                itemsSprites.add(ImageIO.read(App.class.getResource(path)));
            }
            spriteName = character.getHelmet().getSpriteName();
            System.out.println(spriteName);
            if (spriteName != null) {
                path = "/sprites/chars/heads" + sex + spriteName;
                itemsSprites.add(ImageIO.read(App.class.getResource(path)));
            }
            spriteName = character.getGloves().getSpriteName();
            System.out.println(spriteName);
            if (spriteName != null) {
                path = "/sprites/chars/hands" + sex + spriteName;
                itemsSprites.add(ImageIO.read(App.class.getResource(path)));
            }
            spriteName = character.getBoots().getSpriteName();
            System.out.println(spriteName);
            if (spriteName != null) {
                path = "/sprites/chars/feets" + sex + spriteName;
                itemsSprites.add(ImageIO.read(App.class.getResource(path)));
            }
        } catch (IOException e) {
            System.out.println(path);
            e.printStackTrace();
        }
        return itemsSprites;
    }
}
