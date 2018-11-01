package viewIso;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import model.map.MapPiece;
import model.map.mapObjects.MapObject;

import java.awt.*;

public class MapPieceInfo extends Alert {

    public MapPieceInfo(MapPiece clickedMapPiece, Point point) {
        super(Alert.AlertType.INFORMATION, "", ButtonType.OK);
        setTitle("MapPiece info");
        setHeaderText("Map piece nr " + clickedMapPiece.hashCode() + " at " + point);

        StringBuilder contentSB = new StringBuilder();
        contentSB.append("Terrain: " + clickedMapPiece.getTerrain() + "\n");
        contentSB.append("Height: " + clickedMapPiece.getHeight() + "\n");
        contentSB.append("HN: " + clickedMapPiece.getHeightN() + "\n");
        contentSB.append("HE: " + clickedMapPiece.getHeightE() + "\n");
        contentSB.append("HS: " + clickedMapPiece.getHeightS() + "\n");
        contentSB.append("HW: " + clickedMapPiece.getHeightW() + "\n");
        contentSB.append("Slope dir: " + clickedMapPiece.getSlopeDir() + "\n");
        contentSB.append("Slope size: " + clickedMapPiece.getSlopeSize() + "\n");

        MapObject mapObject = clickedMapPiece.getObject();
        if (mapObject != null) {
            contentSB.append("Object: " + mapObject.getType() + "\n");
            contentSB.append("Object size: " + mapObject.getSize() + "\n");
            contentSB.append("Object look: " + mapObject.getLook() + "\n");
        }

        setContentText(contentSB.toString());
        show();
    }
}
