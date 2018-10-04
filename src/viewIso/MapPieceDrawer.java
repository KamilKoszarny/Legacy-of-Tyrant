package viewIso;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import model.map.heights.HeightGenerator;
import model.map.Map;
import model.map.MapPiece;

import java.awt.*;

public class MapPieceDrawer {

    private Map map;
    private GraphicsContext gc;
    private MapDrawer md;

    MapPieceDrawer(Map map, GraphicsContext gc, MapDrawer md, int mapPieceScreenSizeX, int mapPieceScreenSizeY) {
        this.map = map;
        this.gc = gc;
        this.md = md;

        calcMapPiecesScreenRelPositions(mapPieceScreenSizeX, mapPieceScreenSizeY);
    }

    private void calcMapPiecesScreenRelPositions(int mapPieceScreenSizeX, int mapPieceScreenSizeY){
        MapPiece mapPiece;

        for (Point point: map.getPoints().keySet()) {
            int[] relXCoords = new int[6];
            int[] relYCoords = new int[6];
            mapPiece = map.getPoints().get(point);
            relXCoords[0] = md.relativeScreenPosition(point).x;
            relXCoords[1] = md.relativeScreenPosition(point).x + mapPieceScreenSizeX/2;
            relXCoords[2] = md.relativeScreenPosition(point).x + mapPieceScreenSizeX/2;
            relXCoords[3] = md.relativeScreenPosition(point).x;
            relXCoords[4] = md.relativeScreenPosition(point).x - mapPieceScreenSizeX/2;
            relXCoords[5] = md.relativeScreenPosition(point).x - mapPieceScreenSizeX/2;
            relYCoords[0] = md.relativeScreenPosition(point).y - mapPieceScreenSizeY/2 - mapPiece.getHeightN() / HeightGenerator.H_PEX_PIX - 1;
            relYCoords[1] = md.relativeScreenPosition(point).y - mapPiece.getHeightE() / HeightGenerator.H_PEX_PIX - 1;
            relYCoords[2] = md.relativeScreenPosition(point).y - mapPiece.getHeightE() / HeightGenerator.H_PEX_PIX;
            relYCoords[3] = md.relativeScreenPosition(point).y + mapPieceScreenSizeY/2 - mapPiece.getHeightS() / HeightGenerator.H_PEX_PIX;
            relYCoords[4] = md.relativeScreenPosition(point).y - mapPiece.getHeightW() / HeightGenerator.H_PEX_PIX;
            relYCoords[5] = md.relativeScreenPosition(point).y - mapPiece.getHeightW() / HeightGenerator.H_PEX_PIX - 1;
            mapPiece.setRelXCoords(relXCoords);
            mapPiece.setRelYCoords(relYCoords);
        }
    }

    public void drawMapPiece(Point point){
        MapPiece mapPiece = map.getPoints().get(point);
        double[] xCoords = new double[6];
        double[] yCoords = new double[6];
        for (int i = 0; i < 6; i++) {
            xCoords[i] = md.getZeroScreenPosition().x + mapPiece.getRelXCoords()[i];
            yCoords[i] = md.getZeroScreenPosition().y + mapPiece.getRelYCoords()[i];
        }
        Color color = mapPiece.getTerrain().getColor();
        color = color.deriveColor(0, 1, .5 + (double)mapPiece.getLight()/100, 1);

        gc.setFill(color);
        gc.fillPolygon(xCoords, yCoords, 6);
    }
}

