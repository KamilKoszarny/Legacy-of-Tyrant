package model.map;

import java.awt.*;
import java.util.*;

public class MapGenerator {

    Map map;
    int widthM, heightM;

    public Map generateMap(Map map){
        widthM = map.getWidthM();
        heightM = map.getHeightM();
        this.map = map;

        TerrainGenerator terrainGenerator = new TerrainGenerator(map);
        terrainGenerator.generateTerrain();
        HeightGenerator heightGenerator = new HeightGenerator(map);
        heightGenerator.generateHeights();
        LightGenerator lightGenerator = new LightGenerator(map);
        lightGenerator.generateLight(0, 30, 2);
/*
        if(map.isWithRoad()) {
            RoadGenerator roadGenerator = new RoadGenerator(map);
            roadGenerator.generateRoad();
        }

        if(map.getBuildingsCount() > 0) {
            BuildingGenerator buildingGenerator = new BuildingGenerator(map);
            buildingGenerator.generateAndDrawBuildings(map.getBuildingsCount(), map.getBuildingMaxSize());
        }
*/
        return map;
    }


}
