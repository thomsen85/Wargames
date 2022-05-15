package edu.ntnu.thosve.formations;

import edu.ntnu.thosve.map.Terrain;
import edu.ntnu.thosve.map.TileMap;
import edu.ntnu.thosve.map.TileMapFactory;
import edu.ntnu.thosve.models.Army;
import edu.ntnu.thosve.models.formations.Formation;
import edu.ntnu.thosve.models.formations.RectangleFormation;
import edu.ntnu.thosve.models.formations.TriangleFormation;
import edu.ntnu.thosve.models.units.InfantryUnit;
import edu.ntnu.thosve.models.units.Unit;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FormationTest {
    private final TileMap testMap = TileMapFactory.getUniformTileMap(Terrain.FOREST, 10, 10, 10);

    @Test
    void testRectangleFormationSpreadsUnitsEvenly() {
        Army army = new Army("Test army", testMap);
        for (int i = 0; i < 6; i++) {
            army.add(new InfantryUnit("Test Unit", 100));
        }

        army.applyFormation(new RectangleFormation(0, 0, 200, 100));

        List<Unit> units = army.getAllUnits();

        assertEquals(0, units.get(0).getX());
        assertEquals(0, units.get(0).getY());

        assertEquals(100, units.get(1).getX());
        assertEquals(0, units.get(1).getY());

        assertEquals(200, units.get(2).getX());
        assertEquals(0, units.get(2).getY());

        assertEquals(0, units.get(3).getX());
        assertEquals(100, units.get(3).getY());

        assertEquals(100, units.get(4).getX());
        assertEquals(100, units.get(4).getY());

        assertEquals(200, units.get(5).getX());
        assertEquals(100, units.get(5).getY());

    }

    @Test
    void testTriangleFormation() {
        Army army = new Army("Test army", testMap);
        for (int i = 0; i < 100; i++) {
            army.add(new InfantryUnit("Test Unit", 100));
        }

        Formation formation = new TriangleFormation(100, 100, 900, 900);
        formation.spreadUnits(army.getAllUnits());

    }

}