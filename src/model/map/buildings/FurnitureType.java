package model.map.buildings;

import java.awt.*;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public enum FurnitureType {
    BED(1, Arrays.asList(new Dimension(5, 3), new Dimension(3, 5)), 2),
    ;

    private int code;
    private List<Dimension> availableSizes;
    private int availableLooks;

    FurnitureType(int code, List<Dimension> availableSizes, int availableLooks) {
        this.code = code;
        this.availableSizes = availableSizes;
        this.availableLooks = availableLooks;
    }

    public int getCode() {
        return code;
    }

    public List<Dimension> getAvailableSizes() {
        return availableSizes;
    }

    public int getAvailableLooks() {
        return availableLooks;
    }

    public static int getRandomFurnitureCode(FurnitureType type) {
        Dimension sizes = type.getAvailableSizes().get(new Random().nextInt(type.getAvailableSizes().size()));
        int look = new Random().nextInt(type.getAvailableLooks());
        return 1000*type.getCode() + 100*sizes.width + 10*sizes.height + look;
    }
}
