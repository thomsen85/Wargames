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

    @Override
    public String toString() {
        return "Battle{" +
                "armyOne=" + armyOne +
                ", armyTwo=" + armyTwo +
                '}';
    }
}
