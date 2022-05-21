package edu.ntnu.thosve.map;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Factory class for TileMaps that offers different types of maps.
 */
public class TileMapFactory {
    private static final Random random = new Random();

    private TileMapFactory() {
    }

    /**
     * Method for filling the entire map with one given terrain.
     * 
     * @param terrain
     *            to fill the whole map with
     * @param tilePixelSize
     *            of the tile map
     * @param tileWidth
     *            of the tile map
     * @param tileHeight
     *            of the tile map
     */
    public static TileMap getUniformTileMap(Terrain terrain, int tilePixelSize, int tileWidth, int tileHeight) {
        TileMap tileMap = new TileMap(tilePixelSize, tileWidth, tileHeight);
        for (int x = 0; x < tileMap.getTileWidth(); x++) {
            for (int y = 0; y < tileMap.getTileHeight(); y++) {
                tileMap.setTile(x, y, new Tile(terrain, tilePixelSize));
            }
        }

        return tileMap;
    }

    /**
     * Method for randomizing the tile map with the different types of terrain specifed in the {@link Terrain} Enum. The
     * method takes in how many random points to set down on the map. From those random points it will grow those points
     * as much as they can unitl they intersect another growing point.
     *
     * This means in practice, the more random points you have, the more the terrain varies.
     * 
     * @param amountOfRandomPoints
     *            amount of points to grow from, the more points the more variance in the tilemap.
     * @param tilePixelSize
     *            of the tile map
     * @param tileWidth
     *            of the tile map
     * @param tileHeight
     *            of the tile map
     */
    public static TileMap getRandomTerrainMap(int amountOfRandomPoints, int tilePixelSize, int tileWidth,
            int tileHeight) {
        TileMap tileMap = new TileMap(tilePixelSize, tileWidth, tileHeight);
        placeRandomPoints(tileMap, amountOfRandomPoints);
        growPoints(tileMap);

        return tileMap;
    }

    /**
     * Method for placing a given amount of random points on the tileMap
     * 
     * @param tileMap
     *            to add the points to
     * @param amountOfRandomPoints
     *            points to create
     */
    private static void placeRandomPoints(TileMap tileMap, int amountOfRandomPoints) {
        for (int point = 0; point < amountOfRandomPoints; point++) {
            int x = random.nextInt(tileMap.getTileWidth());
            int y = random.nextInt(tileMap.getTileHeight());
            Terrain terrain = Terrain.randomTerrain();
            tileMap.setTile(x, y, new Tile(terrain, tileMap.getTilePixelSize()));
        }
    }

    /**
     * Method for growing points, by looking for points and extending them if found. It iterates randombly or else the
     * first found point would be grown to the whole map.
     * 
     * @param tileMap
     *            to grow the points on
     */
    private static void growPoints(TileMap tileMap) {
        boolean full = false;
        while (!full) {
            full = true;
            for (int x : getRandomUniqueList(tileMap.getTileWidth())) {
                for (int y : getRandomUniqueList(tileMap.getTileHeight())) {
                    if (tileMap.getTile(x, y) == null) {
                        full = false;
                        if (getMostPopularSurrounding(tileMap, x, y) != null) {
                            tileMap.setTile(x, y,
                                    new Tile(getMostPopularSurrounding(tileMap, x, y), tileMap.getTilePixelSize()));
                        }
                    }
                }
            }
        }
    }

    /**
     * Method for retrieving a shuffled list given the amount that it should go to, not including the number. So if you
     * were to use 100 as the amount you would get a list [0, 1, 2, ..., 98, 99], a list of 100 values starting at 0
     * randomly shuffled.
     * 
     * @param amount
     *            amount of elements to create in the list.
     * @return the random list
     */
    private static List<Integer> getRandomUniqueList(int amount) {
        List<Integer> list = new ArrayList<>(amount);
        for (int i = 0; i < amount; i++) {
            list.add(i);
        }
        Collections.shuffle(list, random);
        return list;

    }

    /**
     * Method for finding the most popular terrain in a 3x3 area. Useful when growing points.
     *
     * @param tileMap
     *            to check in.
     * @param x
     *            coordinate of tile
     * @param y
     *            coordinate of tile
     * @return most popular terrain.
     */
    private static Terrain getMostPopularSurrounding(TileMap tileMap, int x, int y) {
        List<Terrain> terrains = new ArrayList<>();

        for (int yDiff = -1; yDiff <= 1; yDiff++) {
            for (int xDiff = -1; xDiff <= 1; xDiff++) {
                if (!(y + yDiff < 0 || y + yDiff >= tileMap.getTileHeight() || x + xDiff < 0
                        || x + xDiff >= tileMap.getTileWidth()))
                    if (tileMap.getTile(x + xDiff, y + yDiff) != null) {
                        terrains.add(tileMap.getTile(x + xDiff, y + yDiff).terrain());
                    }
            }
        }

        return mostCommonTerrain(terrains);
    }

    /**
     * Method for getting the most common terrain in a given list of terrain.
     * 
     * @param terrains
     *            to fin the most common from
     * @return the most common terrain
     */
    private static Terrain mostCommonTerrain(List<Terrain> terrains) {
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

}
