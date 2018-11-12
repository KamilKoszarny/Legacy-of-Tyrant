package model.map;

import helpers.downloaded.pathfinding.grid.GridGraph;
import helpers.my.GeomerticHelper;
import model.character.Character;
import model.map.buildings.BuildingGenerator;
import model.map.heights.HeightGenerator;
import model.map.lights.LightGenerator;
import model.map.mapObjects.MapObjectsGenerator;
import model.map.roads.RoadGenerator;
import model.map.terrains.TerrainGenerator;
import model.map.water.RiverGenerator;
import model.map.water.WaterGenerator;

import java.awt.*;
import java.util.Collection;
import java.util.List;

public class MapGenerator {

    public Map generateMap(Map map){
        long time = System.nanoTime();

        TerrainGenerator terrainGenerator = new TerrainGenerator(map);
        terrainGenerator.generateTerrain();
        System.out.println("terrainGen:" + (System.nanoTime() - time)/1000000. + " ms");
        time = System.nanoTime();

        HeightGenerator heightGenerator = new HeightGenerator(map);
        heightGenerator.generateHeights();
        System.out.println("heightGen:" + (System.nanoTime() - time)/1000000. + " ms");
        time = System.nanoTime();

        WaterGenerator waterGenerator = new WaterGenerator(map);
        if(map.isWithWater()) {
            waterGenerator.generateWater();
            System.out.println("waterGen:" + (System.nanoTime() - time)/1000000. + " ms");
            time = System.nanoTime();
        }

        if(map.isWithRiver()) {
            RiverGenerator roadGenerator = new RiverGenerator(map);
            roadGenerator.generateRiver(25);
            System.out.println("riverGen:" + (System.nanoTime() - time)/1000000. + " ms");
            time = System.nanoTime();
        }

        if(map.isWithRoad()) {
            RoadGenerator roadGenerator = new RoadGenerator(map);
            roadGenerator.generateRoad(10);
            System.out.println("roadGen:" + (System.nanoTime() - time)/1000000. + " ms");
            time = System.nanoTime();
        }

        BuildingGenerator buildingGenerator = new BuildingGenerator(map);
        if(map.getBuildingsCount() > 0) {
            buildingGenerator.generateAndDrawBuildings(map.getBuildingsCount(), map.getBuildingMaxSize());
            System.out.println("buildingsGen:" + (System.nanoTime() - time)/1000000. + " ms");
            time = System.nanoTime();
        }

        for (int i = 0; i < 5; i++) {
            heightGenerator.shapeMapPieces();
            GeomerticHelper.smooth(map);
        }
        waterGenerator.setHeights();
        buildingGenerator.reflattenBuildings();
        heightGenerator.shapeMapPieces();
        heightGenerator.setNonWalkableBigSlope();
        LightGenerator lightGenerator = new LightGenerator(map);
        lightGenerator.generateLight(50, 50, 1);
        System.out.println("shapeAndLightGen:" + (System.nanoTime() - time)/1000000. + " ms");
        time = System.nanoTime();

        MapObjectsGenerator mapObjectsGenerator = new MapObjectsGenerator(map);
        mapObjectsGenerator.generateObjects();
        System.out.println("objectsGen:" + (System.nanoTime() - time)/1000000. + " ms");
        time = System.nanoTime();

        return map;
    }
}
