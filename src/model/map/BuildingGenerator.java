package model.map;

import java.awt.*;
import java.util.*;
import java.util.List;

public class BuildingGenerator {

    static Map map = MapGenerator.map;
    static int minSize = 20;

    static void generateBuildings(int count, int max_size){
        Random r = new Random();
        for (int i = 0; i < count; i++){
            int sizeX = r.nextInt(max_size - minSize) + minSize;
            int sizeY = r.nextInt(max_size - minSize) + minSize;
            int posX = r.nextInt(map.getWidth());
            int posY = r.nextInt(map.getHeight());
            int wallThickness = r.nextInt(5) + 1;

            for (int x = posX; x < posX + sizeX; x++)
                for (int y = posY; y < posY + sizeY; y++)
                    if (MapGenerator.isOnMap(new Point(x, y)))
                        if (x < posX + wallThickness || x >= posX + sizeX - wallThickness ||
                                y < posY + wallThickness || y >= posY + sizeY - wallThickness)
                            map.getPoints().get(new Point(x, y)).setTerrain(Terrain.WALL);
                        else
                            map.getPoints().get(new Point(x, y)).setTerrain(Terrain.GROUND);
        }
    }
}
