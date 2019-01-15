package model.map.mapObjects;

import model.items.Item;
import model.items.ItemWithSprite;

public class ItemMapObject extends MapObject {

    private Item item;
    private ItemWithSprite itemWithSprite;
    private String spriteName;

    public ItemMapObject(Item item) {
        super(MapObjectType.DOOR, false);
        this.item = item;
        if (item instanceof ItemWithSprite)
            itemWithSprite = (ItemWithSprite)item;
        spriteName = item.getName();
    }

    public Item getItem() {
        return item;
    }

    public ItemWithSprite getItemWithSprite() {
        return itemWithSprite;
    }

    public String getSpriteName() {
        return spriteName;
    }
}
