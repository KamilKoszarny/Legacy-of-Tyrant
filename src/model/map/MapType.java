package model.map;

import java.util.List;
import java.util.Random;

public enum MapType {
    GRASSLAND(70, 40, 30, 10, 1, 200),
    FOREST(30, 30, 80, 30, 1, 100),
    VILLAGE(50, 30, 30, 60, 10, 250);

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

    public int getGrass() {
        return grass;
    }

    public int getBush() {
        return bush;
    }

    public int getTrees() {
        return trees;
    }

    public int getGround() {
        return ground;
    }

    public int getBuildings() {
        return buildings;
    }

    public int getBuildingMaxSize() {
        return buildingMaxSize;
    }
}