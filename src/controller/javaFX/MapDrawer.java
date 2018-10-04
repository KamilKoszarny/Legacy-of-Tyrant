package controller.javaFX;

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
            g.fillOval(character.getPosition().x / Map.M_PER_POINT * Map.RESOLUTION_M - character.getType().getSize() / Map.M_PER_POINT / 2,
                    character.getPosition().y / Map.M_PER_POINT * Map.RESOLUTION_M - character.getType().getSize() / Map.M_PER_POINT / 2,
                    character.getType().getSize() / Map.M_PER_POINT, character.getType().getSize() / Map.M_PER_POINT);

            if (character.isChosen()){
                if(character.getDoubleRange() < 5)
                    drawCharCircle(character, character.getColor(), character.getType().getSize() + character.getDoubleRange()*2);
                else
                    drawCharCircle(character, character.getColor(), character.getType().getSize() + 2);
            }
            if (character.isTargeted()){
                drawCharCircle(character, Color.RED, character.getType().getSize());
            }
        }

    }

    public void listCharacters(List<Character> characters, TableView<Character> table){
        ObservableList<Character> obsCharacterList = FXCollections.observableArrayList(characters);
        table.setItems(obsCharacterList);
    }

    public void drawPointProximity(Point point){
        Set<Point> pointProximity = new HashSet<>();
        int s = 80;
        for (int i = -s; i < s; i++){
            for (int j = -s; j < s; j++){
                if(point.x + i >= 0 && point.y + j >= 0 && point.x + i < map.getWidth() && point.y + j < map.getHeight())
                pointProximity.add(new Point(point.x + i, point.y + j));
            }
        }
        drawPartOfMap(pointProximity);
    }

    public void drawPartOfMap(Set<Point> points){
        double x, y;
        Color color;
        for (Point point : points){
            x = point.x * Map.RESOLUTION_M / Map.M_PER_POINT;
            y = point.y * Map.RESOLUTION_M / Map.M_PER_POINT;
            color = map.getPoints().get(point).getTerrain().getColor();

            g.setFill(color);
            g.fillRect(x, y, Math.round(Map.RESOLUTION_M / Map.M_PER_POINT), Math.round(Map.RESOLUTION_M / Map.M_PER_POINT));
        }
    }

    private void drawCharCircle(Character character, Color color, double diameter){
        g.setStroke(color);
        g.strokeOval(character.getPosition().x / Map.M_PER_POINT * Map.RESOLUTION_M - diameter/ 2 / Map.M_PER_POINT,
                character.getPosition().y / Map.M_PER_POINT * Map.RESOLUTION_M - diameter / 2 / Map.M_PER_POINT,
                diameter / Map.M_PER_POINT,
                diameter / Map.M_PER_POINT);

    }
}
