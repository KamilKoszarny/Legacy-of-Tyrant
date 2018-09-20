package model;

import model.map.Map;
import model.map.MapGenerator;
import model.map.Terrain;

public class Battle {

    Map map;

    public Battle() {
        initMap();
    }

    private void initMap(){
        Terrain.GROUND.setIntensity(30);
        Terrain.GRASS.setIntensity(80);
        Terrain.BUSH.setIntensity(40);
        Terrain.TREES.setIntensity(20);

        MapGenerator mapGenerator = new MapGenerator();
        map = mapGenerator.generateMap(50, 50);
    }

    public Map getMap() {
        return map;
    }
}
