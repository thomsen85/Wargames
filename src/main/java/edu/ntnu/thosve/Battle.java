package edu.ntnu.thosve;

import edu.ntnu.thosve.map.TileMap;

/**
 * Class for managing Two armies.
 */
public class Battle {
    private final Army armyOne;
    private final Army armyTwo;

    /**
     * Constructor for creating an instance of the Battle.
     * 
     * @param armyOne
     * @param armyTwo
     */
    public Battle(Army armyOne, Army armyTwo, TileMap tileMap) {
        this.armyOne = armyOne;
        this.armyTwo = armyTwo;

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

    public boolean simulateStep(double deltaTime) {
        armyOne.update(armyTwo, deltaTime);
        if (!armyOne.hasUnits()) {
            return false;
        }
        armyTwo.update(armyOne, deltaTime);
        return armyOne.hasUnits() && armyTwo.hasUnits();
    }

    @Override
    public String toString() {
        return "Battle{" + "armyOne=" + armyOne + ", armyTwo=" + armyTwo + '}';
    }
}
