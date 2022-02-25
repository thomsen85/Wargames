package edu.ntnu.thosve.units;

public enum UnitTypes {
    CAVALRY_UNIT("Cavalry Unit"),
    COMMANDER_UNIT("Commander Unit"),
    INFANTRY_UNIT("Infantry Unit"),
    RANGED_UNIT("Ranged Unit");

    private String name;

    UnitTypes(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
