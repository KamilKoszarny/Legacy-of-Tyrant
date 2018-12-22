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
        long startTime = System.nanoTime();
        long time = startTime;
        List<BufferedImage> itemsSprites = new ArrayList<>();

        String sex = character.isMale() ? "/" : "F/";
        loadAndAddSpriteSheet(itemsSprites, "base", sex, "base.png");
        System.out.println(" base: " + (System.nanoTime() - time)/1000000. + " ms");
        time = System.nanoTime();

        loadAndAddSpriteSheet(itemsSprites, "weapons", sex, character.getWeapon().getSpriteName());
        System.out.println(" weapon: " + (System.nanoTime() - time)/1000000. + " ms");
        time = System.nanoTime();
        loadAndAddSpriteSheet(itemsSprites, "armors", sex, character.getBodyArmorItem().getSpriteName());
        System.out.println(" armor: " + (System.nanoTime() - time)/1000000. + " ms");
        time = System.nanoTime();
        loadAndAddSpriteSheet(itemsSprites, "heads", sex, character.getHelmet().getSpriteName());
        loadAndAddSpriteSheet(itemsSprites, "hands", sex, character.getGloves().getSpriteName());
        loadAndAddSpriteSheet(itemsSprites, "feets", sex, character.getBoots().getSpriteName());

        System.out.println(" TOTAL item load: " + (System.nanoTime() - startTime)/1000000. + " ms");

        return itemsSprites;
    }

    private static void loadAndAddSpriteSheet(List<BufferedImage> itemsSprites, String folder, String sex, String spriteName){
        if (spriteName != null) {
            String path = "/sprites/chars/" + folder + sex + spriteName;
            try {
                itemsSprites.add(ImageIO.read(App.class.getResource(path)));
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println(path);
            }
        }
    }
}
