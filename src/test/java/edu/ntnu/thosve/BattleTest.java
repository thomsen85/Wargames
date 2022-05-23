package edu.ntnu.thosve;

import edu.ntnu.thosve.models.formation.RectangleFormation;
import edu.ntnu.thosve.models.map.Terrain;
import edu.ntnu.thosve.models.map.TileMap;
import edu.ntnu.thosve.models.map.TileMapFactory;
import edu.ntnu.thosve.models.Army;
import edu.ntnu.thosve.models.Battle;
import edu.ntnu.thosve.models.units.InfantryUnit;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BattleTest {

    private final TileMap testMap = TileMapFactory.getUniformTileMap(Terrain.FOREST, 100, 10, 10);

    @Test
    void testThatStrongerArmyWins() {
        Army army1 = new Army("Army 1");
        Army army2 = new Army("Army 2");

        army1.add(new InfantryUnit("Infantry 1", 100));
        army2.add(new InfantryUnit("Infantry 2", 200));

        Battle battle = new Battle(army1, army2, testMap);

        assertEquals(battle.simulate(), army2);
    }

    @Test
    void testThatStrongerArmyWins2() {
        Army army1 = new Army("Army 1");
        Army army2 = new Army("Army 2");

        for (int i = 0; i < 500; i++) {
            army1.add(new InfantryUnit("Infantry 1", 100));
        }

        army1.applyFormation(new RectangleFormation(200, 200, 300, 600));

        for (int i = 0; i < 600; i++) {
            army2.add(new InfantryUnit("Infantry 2", 100));
        }

        army2.applyFormation(new RectangleFormation(500, 200, 700, 600));

        Battle battle = new Battle(army1, army2, testMap);

        assertEquals(battle.simulate(), army2);
    }
}