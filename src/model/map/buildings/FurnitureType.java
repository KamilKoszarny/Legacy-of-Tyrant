package model.map.buildings;

import java.awt.*;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public enum FurnitureType {
    BED(1, Arrays.asList(new Dimension(5, 3), new Dimension(3, 5)), 2),
    FOOD_TABLE(2, Arrays.asList(new Dimension(4, 2), new Dimension(2, 4)), 2),
    CHEST(3, Arrays.asList(new Dimension(2, 1), new Dimension(1, 2)), 2, true),
    PALLET(4, Arrays.asList(new Dimension(4, 2), new Dimension(2, 4)), 2),
    BIG_TABLE(5, Arrays.asList(new Dimension(5, 3), new Dimension(3, 5)), 2),
    ;

    private int code;
    private List<Dimension> availableSizes;
    private int availableLooks;
    private boolean clickable = false;

    FurnitureType(int code, List<Dimension> availableSizes, int availableLooks) {
        this.code = code;
        this.availableSizes = availableSizes;
        this.availableLooks = availableLooks;
    }

    FurnitureType(int code, List<Dimension> availableSizes, int availableLooks, boolean clickable) {
        this.code = code;
        this.availableSizes = availableSizes;
        this.availableLooks = availableLooks;
        this.clickable = clickable;
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

    public static FurnitureType getRandomType() {
        int index = new Random().nextInt(FurnitureType.values().length);
        return FurnitureType.values()[index];
    }

    public boolean isClickable() {
        return clickable;
    }
}
