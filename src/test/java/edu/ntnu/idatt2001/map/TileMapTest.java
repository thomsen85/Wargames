package edu.ntnu.idatt2001.map;

import edu.ntnu.idatt2001.models.map.Terrain;
import edu.ntnu.idatt2001.models.map.Tile;
import edu.ntnu.idatt2001.models.map.TileMap;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TileMapTest {
    @Test
    void testThatIllegalArgumentsThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> new TileMap(-10, 10 , 10));
        assertThrows(IllegalArgumentException.class, () -> new TileMap(10, -10 , 10));
        assertThrows(IllegalArgumentException.class, () -> new TileMap(10, 10 , -10));

        assertThrows(IllegalArgumentException.class, () -> new TileMap(0, 10 , 10));
        assertThrows(IllegalArgumentException.class, () -> new TileMap(10, 0 , 10));
        assertThrows(IllegalArgumentException.class, () -> new TileMap(10, 10 , 0));
    }

    @Test
    void testThatCorrectTerrainGetsRetrieved() {
        TileMap map = new TileMap(10,10,10);

        map.setTile(0,0, new Tile(Terrain.FOREST, 10));

        assertEquals(Terrain.FOREST, map.getTerrainFromCoordinates(1,1));
    }

}
