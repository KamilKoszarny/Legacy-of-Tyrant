package model.character;

import model.MoveCalculator;

import java.awt.*;
import java.util.Set;

public enum CharacterType {

    HUMAN(0.8),
    DWARF(1.2),
    ELF(0.6),
    HALFELF(0.7);

    double size;
    Set<Point> relativePointsUnder;

    CharacterType(double size) {
        this.size = size;
        relativePointsUnder = MoveCalculator.calcRelativePointsUnder(this);
    }

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }

    public Set<Point> getRelativePointsUnder() {
        return relativePointsUnder;
    }

    public void setRelativePointsUnder(Set<Point> relativePointsUnder) {
        this.relativePointsUnder = relativePointsUnder;
    }
}
