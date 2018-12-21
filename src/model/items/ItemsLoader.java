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
            String sex = "/";
            if (character.isMale()) {
                path = "/sprites/chars/clothesM.png";
                itemsSprites.add(ImageIO.read(App.class.getResource(path)));
                path = "/sprites/chars/heads/male_head1.png";
            }
            else {
                path = "/sprites/chars/clothesF.png";
                sex = "F/";
                itemsSprites.add(ImageIO.read(App.class.getResource(path)));
                path = "/sprites/chars/headsF/head_long.png";
            }
            itemsSprites.add(ImageIO.read(App.class.getResource(path)));

            String spriteName = character.getWeapon().getSpriteName();
            if (spriteName != null) {
                path = "/sprites/chars/weapons" + sex + spriteName;
                itemsSprites.add(ImageIO.read(App.class.getResource(path)));
            }
            spriteName = character.getBodyArmorItem().getSpriteName();
            System.out.println(spriteName);
            if (spriteName != null) {
                path = "/sprites/chars/armors" + sex + spriteName;
                itemsSprites.add(ImageIO.read(App.class.getResource(path)));
            }
        } catch (IOException e) {
            System.out.println(path);
            e.printStackTrace();
        }
        return itemsSprites;
    }
}
