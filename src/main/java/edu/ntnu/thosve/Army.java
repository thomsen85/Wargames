package edu.ntnu.thosve;

import edu.ntnu.thosve.units.Unit;

import java.util.*;

/**
 * Class for holding an Army of different types of Units.
 */
public class Army {
    private final String name;
    private List<Unit> units;

    /**
     * Constructor which creates an instance of the Army class
     * @param name of the army
     */
    public Army(String name) {
        this.name = name;
        units = new ArrayList<Unit>();
    }

    /**
     * Constructor which creates an instance of the Army class, with prefilled list of units.
     * @param name of the army.
     * @param units filled list of units.
     */
    public Army(String name, List<Unit> units) {
        this.name = name;
        this.units = units;
    }

    public String getName() {
        return name;
    }

    public boolean add(Unit unit) {
        return this.units.add(unit);
    }

    public boolean addAll(List<Unit> units) {
        return this.units.addAll(units);
    }

    /**
     * Removes the specified element from this army if it is present.
     * @param unit to be removed from army
     * @return true if the army contained the specified unit
     */
    public boolean remove(Unit unit) {
        return this.units.remove(unit);
    }

    /**
     * Checks if the army contains Units
     * @return true if army contains 1 or more Units.
     */
    public boolean hasUnits() {
        return !units.isEmpty();
    }

    /**
     * Method for getting all units.
     * @return
     */
    public List<Unit> getAllUnits() {
        return units;
    }

    /**
     * For my use this is useless
     * @return
     */
    public Unit getRandom() {
        if (!hasUnits()) {
            throw new NoSuchElementException("Army has no Units");
        }
        Random rand = new Random();
        int index = rand.nextInt(units.size());

        return units.get(index);

    }

    /**
     * Method for spreading units evenly across a given square.
     * @param xBottomLeft X coordinate of bottom left of square
     * @param yBottomLeft Y coordinate of bottom left of square
     * @param xTopRight X coordinate of top right of square
     * @param yTopRight Y coordinate of top right of square
     */
    public void spreadUnitsEvenly(int xBottomLeft, int yBottomLeft, int xTopRight, int yTopRight) {
        int columns = 1;
        int rows = 1;

        int height = yTopRight - yBottomLeft;
        int width = xTopRight - xBottomLeft;


        while (columns * rows < units.size() && columns * rows < units.size() ) {
            if (width / (columns + 1) > height / (rows + 1)) {
                columns += 1;
            } else {
                rows += 1;
            }
        }

        for(int row = 0; row < rows; row++ ) {
            for(int column = 0; column < columns; column++) {
                double x = column * ( (double) width / (columns-1)) + xBottomLeft;
                double y =  row * ( (double) height / (rows-1)) + yBottomLeft;
                int index = column + (row*columns);
                if (index < units.size()) {
                    units.get(index).setPos(x, y);
                }
            }
        }
    }

    /**
     * Method for updating all the units in the army. If a unit has health <= 0 it will get removed.
     * If not it will attack if close enough or move closer to opponent.
     * @param opponent to attack
     */
    public void update(Army opponent) {
        List<Unit> dead = new ArrayList<Unit>();
        for(Unit unit: units) {
            if (unit.getHealth() <= 0) {
                dead.add(unit); // To avoid ConcurrentModificationException
            } else {
                unit.targetClosestOpponent(opponent.getAllUnits());
                unit.update();
            }
        }
        units.removeAll(dead);
    }

    /**
     * Method for updating all the units in the army given delta time. If a unit has health <= 0 it will get removed.
     * If not it will attack if close enough or move closer to opponent.
     * @param opponent to attack
     * @param deltaTime is time between frames.
     */
    public void update(Army opponent, double deltaTime) {
        List<Unit> dead = new ArrayList<Unit>();
        for(Unit unit: units) {
            if (unit.getHealth() <= 0) {
                dead.add(unit); // To avoid ConcurrentModificationException
            } else {
                unit.targetClosestOpponent(opponent.getAllUnits());
                unit.update(deltaTime);
            }
        }
        units.removeAll(dead);
    }

    @Override
    public String toString() {
        return "Army: " + name +
                "\n\t-Units left:" + units.size();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Army army = (Army) o;

        if (!Objects.equals(name, army.name)) return false;
        return Objects.equals(units, army.units);
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (units != null ? units.hashCode() : 0);
        return result;
    }
}
