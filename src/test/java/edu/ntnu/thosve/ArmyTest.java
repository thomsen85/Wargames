package edu.ntnu.thosve;

import edu.ntnu.thosve.units.InfantryUnit;
import edu.ntnu.thosve.units.Unit;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ArmyTest {

    @Test
    void testAdd() {
        InfantryUnit unit = new InfantryUnit("Test Unit", 100);
        Army army = new Army("Test army");

        army.add(unit);

        assertTrue(army.hasUnits());
    }

    @Test
    void testRemove() {
        InfantryUnit unit = new InfantryUnit("Test Unit", 100);
        Army army = new Army("Test army");

        army.add(unit);

        assertTrue(army.hasUnits());

        army.remove(unit);

        assertFalse(army.hasUnits());
    }

    @Test
    void testAddAll() {
        List<Unit> units = new ArrayList<Unit>();
        InfantryUnit unit1 = new InfantryUnit("Test Unit 1", 100);
        InfantryUnit unit2 = new InfantryUnit("Test Unit 2", 100);
        units.add(unit1);
        units.add(unit2);
        Army army = new Army("Test army");
        army.addAll(units);

        assertEquals(units, army.getAllUnits());

    }


    @Test
    void testSpreadUnitsEvenly() {
        Army army = new Army("Test army");
        for(int i = 0; i < 6; i++) {
            army.add(new InfantryUnit("Test Unit", 100));
        }

        army.spreadUnitsEvenly(0,0, 200, 100);

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