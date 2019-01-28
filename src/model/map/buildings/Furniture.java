package model.map.buildings;

import model.map.mapObjects.MapObject;
import model.map.mapObjects.MapObjectType;

public class Furniture extends MapObject {

    public static final int ACTION_DIST = 4;

    private FurnitureType furnitureType;
    private int sizeX, sizeY;


    public Furniture(FurnitureType furnitureType) {
        this(furnitureType, FurnitureType.getRandomFurnitureCode(furnitureType));
    }

    public Furniture(FurnitureType furnitureType, int lookCode) {
        super(MapObjectType.FURNITURE, 0, lookCode);
        this.furnitureType = furnitureType;
        sizeX = (lookCode - lookCode /1000*1000)/100;
        sizeY = (lookCode - lookCode /100*100)/10;
    }

    public FurnitureType getFurnitureType() {
        return furnitureType;
    }

    public int getSizeX() {
        return sizeX;
    }

    public int getSizeY() {
        return sizeY;
    }
}
