package model.map;

import helpers.downloaded.pathfinding.grid.GridGraph;
import helpers.my.GeomerticHelper;
import model.map.buildings.BuildingGenerator;
import model.map.heights.HeightGenerator;
import model.map.lights.LightGenerator;
import model.map.mapObjects.MapObjectsGenerator;
import model.map.roads.RoadGenerator;
import model.map.terrains.TerrainGenerator;
import model.map.water.RiverGenerator;
import model.map.water.WaterGenerator;

import java.awt.*;

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

        if(map.isWithWater()) {
            WaterGenerator waterGenerator = new WaterGenerator(map);
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

        map.setGridGraph(generateGridGraph(map));
        System.out.println("gridGen:" + (System.nanoTime() - time)/1000000. + " ms");
        time = System.nanoTime();

        return map;
    }

    @SuppressWarnings("PointlessArithmeticExpression")
    private GridGraph generateGridGraph(Map map) {
        GridGraph gridGraph = map.getGridGraph();

        MapPiece mapPiece;
        int x, y;
        for (Point point: map.getPoints().keySet()) {
            mapPiece = map.getPoints().get(point);
            if (!mapPiece.isWalkable()) {
                x = point.x;
                y = point.y;
                gridGraph.trySetBlocked(x*4 - 1, y*4 + 2, true);
                gridGraph.setBlocked(x*4 + 0, y*4 + 1, true);
                gridGraph.setBlocked(x*4 + 0, y*4 + 2, true);
                gridGraph.trySetBlocked(x*4 + 1, y*4 - 1, true);
                gridGraph.setBlocked(x*4 + 1, y*4 + 0, true);
                gridGraph.setBlocked(x*4 + 1, y*4 + 1, true);
                gridGraph.setBlocked(x*4 + 1, y*4 + 2, true);
                gridGraph.setBlocked(x*4 + 1, y*4 + 3, true);
                gridGraph.setBlocked(x*4 + 2, y*4 + 0, true);
                gridGraph.setBlocked(x*4 + 2, y*4 + 1, true);
                gridGraph.setBlocked(x*4 + 2, y*4 + 2, true);
                gridGraph.setBlocked(x*4 + 2, y*4 + 3, true);
                gridGraph.trySetBlocked(x*4 + 2, y*4 + 4, true);
                gridGraph.setBlocked(x*4 + 3, y*4 + 1, true);
                gridGraph.setBlocked(x*4 + 3, y*4 + 2, true);
                gridGraph.trySetBlocked(x*4 + 4, y*4 + 1, true);
            }
        }
        return gridGraph;
    }


}
