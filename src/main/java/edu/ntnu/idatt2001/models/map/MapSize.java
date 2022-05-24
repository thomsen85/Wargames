package edu.ntnu.idatt2001.models.map;

public enum MapSize {
    MAP_1000x500_5(1000 / 5, 500 / 5, 5),
    MAP_2000x1000_5(2000 / 5, 1000 / 5, 5),
    MAP_4000x2000_5(4000 / 5, 2000 / 5, 5),;

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
        return tileWidth * tilePixelSize + "x" + tileHeight * tilePixelSize;
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
