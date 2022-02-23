package edu.ntnu.thosve;

import edu.ntnu.thosve.formations.RectangleFormation;
import edu.ntnu.thosve.units.InfantryUnit;
import edu.ntnu.thosve.units.Unit;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BattleTest {

    @Test
    void testThatStrongerArmyWins() {
        Army army1 = new Army("Army 1");
        Army army2 = new Army("Army 2");

        army1.add(new InfantryUnit("Infantry 1", 100));
        army2.add(new InfantryUnit("Infantry 2", 200));

        Battle battle = new Battle(army1, army2);

        assertEquals(battle.simulate(), army2);
    }

    @Test
    void testThatStrongerArmyWins2() {
        Army army1 = new Army("Army 1");
        Army army2 = new Army("Army 2");

        for(int i = 0; i < 500; i++) {
            army1.add(new InfantryUnit("Infantry 1", 100));
        }

        army1.applyFormation(new RectangleFormation(0,0,300, 600));

        for(int i = 0; i < 600; i++) {
            army2.add(new InfantryUnit("Infantry 2", 100));
        }

        army2.applyFormation(new RectangleFormation(500,0,700, 600));

        Battle battle = new Battle(army1, army2);

        assertEquals(battle.simulate(), army2);
    }
}