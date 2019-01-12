package viewIso;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Polyline;
import javafx.scene.shape.Shape;
import model.character.Character;

public class PathDrawer {

    public static void drawPath(Character character) {
        Pane pane = (Pane) IsoViewer.getCanvas().getParent();


        Polyline polyline = new Polyline();
        Double[] mapPoints = new Double[character.getPath().size() * 2];
        Double[] screenPoints = new Double[character.getPath().size() * 2];








//        pane.getChildren().addAll(shape, label);
    }
}
