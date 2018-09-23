package model;

import model.map.*;

public class Battle {

    Map map;

    public Battle() {
        initMap();
    }

    private void initMap(){
        boolean[] roadSides = {false, false, false, false};
        map = new Map(50, 50, MapType.FOREST, MapHeightType.MOUTAINS, roadSides);
    }

    public Map getMap() {
        return map;
    }
}
