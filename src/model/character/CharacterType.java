package model.character;

import model.actions.movement.MoveCalculator;

import java.awt.*;
import java.util.Set;

public enum CharacterType {

    HUMAN(0.8, new Stats(0, 0, 0, -5, 5, 0, 0, -10, 10, 0, 0)),
    DWARF(1.2, new Stats(10, 10, 0, -5, 0, -10, -5, 5, -5, 0, 0)),
    ELF(0.6, new Stats(-5, -5, 5, 10, 0, 10, 0, -5, -10, 0, 0)),
    HALFELF(0.7, new Stats(-5, -5, -5, 0, -5, 0, 5, 10, 5, 0, 0)),

    BROWN_BEAR(2, new Stats(45, 40, 30, 25, 35, 30, 5, 5, 5, 2, 6)),
    BLACK_BEAR(2, new Stats(50, 45, 35, 30, 40, 35, 5, 5, 5, 3, 7));


    double size;
    Set<Point> relativePointsUnder;

    private Stats stats;

    CharacterType(double size, Stats stats) {
        this.size = size;
        this.stats = stats;
    }

    public double getSize() {
        return size;
    }

    public Set<Point> getRelativePointsUnder() {
        return relativePointsUnder;
    }

    public Stats getStats() {
        return stats;
    }
}
