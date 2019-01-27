package model.map;

import helpers.my.GeomerticHelper;
import main.App;
import model.map.buildings.BuildingGenerator;
import model.map.heights.HeightGenerator;
import model.map.lights.LightGenerator;
import model.map.mapObjects.MapObjectsGenerator;
import model.map.roads.RoadGenerator;
import model.map.terrains.TerrainGenerator;
import model.map.water.RiverGenerator;
import model.map.water.WaterGenerator;

public class MapGenerator {

    public Map generateMap(Map map){
        App.resetTime(0);
        App.resetTime(1);

        TerrainGenerator terrainGenerator = new TerrainGenerator(map);
        terrainGenerator.generateTerrain();
        App.showAndResetTime("terrainGen", 1);

        if (App.FULL_MODE) {
            HeightGenerator heightGenerator = new HeightGenerator(map);
            heightGenerator.generateHeights();
            App.showAndResetTime("heightGen", 1);
            

            WaterGenerator waterGenerator = new WaterGenerator(map);
            if (map.isWithWater()) {
                waterGenerator.generateWater();
                App.showAndResetTime("waterGen", 1);
            }

            if (map.isWithRiver()) {
                RiverGenerator roadGenerator = new RiverGenerator(map);
                roadGenerator.generateRiver(25);
                App.showAndResetTime("riverGen", 1);
            }

            if (map.isWithRoad()) {
                RoadGenerator roadGenerator = new RoadGenerator(map);
                roadGenerator.generateRoad(10);
                App.showAndResetTime("roadGen", 1);
            }

            BuildingGenerator buildingGenerator = new BuildingGenerator(map);
            if (map.getBuildingsCount() > 0) {
                buildingGenerator.generateAndDrawBuildings(map.getBuildingsCount(), map.getBuildingMaxSize());
                App.showAndResetTime("buildingsGen", 1);
            }

            for (int i = 0; i < 5; i++) {
                heightGenerator.shapeMapPieces();
                GeomerticHelper.smooth(map);
            }
            waterGenerator.setHeights();
            buildingGenerator.reflattenBuildings();
            waterGenerator.createSwampsAndSands();
            heightGenerator.createRocks();
            heightGenerator.shapeMapPieces();
            heightGenerator.setNonWalkableBigSlope();
            LightGenerator lightGenerator = new LightGenerator(map);
            lightGenerator.generateLight(50, 50, 1);
            App.showAndResetTime("shapeAndLightGen", 1);

            MapObjectsGenerator mapObjectsGenerator = new MapObjectsGenerator(map);
            mapObjectsGenerator.generateObjects();
            App.showAndResetTime("objectsGen", 1);
        }

        App.showAndResetTime("TOTAL", 0);
        return map;
    }
}
