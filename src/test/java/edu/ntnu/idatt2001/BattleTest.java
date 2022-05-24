package edu.ntnu.idatt2001;

import edu.ntnu.idatt2001.models.formation.RectangleFormation;
import edu.ntnu.idatt2001.models.map.Terrain;
import edu.ntnu.idatt2001.models.map.TileMap;
import edu.ntnu.idatt2001.models.map.TileMapFactory;
import edu.ntnu.idatt2001.models.Army;
import edu.ntnu.idatt2001.models.Battle;
import edu.ntnu.idatt2001.models.units.InfantryUnit;
import edu.ntnu.idatt2001.models.units.UnitFactory;
import edu.ntnu.idatt2001.models.units.UnitType;
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

    @Test
    void testThatIllegalArgumentsThrowsExceptions() {
        Army army = new Army("TestArmy");

        assertThrows(NullPointerException.class, () -> new Battle(null, null, null));
        assertThrows(IllegalArgumentException.class, () -> new Battle(army, army, testMap));
    }

    @Test
    void testResettingOfBattle() {
        Army army1 = new Army("TestName");
        Army army2 = new Army("TestName");
        Battle battle = new Battle(army1, army2, testMap);

        army1.add(UnitFactory.getUnit(UnitType.INFANTRY_UNIT));
        army2.addAll(UnitFactory.getUnits(UnitType.COMMANDER_UNIT, 100));

        battle.setResetPoint();
        assertTrue(battle.getArmyOne().hasUnits());
        battle.simulate();
        assertFalse(battle.getArmyOne().hasUnits());
        battle.reset();
        assertTrue(battle.getArmyOne().hasUnits());
    }
}