package edu.ntnu.thosve.formations;

import edu.ntnu.thosve.Army;
import edu.ntnu.thosve.units.InfantryUnit;
import edu.ntnu.thosve.units.Unit;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FormationTest {

    @Test
    void testRectangleFormationSpreadsUnitsEvenly() {
        Army army = new Army("Test army");
        for(int i = 0; i < 6; i++) {
            army.add(new InfantryUnit("Test Unit", 100));
        }

        army.applyFormation(new RectangleFormation(0,0, 200, 100));

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

}