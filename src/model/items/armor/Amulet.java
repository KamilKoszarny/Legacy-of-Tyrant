package model.items.armor;

import javafx.scene.image.Image;
import model.items.ItemImagesLoader;

public enum Amulet implements Armor{

    NOTHING,
    BRONZE_AMULET,
    SILVER_AMULET,
    GOLD_AMULET,
    ;

    private Image image;
    private String name;

    Amulet() {
        image = ItemImagesLoader.loadItemImage("/items/amulets/" + this.name() + ".png");
        name = ItemImagesLoader.setItemName(this.name());
    }

    public Image getImage() {
        return image;
    }

    public String getName() {
        return name;
    }

    @Override
    public int getWeight() {
        return 0;
    }
}
