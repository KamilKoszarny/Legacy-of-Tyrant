package model.map.mapObjects;

import model.map.terrains.Terrain;

public enum MapObjectType {
    TREE(3, 16, Terrain.TREES, 100, false, true, false),
    BUSH(3, 5, Terrain.BUSH, 50, false, true, false),
    GROUND(3, 2, Terrain.GROUND, 2500, false, true, false),
    GRASS(0, 5, Terrain.GRASS, 10, false, false, false),
    WALL(false, true),
    DOOR(true, true),
    WINDOW(false, true),
    FURNITURE(false, false),
    ITEM(true, false),
    ;

    private int sizes, looks;
    private Terrain terrain;
    private int probabilityDivider;
    private boolean clickable, cutable, hasCutImage;

    MapObjectType(boolean clickable, boolean cutable) {
        this.clickable = clickable;
        this.cutable = cutable;
        hasCutImage = cutable;
    }

    MapObjectType(boolean clickable, boolean cutable, boolean hasCutImage) {
        this.clickable = clickable;
        this.cutable = cutable;
        this.hasCutImage = hasCutImage;
    }

    MapObjectType(int sizes, int looks, Terrain terrain, int probabilityDivider, boolean clickable, boolean cutable, boolean hasCutImage) {
        this.sizes = sizes;
        this.looks = looks;
        this.terrain = terrain;
        this.probabilityDivider = probabilityDivider;
        this.clickable = clickable;
        this.cutable = cutable;
        this.hasCutImage = hasCutImage;
    }

    public static MapObjectType mapObjectTypeByTerrain(Terrain terrain) {
        for (MapObjectType type: MapObjectType.values()) {
            if (terrain.equals(type.getTerrain()))
                return type;
        }
        return null;
    }

    int getSizes() {
        return sizes;
    }

    int getLooks() {
        return looks;
    }

    public Terrain getTerrain() {
        return terrain;
    }

    public int getProbabilityDivider() {
        return probabilityDivider;
    }

    public boolean isClickable() {
        return clickable;
    }

    public boolean isCutable() {
        return cutable;
    }

    public boolean hasCutImage() {
        return hasCutImage;
    }
}
