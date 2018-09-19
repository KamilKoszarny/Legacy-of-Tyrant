package viewIso;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import model.map.Light;
import model.map.Map;
import model.map.MapPiece;

import java.awt.*;

public class MapPieceDrawer {

    Map map;
    Point zeroScreenPosition;
    int mapPieceScreenSizeX, mapPieceScreenSizeY;


    public void drawMapPiece(GraphicsContext g, Point point){
        MapPiece mapPiece = map.getPoints().get(point);
        double[] xCoords = new double[4];
        double[] yCoords = new double[4];
        xCoords[0] = screenPosition(point).x;
        xCoords[1] = screenPosition(point).x + mapPieceScreenSizeX/2;
        xCoords[2] = screenPosition(point).x;
        xCoords[3] = screenPosition(point).x - mapPieceScreenSizeX/2;
        yCoords[0] = screenPosition(point).y + mapPieceScreenSizeY/2 + mapPiece.getHeightN();
        yCoords[1] = screenPosition(point).y + mapPiece.getHeightE();
        yCoords[2] = screenPosition(point).y - mapPieceScreenSizeY/2 + mapPiece.getHeightS();
        yCoords[3] = screenPosition(point).y + mapPiece.getHeightW();


        Color color = mapPiece.getColor();

        g.setFill(color);
        g.fillPolygon(xCoords, yCoords, 3);
    }

    private Point screenPosition(Point point){
        return new Point(zeroScreenPosition.x + point.x * mapPieceScreenSizeX - point.y * mapPieceScreenSizeX,
                zeroScreenPosition.y + point.y * mapPieceScreenSizeY + point.y * mapPieceScreenSizeY);
    }

}

