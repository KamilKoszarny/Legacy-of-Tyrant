package controller;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import model.Map;
import model.MapGenerator;
import model.Terrain;


public class PaneController {


    @FXML
    private Canvas mapCanvas;

    public PaneController(){
    }

    @FXML
    void initialize(){
        java.util.Map<Terrain, Double> terrainIntensity = new java.util.HashMap<>();
        terrainIntensity.put(Terrain.GROUND, 20.);
        terrainIntensity.put(Terrain.GRASS, 80.);
        terrainIntensity.put(Terrain.BUSH, 40.);
        terrainIntensity.put(Terrain.TREES, 100.);

        Map map = MapGenerator.generateMap(50, 50, terrainIntensity);
        mapCanvas.setHeight(50 * 10);
        mapCanvas.setWidth(50 * 10);

        GraphicsContext g = mapCanvas.getGraphicsContext2D();
        MapDrawer mapDrawer = new MapDrawer(g);
        mapDrawer.drawMap(map);
    }

}
