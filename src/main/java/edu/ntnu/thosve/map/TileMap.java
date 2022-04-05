package edu.ntnu.thosve.map;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.util.*;

public class TileMap {
    Tile[][] tiles;

    private final int tilePixelSize;
    private final int tileWidth;
    private final int tileHeight;

    private final Random random = new Random();


    public TileMap(int tilePixelSize, int tileWidth, int tileHeight) {
        if(tileHeight < 1) {
            throw new IllegalArgumentException("Tile hight can not be: " + tileHeight + ". Must be above 1.");
        }

        this.tilePixelSize = tilePixelSize;
        this.tileHeight = tileHeight;
        this.tileWidth = tileWidth;

        tiles = new Tile[tileHeight][tileWidth];
    }

    public void generateRandomMap(int amountOfRandomPoints) {
        placeRandomPoints(amountOfRandomPoints);
        growPoints();
    }

    private void growPoints() {
        boolean full = false;
        while (!full) {
            full = true;
            for (int y: getRandomUniqueList(tiles.length)) {
                for (int x: getRandomUniqueList(tiles[0].length)) {
                    if (tiles[y][x] == null) {
                        full = false;
                        if (getMostPopularSurrounding(x, y, tiles) != null) {
                            tiles[y][x] = new Tile(getMostPopularSurrounding(x, y, tiles), tilePixelSize);
                        }
                    }
                }
            }
        }
    }

    private void placeRandomPoints(int amountOfRandomPoints) {
        for (int point = 0; point < amountOfRandomPoints; point++) {
            int y = random.nextInt(tiles.length);
            int x = random.nextInt(tiles[0].length);
            Terrain terrain = Terrain.randomTerrain();
            tiles[y][x] = new Tile(terrain, tilePixelSize);
        }
    }

    private List<Integer> getRandomUniqueList(int amount) {
        List<Integer> list = new ArrayList<>(amount);
        for (int i = 0; i < amount; i++) {
            list.add(i);
        }
        Collections.shuffle(list, random);
        return list;

    }

    private Terrain getMostPopularSurrounding(int x, int y, Tile[][] tiles) {
        List<Terrain> terrains = new ArrayList<>();

        for (int yDiff = -1; yDiff <= 1; yDiff++) {
            for (int xDiff = -1; xDiff <= 1; xDiff++) {
                if (!(y + yDiff < 0 || y + yDiff >= tiles.length || x + xDiff < 0 || x + xDiff >= tiles[0].length))
                    if (tiles[y+yDiff][x+xDiff] != null) {
                        terrains.add(tiles[y+yDiff][x+xDiff].terrain());
                    }
            }
        }

        return mostCommonTerrain(terrains);
    }

    private Terrain mostCommonTerrain(List<Terrain> terrains) {
        List<Terrain> differentTerrains = terrains.stream().distinct().toList();
        Terrain mostCommon = null;
        int max = 0;
        for (Terrain terrain : differentTerrains) {
            int numberOfItem = Collections.frequency(terrains, terrain);
            if (numberOfItem > max) {
                mostCommon = terrain;
                max = numberOfItem;
            }
        }
        return mostCommon;
    }

    public Image getMapAsImage() {
        int width = tileWidth * tilePixelSize;
        int height = tileHeight * tilePixelSize;

        WritableImage wr = new WritableImage(width, height);
        PixelWriter pw = wr.getPixelWriter();

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                pw.setColor(x, y, tiles[y/tilePixelSize][x/tilePixelSize].terrain().getColor());
            }
        }
        return new ImageView(wr).getImage();
    }

    public Tile[][] getTiles() {
        return tiles;
    }

    public int getTilePixelSize() {
        return tilePixelSize;
    }

    public int getTileWidth() {
        return tileWidth;
    }

    public int getTileHeight() {
        return tileHeight;
    }
}
