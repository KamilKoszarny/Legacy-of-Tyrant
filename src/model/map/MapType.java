package model.map;

import model.map.terrains.Terrain;

import java.util.List;
import java.util.Random;

public enum MapType {
    GRASSLAND(70, 40, 30, 10, 1, 75),
    FOREST(30, 30, 80, 30, 1, 50),
    VILLAGE(50, 30, 30, 60, 10, 150);

    private int grass, bush, trees, ground;
    private int buildings, buildingMaxSize;

    MapType(int grass, int bush, int trees, int ground, int buildings, int buildingMaxSize) {
        Random r = new Random();
        this.grass = grass;
        this.bush = bush;
        this.trees = trees;
        this.ground = ground;
        this.buildings = buildings;
        this.buildingMaxSize = buildingMaxSize;
    }

    public int getTerrainIntensity(Terrain terrain) {
        switch (terrain){
            case GROUND: return ground;
            case GRASS: return grass;
            case BUSH: return bush;
            case TREES: return trees;
        }
        return 0;
    }

    public int getBuildings() {
        return buildings;
    }

    public int getBuildingMaxSize() {
        return buildingMaxSize;
    }
}
