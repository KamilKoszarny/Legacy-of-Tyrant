

package model.map;

import javafx.scene.paint.Color;

import java.awt.*;

public class MapPiece {
    private Terrain terrain;
    private int height = 0;
    private int heightN, heightE, heightS, heightW;
    private int slopeDir, slopeSize;
    private Color color;
    private int light;
    private int[] relXCoords, relYCoords;

    public Terrain getTerrain() {
        return terrain;
    }

    public void setTerrain(Terrain terrain) {
        this.terrain = terrain;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getHeightN() {
        return heightN;
    }

    public void setHeightN(int heightN) {
        this.heightN = heightN;
    }

    public int getHeightE() {
        return heightE;
    }

    public void setHeightE(int heightE) {
        this.heightE = heightE;
    }

    public int getHeightS() {
        return heightS;
    }

    public void setHeightS(int heightS) {
        this.heightS = heightS;
    }

    public int getHeightW() {
        return heightW;
    }

    public void setHeightW(int heightW) {
        this.heightW = heightW;
    }

    public int[] getRelXCoords() {
        return relXCoords;
    }

    public void setRelXCoords(int[] relXCoords) {
        this.relXCoords = relXCoords;
    }

    public int[] getRelYCoords() {
        return relYCoords;
    }

    public void setRelYCoords(int[] relYCoords) {
        this.relYCoords = relYCoords;
    }

    public boolean isClicked(Point relClickPoint) {
        Polygon polygon = new Polygon(relXCoords, relYCoords, 6);
        if (polygon.contains(relClickPoint))
            return true;
        return false;
    }

    public int getSlopeDir() {
        return slopeDir;
    }

    public int getSlopeSize() {
        return slopeSize;
    }

    public void setSlope(int dir, int size) {
        slopeDir = dir;
        slopeSize = size;
    }

    public Color getColor() {
        return color;
    }

    public int getLight() {
        return light;
    }

    public void setLight(int light) {
        this.light = light;
    }
}


