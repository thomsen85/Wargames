package edu.ntnu.thosve;

import edu.ntnu.thosve.map.Terrain;
import edu.ntnu.thosve.map.TileMap;
import edu.ntnu.thosve.map.TileMapFactory;
import edu.ntnu.thosve.models.units.*;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ArmyTest {

    private final TileMap testMap = TileMapFactory.getUniformTileMap(Terrain.FOREST, 100, 10, 10);

    @Test
    void testThatBlankArmyNameThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> new Army("", testMap));
    }

    @Test
    void testThatNullArmyNameThrowsException() {
        assertThrows(NullPointerException.class, () -> new Army(null, testMap));
    }

    @Test
    void testAdd() {
        InfantryUnit unit = new InfantryUnit("Test Unit", 100);
        Army army = new Army("Test army", testMap);

        army.add(unit);

        assertTrue(army.hasUnits());
    }

    @Test
    void testRemove() {
        InfantryUnit unit = new InfantryUnit("Test Unit", 100);
        Army army = new Army("Test army", testMap);

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
        Army army = new Army("Test army", testMap);
        army.addAll(units);

        assertEquals(units, army.getAllUnits());
    }

    private Army getTestArmy(int numberOfEachUnit) {
        Army army = new Army("Test Army", testMap);
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
            readArmy = Army.readCSV(path, new TileMap(10, 10, 10));
        } catch (IOException e) {
            e.printStackTrace();
        }
        assertNotNull(readArmy);

        Object[] writeArmyClasses = writeArmy.getAllUnits().stream().map(unit -> unit.getClass().getSimpleName())
                .toArray();
        Object[] writeArmyNames = writeArmy.getAllUnits().stream().map(Unit::getName).toArray();
        double[] writeArmyHealths = writeArmy.getAllUnits().stream().mapToDouble(Unit::getHealth).toArray();

        Object[] readArmyClasses = readArmy.getAllUnits().stream().map(unit -> unit.getClass().getSimpleName())
                .toArray();
        Object[] readArmyNames = readArmy.getAllUnits().stream().map(Unit::getName).toArray();
        double[] readArmyHealths = readArmy.getAllUnits().stream().mapToDouble(Unit::getHealth).toArray();

        assertEquals(writeArmy.getName(), readArmy.getName());
        assertArrayEquals(writeArmyHealths, readArmyHealths);
        assertArrayEquals(writeArmyNames, readArmyNames);
        assertArrayEquals(writeArmyClasses, readArmyClasses);
    }

    @Test
    public void testUnitTargetsClosestOpponent() {
        Army army = new Army("Test Army", testMap);
        Army opponentArmy = new Army("Test Opponent Army", testMap);

        InfantryUnit unit = new InfantryUnit("Unit", 100);
        InfantryUnit opponent1 = new InfantryUnit("Opponent 1 (Closest)", 100);
        InfantryUnit opponent2 = new InfantryUnit("Opponent 2 (Farthest)", 100);
        List<Unit> opponents = new ArrayList<>();

        opponent1.setPos(100, 100);
        opponent2.setPos(200, 320);

        opponents.add(opponent1);
        opponents.add(opponent2);

        army.add(unit);
        opponentArmy.addAll(opponents);

        army.update(opponentArmy);

        // Checking if he is moving diagonally
        assertEquals(unit.getXSpeed(), unit.getYSpeed());

        // Checking if he is moving the correct diagonal
        assertTrue(unit.getXSpeed() > 0);
        assertTrue(unit.getYSpeed() > 0);
    }

    @Test
    public void testUnitTargetsClosestOpponentTwo() {
        Army army = new Army("Test Army", testMap);
        Army opponentArmy = new Army("Test Opponent Army", testMap);

        InfantryUnit unit = new InfantryUnit("Unit", 100);
        InfantryUnit opponent1 = new InfantryUnit("Opponent 1 (Closest)", 100);
        InfantryUnit opponent2 = new InfantryUnit("Opponent 2 (Farthest)", 100);
        List<Unit> opponents = new ArrayList<Unit>();

        unit.setPos(500, 500);
        opponent1.setPos(100, 100);
        opponent2.setPos(600, 600);

        opponents.add(opponent1);
        opponents.add(opponent2);

        army.add(unit);
        opponentArmy.addAll(opponents);

        army.update(opponentArmy);

        assertEquals(unit.getCurrentOpponent(), opponent2);

        assertEquals(unit.getXSpeed(), unit.getYSpeed());

        // Checking if he is moving the correct diagonal
        assertTrue(unit.getXSpeed() > 0);
        assertTrue(unit.getYSpeed() > 0);
    }

    @Test
    public void testUnitMovesAtMaxSpeed() {
        Army army = new Army("Test Army", testMap);
        Army opponentArmy = new Army("Test Opponent Army", testMap);

        InfantryUnit unit = new InfantryUnit("Unit", 100);
        InfantryUnit opponent = new InfantryUnit("Opponent", 100);

        opponent.setPos(200, 100);
        List<Unit> opponents = new ArrayList<>();
        opponents.add(opponent);

        army.add(unit);
        opponentArmy.addAll(opponents);

        army.update(opponentArmy);

        double speed = Math.sqrt(Math.pow(unit.getXSpeed(), 2) + Math.pow(unit.getYSpeed(), 2));
        double error = Math.abs((speed - unit.getMaxSpeed()) / unit.getMaxSpeed());

        assertTrue(error <= 0.05);
    }
}