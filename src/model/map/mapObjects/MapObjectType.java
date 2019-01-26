package model.map.mapObjects;

import model.map.terrains.Terrain;

public enum MapObjectType {
    TREE(3, 16, Terrain.TREES, 100, false, true, false, 0.2),
    BUSH(3, 5, Terrain.BUSH, 50, false, true, false, 0.4),
    GROUND(3, 2, Terrain.GROUND, 2500, false, true, false, 0.6),
    GRASS(0, 5, Terrain.GRASS, 10, false, false, false, 0.9),
    WALL(false, true, 0),
    DOOR(true, true, 0),
    WINDOW(false, true, 0.8),
    FURNITURE(false, false, 1),
    ITEM(true, false, 1),
    ;

    private int sizes, looks;
    private Terrain terrain;
    private int probabilityDivider;
    private boolean clickable, cutable, hasCutImage;
    private double transparency;

    MapObjectType(boolean clickable, boolean cutable, double transparency) {
        this.clickable = clickable;
        this.cutable = cutable;
        hasCutImage = cutable;
        this.transparency = transparency;
    }

    MapObjectType(boolean clickable, boolean cutable, boolean hasCutImage, double transparency) {
        this.clickable = clickable;
        this.cutable = cutable;
        this.hasCutImage = hasCutImage;
        this.transparency = transparency;
    }

    MapObjectType(int sizes, int looks, Terrain terrain, int probabilityDivider, boolean clickable, boolean cutable, boolean hasCutImage, double transparency) {
        this.sizes = sizes;
        this.looks = looks;
        this.terrain = terrain;
        this.probabilityDivider = probabilityDivider;
        this.clickable = clickable;
        this.cutable = cutable;
        this.hasCutImage = hasCutImage;
        this.transparency = transparency;
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

    public double getTransparency() {
        return transparency;
    }
}
