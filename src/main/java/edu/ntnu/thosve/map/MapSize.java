package edu.ntnu.thosve.map;

public enum MapSize {
    MAP_1000x1000_5(1000/5, 1000/5, 5),
    MAP_2000x2000_5(2000/5, 2000/5, 5),
    MAP_2000x1000_5(2000/5, 1000/5, 5),
    ;

    private final int tileWidth;
    private final int tileHeight;
    private final int tilePixelSize;

    MapSize(int tileWidth, int tileHeight, int tilePixelSize) {
        this.tileWidth = tileWidth;
        this.tileHeight = tileHeight;
        this.tilePixelSize = tilePixelSize;
    }

    @Override
    public String toString() {
        return tileWidth * tilePixelSize + "x" + tileHeight * tilePixelSize + ", " + tilePixelSize;
    }

    public int getTileWidth() {
        return tileWidth;
    }

    public int getTileHeight() {
        return tileHeight;
    }

    public int getTilePixelSize() {
        return tilePixelSize;
    }
}
