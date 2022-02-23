package edu.ntnu.thosve;

import edu.ntnu.thosve.units.Unit;

public class Battle {
    private Army armyOne;
    private Army armyTwo;

    public Battle(Army armyOne, Army armyTwo) {
        this.armyOne = armyOne;
        this.armyTwo = armyTwo;
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

    public boolean manualSimulate(double deltaTime) {
        armyOne.update(armyTwo, deltaTime);
        if (!armyOne.hasUnits()) {
            return false;
        }
        armyTwo.update(armyOne, deltaTime);
        return armyOne.hasUnits() && armyTwo.hasUnits();
    }

    @Override
    public String toString() {
        return "Battle{" +
                "armyOne=" + armyOne +
                ", armyTwo=" + armyTwo +
                '}';
    }
}
