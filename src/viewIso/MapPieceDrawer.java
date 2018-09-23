package viewIso;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import model.map.Map;
import model.map.MapPiece;
import model.map.Terrain;

import java.awt.*;

public class MapPieceDrawer {

    private Map map;
    private GraphicsContext gc;
    private MapDrawer md;
    private int mapPieceScreenSizeX;
    private int mapPieceScreenSizeY;

    MapPieceDrawer(Map map, GraphicsContext gc, MapDrawer md, int mapPieceScreenSizeX, int mapPieceScreenSizeY) {
        this.map = map;
        this.gc = gc;
        this.md = md;
        this.mapPieceScreenSizeX = mapPieceScreenSizeX;
        this.mapPieceScreenSizeY = mapPieceScreenSizeY;
    }

    public void drawMapPiece(Point point){
        MapPiece mapPiece = map.getPoints().get(point);
        double[] xCoords = new double[4];
        double[] yCoords = new double[4];
        xCoords[0] = md.screenPosition(point).x;
        xCoords[1] = md.screenPosition(point).x + mapPieceScreenSizeX/2;
        xCoords[2] = md.screenPosition(point).x;
        xCoords[3] = md.screenPosition(point).x - mapPieceScreenSizeX/2;
        yCoords[0] = md.screenPosition(point).y - mapPieceScreenSizeY/2 - mapPiece.getHeightN();
        yCoords[1] = md.screenPosition(point).y - mapPiece.getHeightE();
        yCoords[2] = md.screenPosition(point).y + mapPieceScreenSizeY/2 - mapPiece.getHeightS();
        yCoords[3] = md.screenPosition(point).y - mapPiece.getHeightW();


        Color color = mapPiece.getColor();

        gc.setFill(color);
        gc.fillPolygon(xCoords, yCoords, 4);
    }
}

