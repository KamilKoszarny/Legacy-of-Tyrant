package model.map.buildings;

public class FurnitureFactory {

    public static Furniture getFurniture(FurnitureType type) {
        if (type.equals(FurnitureType.CHEST))
            return new Chest();
        else
            return new Furniture(type);
    }
}
