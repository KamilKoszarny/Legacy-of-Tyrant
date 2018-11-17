package model.items;

import javafx.scene.image.Image;

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
        String name = enumName.substring(0 , 1) + enumName.substring(1, enumName.length()).toLowerCase();
        name = name.replace('_', ' ');
        return name;
    }
}
