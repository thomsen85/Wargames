package edu.ntnu.idatt2001.models.units;

public enum UnitType {
    CAVALRY_UNIT("Cavalry Unit"), COMMANDER_UNIT("Commander Unit"), INFANTRY_UNIT("Infantry Unit"), RANGED_UNIT(
            "Ranged Unit");

    private final String name;

    UnitType(String name) {
        this.name = name;
    }

    public static UnitType getTypeOfUnit(Unit unit) {
        UnitType type;
        switch (unit.getClass().getSimpleName()) {
        case "CavalryUnit" -> type = CAVALRY_UNIT;
        case "CommanderUnit" -> type = COMMANDER_UNIT;
        case "RangedUnit" -> type = RANGED_UNIT;
        case "InfantryUnit" -> type = INFANTRY_UNIT;
        default -> type = null;
        }
        return type;
    }

    @Override
    public String toString() {
        return name;
    }
}
