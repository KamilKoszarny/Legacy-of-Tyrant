package controller.isoView;

import controller.javaFX.BattlePaneController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import model.character.Character;
import model.map.Map;
import model.map.MapGenerator;
import model.map.Terrain;

import java.io.IOException;
import java.util.List;

public class IsoViewController {

    Map map;

    @FXML
    void initialize(){
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
}
