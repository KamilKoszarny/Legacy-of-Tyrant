

package model.map;

import model.map.mapObjects.MapObject;
import model.map.terrains.Terrain;

import java.awt.*;

public class MapPiece {
    private Terrain terrain;
    private int height = 0;
    private int heightN, heightE, heightS, heightW;
    private int slopeDir, slopeSize;
    private int light;
    private int[] relXCoords, relYCoords;
    private double[] doubleRelXCoords, doubleRelYCoords;
    private MapObject object = null;
    private boolean walkable = true;
    private double transparency = 1.;
    private double visibility = 1;

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

    public double[] getDoubleRelXCoords() {
        return doubleRelXCoords;
    }

    public void setRelXCoords(int[] relXCoords) {
        this.relXCoords = relXCoords;
        doubleRelXCoords = new double[relXCoords.length];
        for (int i = 0; i < relXCoords.length; i++) {
            doubleRelXCoords[i] = relXCoords[i];
        }
    }

    public int[] getRelYCoords() {
        return relYCoords;
    }

    public double[] getDoubleRelYCoords() {
        return doubleRelYCoords;
    }

    public void setRelYCoords(int[] relYCoords) {
        this.relYCoords = relYCoords;
        doubleRelYCoords = new double[relYCoords.length];
        for (int i = 0; i < relYCoords.length; i++) {
            doubleRelYCoords[i] = relYCoords[i];
        }
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

    public int getLight() {
        return light;
    }

    public void setLight(int light) {
        this.light = light;
    }

    public MapObject getObject() {
        return object;
    }

    public void setObject(MapObject object) {
        this.object = object;
    }

    public boolean isWalkable() {
        return walkable;
    }

    public void setWalkable(boolean walkable) {
        this.walkable = walkable;
    }

    public double getTransparency() {
        return transparency;
    }

    public void setTransparency(double transparency) {
        this.transparency = transparency;
    }

    public double getVisibility() {
        return visibility;
    }

    public void setVisibility(double visibility) {
        this.visibility = visibility;
    }
}


