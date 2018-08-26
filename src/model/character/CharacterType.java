package model.character;

public enum CharacterType {

    HUMAN(0.8),
    DWARF(1.2),
    ELF(0.6),
    HALFELF(0.7);

    double size;

    CharacterType(double size) {
        this.size = size;
    }

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }
}
