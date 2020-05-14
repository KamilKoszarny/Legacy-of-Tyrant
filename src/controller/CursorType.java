package controller;

public enum CursorType {
    NORMAL("knights-glove.png"),
    ATTACK("sword.png"),
    ATTACK_DIST("bow.png");

    private String pngName;

    CursorType(String pngName) {
        this.pngName = pngName;
    }

    public String getPngName() {
        return pngName;
    }
}
