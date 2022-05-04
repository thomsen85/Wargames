package edu.ntnu.thosve.models.units;

public enum UnitType {
    CAVALRY_UNIT("Cavalry Unit"), COMMANDER_UNIT("Commander Unit"), INFANTRY_UNIT("Infantry Unit"), RANGED_UNIT(
            "Ranged Unit");

    private String name;

    UnitType(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
