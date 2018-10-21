package model.map.terrains;

import model.map.Map;
import model.map.MapPiece;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class TerrainGenerator {

    Map map;

    public TerrainGenerator(Map map) {
        this.map = map;
    }

    public void generateTerrain(){
        recalcTerrainIntensity();

        ArrayList<Point> points = new ArrayList<>(map.getPoints().keySet());
//        Collections.shuffle(points);
        connectPoints();

        for (Point point : points) {
            MapPiece mapPiece = map.getPoints().get(point);
            mapPiece.setTerrain(chooseTerrain(point));
        }
    }

    private void recalcTerrainIntensity(){
        double sumIntensity = 0;
        for (Terrain terrain: Terrain.values()) {
            sumIntensity += terrain.getIntensity();
        }

        for (Terrain terrain: Terrain.values()) {
            terrain.setIntensity(terrain.getIntensity() / sumIntensity);
        }
    }

    private void connectPoints(){
        Random r = new Random();
        for (Terrain t: Terrain.values()) {
            ArrayList<Point> terrainFocalPoints = new ArrayList<>();
            Point p = new Point(r.nextInt(map.getWidth()), r.nextInt(map.getHeight()));
            for(int i = 1; i < t.getIntensity() * 10; i++) {
                terrainFocalPoints.add(nearPoint(p));
            }
            t.setFocalPoints(terrainFocalPoints);
        }
    }

    private Terrain chooseTerrain(Point point){

//        return Terrain.BUSH;
        recalcTerrainIntensityBasedOnSector(point);
        recalcTerrainIntensityBasedOnSurrounding(point);
        recalcTerrainIntensity();

        Random rand = new Random();
        double n = rand.nextDouble();
        double ti = 0;

        for (Terrain terrain: Terrain.values()){
            ti += terrain.getIntensity();
            if (n < ti)
                return terrain;
        }
        return null;
    }

    private void recalcTerrainIntensityBasedOnSurrounding(Point point){
        Point surroundPoint;
        Terrain terrain;

        for (int i = -5; i < 5; i += 1)
            for (int j = -5; j < 5; j += 1){
                surroundPoint = new Point(point.x + i, point.y + j);

                if (surroundPoint.x >= 0 && surroundPoint.y >= 0 && surroundPoint.x < map.getWidth() && surroundPoint.y < map.getHeight()) {
                    terrain = map.getPoints().get(surroundPoint).getTerrain();
                    if (terrain != null)
                        terrain.setIntensity(terrain.getIntensity() + 10/point.distance(surroundPoint));
                }
            }
    }

    private void recalcTerrainIntensityBasedOnSector(Point point){
        for (Terrain t: Terrain.values()) {
            for (Point p: t.getFocalPoints()) {
                if (!point.equals(p))
                    t.setIntensity(t.getIntensity() + 25/point.distance(p));
            }
        }
    }


    private Point nearPoint(Point p){
        Random r = new Random();
        Point nearPoint = new Point(p.x + (int)(10*map.getWidth()/((r.nextDouble()-0.5)*map.getWidth())), p.y + (int)(10*map.getHeight()/((r.nextDouble()-0.5)*map.getHeight())));
        return nearPoint;
    }
}
