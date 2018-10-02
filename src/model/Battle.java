package model;

import model.map.*;

public class Battle {

    Map map;

    public Battle() {
        initMap();
    }

    private void initMap(){
        boolean[] roadSides = {false, true, true, true};
        map = new Map(50, 50, MapType.FOREST, MapHeightType.PEAK, roadSides);
    }

    public Map getMap() {
        return map;
    }
}
