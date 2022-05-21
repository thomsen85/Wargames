package edu.ntnu.thosve.units;

import edu.ntnu.thosve.models.units.*;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UnitFactoryTest {

    @Test
    void testGetUnit() {
        double testHealth = 234;
        Unit testUnit1InfantryUnit = UnitFactory.getUnit(UnitType.INFANTRY_UNIT, testHealth);
        Unit testUnit2RangedUnit = UnitFactory.getUnit(UnitType.RANGED_UNIT, testHealth);
        Unit testUnit3CommanderUnit = UnitFactory.getUnit(UnitType.COMMANDER_UNIT, testHealth);
        Unit testUnit4CavalryUnit = UnitFactory.getUnit(UnitType.CAVALRY_UNIT, testHealth);

        assertEquals(testUnit1InfantryUnit.getHealth(), testHealth);
        assertTrue(testUnit1InfantryUnit instanceof InfantryUnit);

        assertEquals(testUnit2RangedUnit.getHealth(), testHealth);
        assertTrue(testUnit2RangedUnit instanceof RangedUnit);

        assertEquals(testUnit3CommanderUnit.getHealth(), testHealth);
        assertTrue(testUnit3CommanderUnit instanceof CommanderUnit);

        assertEquals(testUnit4CavalryUnit.getHealth(), testHealth);
        assertTrue(testUnit4CavalryUnit instanceof CavalryUnit);
    }

    @Test
    void testGetListOfUnits() {
        int amountOfUnits = 100;
        double healthOfUnits = 234;

        List<Unit> units = UnitFactory.getUnits(UnitType.COMMANDER_UNIT, amountOfUnits, healthOfUnits);

        assertEquals(units.size(), amountOfUnits);
        assertEquals(units.get(0).getHealth(), healthOfUnits);
        assertTrue(units.get(0) instanceof CavalryUnit);

    }
}