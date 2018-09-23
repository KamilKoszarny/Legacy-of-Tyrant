

package model.map;

import javafx.scene.paint.Color;

public class MapPiece {
    Terrain terrain;
    int height = 0;
    int heightN, heightE, heightS, heightW;
    int slopeDir, slopeSize;
    Color color;

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

    public void setColor(Color color) {
        this.color = color;
    }
}


