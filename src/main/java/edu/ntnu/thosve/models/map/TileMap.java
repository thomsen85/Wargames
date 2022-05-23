package edu.ntnu.thosve.models.map;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;

import java.io.Serializable;
import java.util.*;

/**
 * Class for holding multiple terrains in "tiles". The amount of tiles can be customized, but a tile must be square.
 */
public class TileMap implements Serializable {
    private final Tile[][] tiles;

    private final int tilePixelSize;
    private final int tileWidth;
    private final int tileHeight;

    private final Random random = new Random();

    /**
     * Contructor
     * 
     * @param tilePixelSize
     * @param tileWidth
     * @param tileHeight
     */
    public TileMap(int tilePixelSize, int tileWidth, int tileHeight) {
        if (tileHeight < 1) {
            throw new IllegalArgumentException("Tile height can not be: " + tileHeight + ". Must be above 1.");
        }

        this.tilePixelSize = tilePixelSize;
        this.tileHeight = tileHeight;
        this.tileWidth = tileWidth;

        tiles = new Tile[tileHeight][tileWidth];
    }

    /**
     * Method for getting a terrain from coordinates on the tileMap.
     * 
     * @param x
     *            coordinate
     * @param y
     *            coordinate
     * @return Terrain of the given x and y coordinate.
     */
    public Terrain getTerrainFromCoordinates(int x, int y) {
        if (x < 0 || x > tileWidth * tilePixelSize) {
            throw new IllegalArgumentException(
                    x + " :x coordinate is out of range. Max is " + (tileWidth * tilePixelSize));
        } else if (y < 0 || y > tileHeight * tilePixelSize) {
            throw new IllegalArgumentException(
                    y + " :y coordinate is out of range. Max is " + (tileHeight * tilePixelSize));
        }

        int xTile = x / tilePixelSize;
        int yTile = y / tilePixelSize;

        return tiles[yTile][xTile].terrain();
    }

    /**
     * Method for getting a JavaFX Image representation of the tile map
     * 
     * @return the image
     */
    public Image getMapAsImage() {
        int width = tileWidth * tilePixelSize;
        int height = tileHeight * tilePixelSize;

        WritableImage wr = new WritableImage(width, height);
        PixelWriter pw = wr.getPixelWriter();

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                pw.setColor(x, y, tiles[y / tilePixelSize][x / tilePixelSize].terrain().getColor());
            }
        }
        return new ImageView(wr).getImage();
    }

    public void setTile(int x, int y, Tile tile) {
        tiles[y][x] = tile;
    }

    public Tile getTile(int x, int y) {
        return tiles[y][x];
    }

    public int getTileHeight() {
        return tiles.length;
    }

    public int getTileWidth() {
        return tiles[0].length;
    }

    public int getTilePixelSize() {
        return tilePixelSize;
    }

    public int getPixelWidth() {
        return getTileWidth() * getTilePixelSize();
    }

    public int getPixelHeight() {
        return getTileHeight() * getTilePixelSize();
    }

}
