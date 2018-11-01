package model.map.mapObjects;

import model.map.terrains.Terrain;

public enum MapObjectType {
    TREE(3, 8, Terrain.TREES, 100);

    private int sizes, looks;
    private Terrain terrain;
    private int probabilityDivider;

    MapObjectType(int sizes, int looks, Terrain terrain, int probabilityDivider) {
        this.sizes = sizes;
        this.looks = looks;
        this.terrain = terrain;
        this.probabilityDivider = probabilityDivider;
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
}
