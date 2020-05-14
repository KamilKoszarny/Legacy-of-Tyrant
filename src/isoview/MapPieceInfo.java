package isoview;

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
        contentSB.append("Terrain: ").append(clickedMapPiece.getTerrain()).append("\n");
        contentSB.append("Walkable: ").append(clickedMapPiece.isWalkable()).append("\n");
        contentSB.append("Height: ").append(clickedMapPiece.getHeight()).append("\n");
        contentSB.append("HN: ").append(clickedMapPiece.getHeightN()).append("\n");
        contentSB.append("HE: ").append(clickedMapPiece.getHeightE()).append("\n");
        contentSB.append("HS: ").append(clickedMapPiece.getHeightS()).append("\n");
        contentSB.append("HW: ").append(clickedMapPiece.getHeightW()).append("\n");
        contentSB.append("Slope dir: ").append(clickedMapPiece.getSlopeDir()).append("\n");
        contentSB.append("Slope size: ").append(clickedMapPiece.getSlopeSize()).append("\n");
        contentSB.append("Transparency: ").append(clickedMapPiece.getTransparency()).append("\n");

        MapObject mapObject = clickedMapPiece.getObject();
        if (mapObject != null) {
            contentSB.append("Object: ").append(mapObject.getType()).append("\n");
            contentSB.append("Object size: ").append(mapObject.getSize()).append("\n");
            contentSB.append("Object look: ").append(mapObject.getLook()).append("\n");
        }

        setContentText(contentSB.toString());
        show();
    }
}
