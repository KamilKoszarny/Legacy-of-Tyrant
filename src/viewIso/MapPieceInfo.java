package viewIso;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import model.map.MapPiece;

import java.awt.*;

public class MapPieceInfo extends Alert {

    public MapPieceInfo(MapPiece clickedMapPiece, Point point) {
        super(Alert.AlertType.INFORMATION, "", ButtonType.OK);
        setTitle("MapPiece info");
        setHeaderText("Map piece nr " + clickedMapPiece.hashCode() + " at " + point);
        setContentText("Terrain: " + clickedMapPiece.getTerrain() + "\n" +
                "Height: " + clickedMapPiece.getHeight() + "\n" +
                "HN: " + clickedMapPiece.getHeightN() + "\n" +
                "HE: " + clickedMapPiece.getHeightE() + "\n" +
                "HS: " + clickedMapPiece.getHeightS() + "\n" +
                "HW: " + clickedMapPiece.getHeightW() + "\n" +
                "Slope dir: " + clickedMapPiece.getSlopeDir() + "\n" +
                "Slope size: " + clickedMapPiece.getSlopeSize() + "\n");
        show();
    }
}
