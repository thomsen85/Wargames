package edu.ntnu.thosve.units;

import edu.ntnu.thosve.models.units.*;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UnitFactoryTest {

    @Test
    void testGetUnit() {
        double testHealth = 234;
        String testName = "testName";
        Unit testUnit1InfantryUnit = UnitFactory.getUnit(UnitType.INFANTRY_UNIT, testName, testHealth);
        Unit testUnit2RangedUnit = UnitFactory.getUnit(UnitType.RANGED_UNIT, testName, testHealth);
        Unit testUnit3CommanderUnit = UnitFactory.getUnit(UnitType.COMMANDER_UNIT, testName, testHealth);
        Unit testUnit4CavalryUnit = UnitFactory.getUnit(UnitType.CAVALRY_UNIT, testName, testHealth);

        assertEquals(testUnit1InfantryUnit.getHealth(), testHealth);
        assertSame(testUnit1InfantryUnit.getName(), testName);
        assertTrue(testUnit1InfantryUnit instanceof InfantryUnit);

        assertEquals(testUnit2RangedUnit.getHealth(), testHealth);
        assertSame(testUnit2RangedUnit.getName(), testName);
        assertTrue(testUnit2RangedUnit instanceof RangedUnit);

        assertEquals(testUnit3CommanderUnit.getHealth(), testHealth);
        assertSame(testUnit3CommanderUnit.getName(), testName);
        assertTrue(testUnit3CommanderUnit instanceof CommanderUnit);

        assertEquals(testUnit4CavalryUnit.getHealth(), testHealth);
        assertSame(testUnit4CavalryUnit.getName(), testName);
        assertTrue(testUnit4CavalryUnit instanceof CavalryUnit);
    }

    @Test
    void testGetListOfUnits() {
        int amountOfUnits = 100;
        double healthOfUnits = 234;
        String nameOfUnits = "testName";

        List<Unit> units = UnitFactory.getUnits(UnitType.COMMANDER_UNIT, amountOfUnits, nameOfUnits, healthOfUnits);

        assertEquals(units.size(), amountOfUnits);
        assertEquals(units.get(0).getName(), nameOfUnits);
        assertEquals(units.get(0).getHealth(), healthOfUnits);
        assertTrue(units.get(0) instanceof CavalryUnit);

    }
}