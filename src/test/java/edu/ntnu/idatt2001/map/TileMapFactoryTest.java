package edu.ntnu.idatt2001.map;


import edu.ntnu.idatt2001.models.map.Terrain;
import edu.ntnu.idatt2001.models.map.Tile;
import edu.ntnu.idatt2001.models.map.TileMap;
import edu.ntnu.idatt2001.models.map.TileMapFactory;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class TileMapFactoryTest {

    @Test
    void testUniformMapCreation() {
        TileMap map = TileMapFactory.getUniformTileMap(Terrain.FOREST,10,10,10);

        for (int y = 0; y < map.getPixelHeight(); y++) {
            for (int x = 0; x < map.getPixelWidth(); x++) {
                assertEquals(Terrain.FOREST, map.getTerrainFromCoordinates(x,y));
            }
        }
    }

    @Test
    void testRandomMapCreation() {
        TileMap map = TileMapFactory.getRandomTerrainMap(25, 10, 10, 10);

    }
}
