package edu.ntnu.thosve.units;

import java.util.ArrayList;
import java.util.List;

public class UnitFactory {

    /**
     * Method for getting a unit by its type. If unit type is unknown it will create an InfantryUnit.
     * @param unitType to be created
     * @param name of the unit to be created
     * @param health of the unit to be created
     * @return the unit that has been created
     */
    public static Unit getUnit(UnitType unitType, String name, double health) {
        Unit unit;
        switch (unitType) {
            case COMMANDER_UNIT -> unit = new CommanderUnit(name, health);
            case RANGED_UNIT -> unit = new RangedUnit(name, health);
            case CAVALRY_UNIT -> unit = new CavalryUnit(name, health);
            default -> unit = new InfantryUnit(name, health);
        }
        return unit;
    }

    /**
     * Method for getting a list of units given amount and type. If unit type is unknown it will create a list of InfantryUnits.
     * @param unitType to be created
     * @param amount of units to be created
     * @param name of the units
     * @param health of the units
     * @return the units
     */
    public static List<Unit> getUnits(UnitType unitType, int amount, String name, double health) {
        List<Unit> units = new ArrayList<>();
        for (int i = 0; i < amount; i++) {
            units.add(getUnit(unitType, name, health));
        }
        return units;
    }



}
