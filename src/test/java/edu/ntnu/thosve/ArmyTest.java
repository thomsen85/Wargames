package edu.ntnu.thosve;

import edu.ntnu.thosve.units.*;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class ArmyTest {

    @Test
    void testThatBlankArmyNameThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> new Army(""));
    }

    @Test
    void testThatNullArmyNameThrowsException() {
        assertThrows(NullPointerException.class, () -> new Army(null));
    }

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

    private Army getTestArmy(int numberOfEachUnit) {
        Army army = new Army("Test Army");
        for (int i = 0; i < 10; i++) {
            army.add(new InfantryUnit("TestInfantryUnit"));
            army.add(new RangedUnit("TestRangedUnit"));
            army.add(new CavalryUnit("TestCavalryUnit"));
            army.add(new CommanderUnit("TestCommanderUnit"));

        }
        return army;
    }

    @Test
    void testGetInfantryUnits() {
        int numberOfEachUnit = 10;
        Army testArmy = getTestArmy(numberOfEachUnit);

        List<Unit> infantryUnits = testArmy.getInfantryUnits();

        infantryUnits.forEach(unit -> assertEquals(unit.getClass(), InfantryUnit.class));
        assertEquals(numberOfEachUnit, infantryUnits.size());
    }

    @Test
    void testGetCavalryUnits() {
        int numberOfEachUnit = 10;
        Army testArmy = getTestArmy(numberOfEachUnit);

        List<Unit> cavalryUnit = testArmy.getCavalryUnits();

        cavalryUnit.forEach(unit -> assertEquals(unit.getClass(), CavalryUnit.class));
        assertEquals(numberOfEachUnit, cavalryUnit.size());
    }

    @Test
    void testGetRangedUnits() {
        int numberOfEachUnit = 10;
        Army testArmy = getTestArmy(numberOfEachUnit);

        List<Unit> rangedUnits = testArmy.getRangedUnits();

        rangedUnits.forEach(unit -> assertEquals(unit.getClass(), RangedUnit.class));
        assertEquals(numberOfEachUnit, rangedUnits.size());

    }

    @Test
    void testGetCommanderUnits() {
        int numberOfEachUnit = 10;
        Army testArmy = getTestArmy(numberOfEachUnit);

        List<Unit> commanderUnits = testArmy.getCommanderUnits();

        commanderUnits.forEach(unit -> assertEquals(unit.getClass(), CommanderUnit.class));
        assertEquals(numberOfEachUnit, commanderUnits.size());
    }

    @Test
    void testCSVWritingAndReading() {
        Army writeArmy = getTestArmy(10);
        writeArmy.add(new CommanderUnit("SuperTest", 1));

        String path = "testCSVWritingAndReading.csv";
        try {
            writeArmy.writeCSV(path);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Army readArmy = null;
        try {
            readArmy = Army.readCSV(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        assertNotNull(readArmy);

        Object[] writeArmyClasses = writeArmy.getAllUnits().stream()
                .map(unit -> unit.getClass().getSimpleName()).toArray();
        Object[] writeArmyNames = writeArmy.getAllUnits().stream().map(Unit::getName).toArray();
        double[] writeArmyHealths = writeArmy.getAllUnits().stream().mapToDouble(Unit::getHealth).toArray();

        Object[] readArmyClasses = readArmy.getAllUnits().stream()
                .map(unit -> unit.getClass().getSimpleName()).toArray();
        Object[] readArmyNames = readArmy.getAllUnits().stream().map(Unit::getName).toArray();
        double[] readArmyHealths = readArmy.getAllUnits().stream().mapToDouble(Unit::getHealth).toArray();


        assertEquals(writeArmy.getName(), readArmy.getName());
        assertArrayEquals(writeArmyHealths, readArmyHealths);
        assertArrayEquals(writeArmyNames, readArmyNames);
        assertArrayEquals(writeArmyClasses, readArmyClasses);

    }
}