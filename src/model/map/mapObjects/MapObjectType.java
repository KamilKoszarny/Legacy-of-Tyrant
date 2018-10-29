package model.map.mapObjects;

public enum MapObjectType {
    TREE(3, 8);

    private int sizes, looks;

    MapObjectType(int sizes, int looks) {
        this.sizes = sizes;
        this.looks = looks;
    }

    int getSizes() {
        return sizes;
    }

    int getLooks() {
        return looks;
    }
}
