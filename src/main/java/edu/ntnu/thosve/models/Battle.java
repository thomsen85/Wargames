package edu.ntnu.thosve.models;

import edu.ntnu.thosve.models.map.TileMap;
import edu.ntnu.thosve.models.units.Unit;

import java.io.*;
import java.util.List;
import java.util.stream.Stream;

/**
 * Class for managing Two armies.
 */
public class Battle implements Serializable {
    private final String RESET_FILE = "/tmp/battle-reset-point.ser";

    private Army armyOne;
    private Army armyTwo;
    private final TileMap tileMap;

    /**
     * Constructor for creating an instance of the Battle.
     * 
     * @param armyOne
     *            first army, does not need to contain tileMap
     * @param armyTwo
     *            second army, does not need to contain tileMap
     * @param tileMap
     *            map that battle is placed on.
     */
    public Battle(Army armyOne, Army armyTwo, TileMap tileMap) {
        this.armyOne = armyOne;
        this.armyTwo = armyTwo;
        this.tileMap = tileMap;

        this.armyOne.setTileMap(tileMap);
        this.armyTwo.setTileMap(tileMap);

    }

    public Army simulate() {
        while (armyOne.hasUnits() && armyTwo.hasUnits()) {
            armyOne.update(armyTwo);
            if (!armyOne.hasUnits()) {
                break;
            }
            armyTwo.update(armyOne);
        }

        return armyOne.hasUnits() ? armyOne : armyTwo;
    }

    /**
     *
     * @param deltaTime
     *            in seconds
     * @return false if on army is empty of units.
     */
    public boolean simulateStep(double deltaTime) {
        if (!armyTwo.hasUnits()) {
            return false;
        }
        armyOne.update(armyTwo, deltaTime);
        if (!armyOne.hasUnits()) {
            return false;
        }
        armyTwo.update(armyOne, deltaTime);
        return true;
    }

    /**
     * Method for setting a current reset point, if
     */
    public void setResetPoint() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(RESET_FILE))) {
            out.writeObject(this);
        } catch (IOException i) {
            throw new UncheckedIOException(i);
        }
    }

    /**
     * Method for resting from reset point.
     */
    public void reset() {
        File tempFile = new File(RESET_FILE);
        if (!tempFile.exists()) {
            throw new IllegalStateException("Reset point must be set first.");
        }

        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(tempFile));) {
            Battle resetBattle = (Battle) in.readObject();
            armyOne = resetBattle.getArmyOne();
            armyTwo = resetBattle.getArmyTwo();

        } catch (IOException i) {
            throw new UncheckedIOException(i);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        if (!tempFile.delete()) {
            throw new UncheckedIOException(new IOException("Save file was not deleted"));
        }
    }

    public void setArmyOne(Army armyOne) {
        this.armyOne = armyOne;
    }

    public void setArmyTwo(Army armyTwo) {
        this.armyTwo = armyTwo;
    }

    /**
     * Method for getting all units from both armies
     * 
     * @return all units
     */
    public List<Unit> getAllUnits() {
        return Stream.concat(armyOne.getAllUnits().stream(), armyTwo.getAllUnits().stream()).toList();

    }

    public Army getArmyOne() {
        return armyOne;
    }

    public Army getArmyTwo() {
        return armyTwo;
    }

    public TileMap getTileMap() {
        return tileMap;
    }

    @Override
    public String toString() {
        return "Battle{" + "armyOne=" + armyOne + ", armyTwo=" + armyTwo + '}';
    }
}
