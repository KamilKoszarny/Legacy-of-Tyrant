package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.TableView;
import model.character.Character;
import model.map.Map;

import javafx.scene.paint.Color;

import java.awt.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MapDrawer {

    GraphicsContext g;
    Map map;

    public MapDrawer(GraphicsContext g, Map map){
        this.g = g;
        this.map = map;
    }

    public void drawMap(){
        Set<Point> points = map.getPoints().keySet();
        drawPartOfMap(points);
    }

    public void drawCharacters(List<Character> characters){
        for (Character character: characters) {
            g.setFill(character.getColor());
            g.fillOval(character.getPosition().x - character.getType().getSize() / Map.RESOLUTION_M/2,
                    character.getPosition().y - character.getType().getSize() / Map.RESOLUTION_M/2,
                    character.getType().getSize() / Map.RESOLUTION_M, character.getType().getSize() / Map.RESOLUTION_M);

            if (character.isChosen()){
                g.setStroke(Color.WHITE);
                g.strokeOval(character.getPosition().x - character.getType().getSize() / Map.RESOLUTION_M,
                        character.getPosition().y - character.getType().getSize() / Map.RESOLUTION_M,
                        character.getType().getSize() / Map.RESOLUTION_M *2, character.getType().getSize() / Map.RESOLUTION_M *2);
            }
        }
    }

    public void listCharacters(List<Character> characters, TableView<Character> table){
        ObservableList<Character> obsCharacterList = FXCollections.observableArrayList(characters);
        table.setItems(obsCharacterList);
    }

    public void drawPartOfMap(Set<Point> points){
        double x, y;
        Color color;
        for (Point point : points){
            x = point.x;
            y = point.y;
            color = map.getPoints().get(point).getTerrain().getColor();

            g.setFill(color);
            g.fillRect(x, y,1, 1);
        }
    }

    public void drawPointProximity(Point point){
        Set<Point> pointProximity = new HashSet<>();
        int s = 20;
        for (int i = -s; i < s; i++){
            for (int j = -s; j < s; j++){
                if(point.x + i >= 0 && point.y + j >= 0 && point.x + i < map.getWidth() && point.y + j < map.getHeight())
                pointProximity.add(new Point(point.x + i, point.y + j));
            }
        }
        drawPartOfMap(pointProximity);
    }
}
