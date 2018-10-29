package model.map;

import model.map.buildings.BuildingGenerator;
import model.map.heights.HeightGenerator;
import model.map.lights.LightGenerator;
import model.map.mapObjects.MapObjectsGenerator;
import model.map.roads.RoadGenerator;
import model.map.terrains.TerrainGenerator;

public class MapGenerator {

    public Map generateMap(Map map){
        TerrainGenerator terrainGenerator = new TerrainGenerator(map);
        terrainGenerator.generateTerrain();
        HeightGenerator heightGenerator = new HeightGenerator(map);
        heightGenerator.generateHeights();

        if(map.isWithRoad()) {
            RoadGenerator roadGenerator = new RoadGenerator(map);
            roadGenerator.generateRoad();
        }

        if(map.getBuildingsCount() > 0) {
            BuildingGenerator buildingGenerator = new BuildingGenerator(map);
            buildingGenerator.generateAndDrawBuildings(map.getBuildingsCount(), map.getBuildingMaxSize());
        }

        heightGenerator.shapeMapPieces();
        LightGenerator lightGenerator = new LightGenerator(map);
        lightGenerator.generateLight(50, 50, 1);

        MapObjectsGenerator mapObjectsGenerator = new MapObjectsGenerator(map);
        mapObjectsGenerator.generateObjects();

        return map;
    }


}
