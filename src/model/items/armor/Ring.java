package model.items.armor;

import javafx.scene.image.Image;
import model.items.ItemsLoader;

public enum Ring implements Armor{

    NOTHING,
    BRONZE_RING,
    SILVER_RING,
    GOLD_RING,
    ;

    private Image image;
    private String name;

    Ring() {
        image = ItemsLoader.loadItemImage("/items/rings/" + this.name() + ".png");
        name = ItemsLoader.setItemName(this.name());
    }

    public Image getImage() {
        return image;
    }

    public String getName() {
        return name;
    }
}
