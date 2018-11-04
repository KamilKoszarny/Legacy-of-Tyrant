package model.map.mapObjects;

import model.map.terrains.Terrain;

public enum MapObjectType {
    TREE(3, 16, Terrain.TREES, 100),
    BUSH(3, 5, Terrain.BUSH, 50),
    WALL(3, 5, null, 50),
    ;

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
