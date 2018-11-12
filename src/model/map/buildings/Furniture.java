package model.map.buildings;

import model.map.mapObjects.MapObject;
import model.map.mapObjects.MapObjectType;

import java.awt.*;
import java.util.Random;

public class Furniture extends MapObject {

    public static final int ACTION_DIST = 4;

    int sizeX, sizeY;

    public Furniture(FurnitureType type) {
        this(FurnitureType.getRandomFurnitureCode(type));
    }

    public Furniture(int look) {
        super(MapObjectType.FURNITURE, 0, look, false);
        sizeX = (look - look/1000*1000)/100;
        sizeY = (look - look/100*100)/10;
    }

    public int getSizeX() {
        return sizeX;
    }

    public int getSizeY() {
        return sizeY;
    }
}
