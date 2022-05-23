package edu.ntnu.thosve.models.units;

import com.github.javafaker.Faker;

import java.util.ArrayList;
import java.util.List;

public class UnitFactory {

    /**
     * Method for getting a unit by its type. If unit type is unknown it will create an InfantryUnit.
     * 
     * @param unitType
     *            to be created
     * @param health
     *            of the unit to be created
     * @return the unit that has been created
     */
    public static Unit getUnit(UnitType unitType, double health) {
        Unit unit;
        Faker faker = new Faker();
        String name = faker.name().fullName();
        switch (unitType) {
        case COMMANDER_UNIT -> unit = new CommanderUnit(name, health);
        case RANGED_UNIT -> unit = new RangedUnit(name, health);
        case CAVALRY_UNIT -> unit = new CavalryUnit(name, health);
        default -> unit = new InfantryUnit(name, health);
        }
        return unit;
    }

    public static Unit getUnit(UnitType unitType) {
        Unit unit;
        Faker faker = new Faker();
        String name = faker.name().fullName();
        switch (unitType) {
        case COMMANDER_UNIT -> unit = new CommanderUnit(name);
        case RANGED_UNIT -> unit = new RangedUnit(name);
        case CAVALRY_UNIT -> unit = new CavalryUnit(name);
        default -> unit = new InfantryUnit(name);
        }
        return unit;
    }

    /**
     * Method for getting a list of units given amount and type. If unit type is unknown it will create a list of
     * InfantryUnits.
     * 
     * @param unitType
     *            to be created
     * @param amount
     *            of units to be created
     * @param health
     *            of the units
     * @return the units
     */
    public static List<Unit> getUnits(UnitType unitType, int amount, double health) {
        List<Unit> units = new ArrayList<>();
        for (int i = 0; i < amount; i++) {
            units.add(getUnit(unitType, health));
        }
        return units;
    }

    public static List<Unit> getUnits(UnitType unitType, int amount) {
        List<Unit> units = new ArrayList<>();
        for (int i = 0; i < amount; i++) {
            units.add(getUnit(unitType));
        }
        return units;
    }

}
