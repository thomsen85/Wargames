package edu.ntnu.idatt2001.models;

import edu.ntnu.idatt2001.models.formation.Formation;
import edu.ntnu.idatt2001.models.map.Terrain;
import edu.ntnu.idatt2001.models.map.TileMap;
import edu.ntnu.idatt2001.models.units.*;

import java.io.*;
import java.util.*;

/**
 * Class for holding an Army of different types of Units.
 */
public class Army implements Serializable {
    private final String name;
    private final List<Unit> units;

    private TileMap tileMap;

    /**
     * Constructor which creates an instance of the Army class
     * 
     * @param name
     *            of the army
     * @param tileMap
     *            that the army is on
     *
     */
    public Army(String name, TileMap tileMap) {
        Objects.requireNonNull(name, "Name cannot be null");
        Objects.requireNonNull(tileMap, "Tile Map cannot be null");
        if (name.isBlank()) {
            throw new IllegalArgumentException("Name cannot be blank");
        }

        this.name = name;
        this.tileMap = tileMap;
        units = new ArrayList<>();
    }

    /**
     * Constructor which creates an instance of the Army class, with prefilled list of units.
     * 
     * @param name
     *            of the army. Can not be blank.
     * @param tileMap
     *            that the army is on.
     * @param units
     *            filled list of units.
     */
    public Army(String name, TileMap tileMap, List<Unit> units) {
        Objects.requireNonNull(name, "Name cannot be null");
        Objects.requireNonNull(tileMap, "TileMap cannot be null");
        Objects.requireNonNull(units, "Units cannot be null");
        if (name.isBlank()) {
            throw new IllegalArgumentException("Name cannot be blank");
        }

        this.name = name;
        this.tileMap = tileMap;
        this.units = units;
    }

    /**
     * Lightweight constructor method for creation of army. tileMap updated later.
     * 
     * @param name
     *            of army, can not be blank
     */
    public Army(String name) {
        Objects.requireNonNull(name, "Name cannot be null");
        if (name.isBlank()) {
            throw new IllegalArgumentException("Name cannot be blank");
        }

        this.name = name;
        this.units = new ArrayList<>();
    }

    /**
     * Gets the name of the army
     * 
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Adds a single unit to the army
     * 
     * @param unit
     *            to be added
     * @return true if this collection changed as a result of the call
     */
    public boolean add(Unit unit) {
        return this.units.add(unit);
    }

    /**
     * Adds multiple units to the army
     * 
     * @param units
     *            to be added
     * @return true if this collection changed as a result of the call
     */
    public boolean addAll(List<Unit> units) {
        return this.units.addAll(units);
    }

    /**
     * Removes the specified element from this army if it is present.
     * 
     * @param unit
     *            to be removed from army
     * @return true if the army contained the specified unit
     */
    public boolean remove(Unit unit) {
        return this.units.remove(unit);
    }

    /**
     * Removes the specified elements from this army if it is present.
     * 
     * @param units
     *            to be removed.
     * @return true if this list changed as a result of the call
     */
    public boolean removeAll(List<Unit> units) {
        return this.units.removeAll(units);
    }

    /**
     * Checks if the army contains Units
     * 
     * @return true if army contains 1 or more Units.
     */
    public boolean hasUnits() {
        return !units.isEmpty();
    }

    /**
     * Method for getting all units.
     * 
     * @return all units
     */
    public List<Unit> getAllUnits() {
        return units;
    }

    /**
     * For my use this is useless. Only here because of the requirements.
     * 
     * @return a random unit
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
     * Applies a formation to the units.
     * 
     * @param formation
     *            to apply
     */
    public void applyFormation(Formation formation) {
        formation.spreadUnits(units);
    }

    /**
     * Method for updating all the units in the army. If a unit has health <= 0 it will get removed. If not it will
     * attack if close enough or move closer to opponent.
     * 
     * @param opponent
     *            to attack
     */
    public void update(Army opponent) {
        List<Unit> dead = new ArrayList<>();
        for (Unit unit : units) {
            if (unit.getHealth() <= 0) {
                dead.add(unit); // To avoid ConcurrentModificationException
            } else {
                unitTargetClosestOpponent(unit, opponent.getAllUnits());
                updateUnit(unit);
            }
        }
        units.removeAll(dead);
    }

    /**
     * Method for updating all the units in the army given delta time. If a unit has health <= 0 it will get removed. If
     * not it will attack if close enough or move closer to opponent.
     * 
     * @param opponent
     *            to attack
     * @param deltaTime
     *            is time between frames.
     */
    public void update(Army opponent, double deltaTime) {
        List<Unit> dead = new ArrayList<>();
        for (Unit unit : units) {
            if (unit.getHealth() <= 0) {
                dead.add(unit); // To avoid ConcurrentModificationException
            } else {
                unitTargetClosestOpponent(unit, opponent.getAllUnits());
                updateUnit(unit, deltaTime);
            }
        }
        units.removeAll(dead);
    }

    /**
     * Method for updating a unit. If an opponent is within attacking range it will attack, if not it will update the
     * position of the unit based on the current speed.
     *
     * @param unit
     *            to update
     * @param deltaTime
     *            to multiply in
     */
    private void updateUnit(Unit unit, double deltaTime) {
        unit.setCurrentTerrain(getTerrainFromUnit(unit));

        if (unit.getCurrentOpponent() != null) {
            if (getDistanceBetweenUnits(unit, unit.getCurrentOpponent()) <= unit.getAttackRadius()) {
                unit.attack(unit.getCurrentOpponent(), deltaTime);
            } else {
                unit.setX(unit.getX() + unit.getXSpeed() * deltaTime);
                unit.setY(unit.getY() + unit.getYSpeed() * deltaTime);
            }
        }
    }

    /**
     * Method for updating a unit. If an opponent is within attacking range it will attack, if not it will update the
     * position of the unit based on the current speed.
     *
     * @param unit
     *            to update
     */
    private void updateUnit(Unit unit) {
        unit.setCurrentTerrain(getTerrainFromUnit(unit));

        if (unit.getCurrentOpponent() != null) {
            if (getDistanceBetweenUnits(unit, unit.getCurrentOpponent()) <= unit.getAttackRadius()) {
                unit.attack(unit.getCurrentOpponent());
            } else {
                unit.setX(unit.getX() + unit.getXSpeed());
                unit.setY(unit.getY() + unit.getYSpeed());
            }
        }
    }

    /**
     * Method that will make a unit target the closest opponent. This will change it movement direction to move to the
     * closest opponent
     * 
     * @param unit
     *            to set target for.
     * @param opponents
     *            list of all the opponents.
     */
    private void unitTargetClosestOpponent(Unit unit, List<Unit> opponents) {
        Unit closestOpponent = findClosestOpponentToUnit(unit, opponents);
        unitMoveToOpponent(unit, closestOpponent);
        unit.setCurrentOpponent(closestOpponent);
    }

    /**
     * Finds the closes Unit by distance given a list of opponents
     * 
     * @param unit
     *            unit
     * @param opponents
     *            to find closest from
     * @return closest Unit
     */
    private Unit findClosestOpponentToUnit(Unit unit, List<Unit> opponents) {
        return opponents.stream().min(Comparator.comparingDouble(o -> getDistanceBetweenUnits(unit, o))).orElseThrow();
    }

    /**
     * Method for setting x and y speed to point to given unit.
     * 
     * @param unit
     *            unit to more
     * @param opponent
     *            to move to
     */
    private void unitMoveToOpponent(Unit unit, Unit opponent) {
        double xVec = opponent.getX() - unit.getX();
        double yVec = opponent.getY() - unit.getY();

        double length = Math.sqrt(Math.pow(xVec, 2) + Math.pow(yVec, 2));

        double xVecNormalized = xVec / length;
        double yVecNormalized = yVec / length;

        unit.setXSpeed(xVecNormalized * unit.getMaxSpeed());
        unit.setYSpeed(yVecNormalized * unit.getMaxSpeed());
    }

    /**
     * Method for getting the distance between two specified units
     * 
     * @param unit1
     *            unit one
     * @param unit2
     *            unit two
     * @return distance between units
     */
    public double getDistanceBetweenUnits(Unit unit1, Unit unit2) {
        return Math.sqrt(Math.pow(unit2.getX() - unit1.getX(), 2) + Math.pow(unit2.getY() - unit1.getY(), 2));
    }

    /**
     * If unit is outside of map, the terrain wil be set to FOREST by default.
     * 
     * @param unit
     *            to check
     * @return terrain that unit is standing on.
     */
    private Terrain getTerrainFromUnit(Unit unit) {
        Terrain terrainFromCoordinates;
        try {
            terrainFromCoordinates = tileMap.getTerrainFromCoordinates((int) unit.getX(), (int) unit.getY());
        } catch (IllegalArgumentException e) {
            terrainFromCoordinates = Terrain.FOREST;
        }
        return terrainFromCoordinates;
    }

    /**
     * Method for return all the infantry units in the army. Will return empty list if none is found
     * 
     * @return list of all infantry units
     */
    public List<Unit> getInfantryUnits() {
        return units.stream().filter(o -> o.getClass() == InfantryUnit.class).toList();
    }

    /**
     * Method for return all the cavalry units in the army. Will return empty list if none is found
     * 
     * @return list of all cavalry units
     */
    public List<Unit> getCavalryUnits() {
        return units.stream().filter(o -> o.getClass() == CavalryUnit.class).toList();
    }

    /**
     * Method for return all the ranged units in the army. Will return empty list if none is found
     * 
     * @return list of all ranged units
     */
    public List<Unit> getRangedUnits() {
        return units.stream().filter(o -> o.getClass() == RangedUnit.class).toList();
    }

    /**
     * Method for return all the commander units in the army. Will return empty list if none is found
     * 
     * @return list of all commander units
     */
    public List<Unit> getCommanderUnits() {
        return units.stream().filter(o -> o.getClass() == CommanderUnit.class).toList();
    }

    @Override
    public String toString() {
        return "Army: " + name + "\n\t-Units left:" + units.size();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        Army army = (Army) o;

        if (!Objects.equals(name, army.name))
            return false;
        return units.stream().anyMatch(unit -> army.getAllUnits().contains(unit));
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (units != null ? units.hashCode() : 0);
        return result;
    }

    /**
     * Method for writing the current instance to an CSV.
     * 
     * @param path
     *            to be written to, must end in .csv
     * @throws IOException
     *             if something wrong happens
     */
    public void writeCSV(String path) throws IOException {
        if (!path.endsWith(".csv")) {
            throw new IllegalArgumentException("Path must end with .csv");
        }

        FileWriter fileWriter = new FileWriter(path);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        PrintWriter printWriter = new PrintWriter(bufferedWriter);

        printWriter.println(getName());

        for (Unit unit : units) {
            StringBuilder str = new StringBuilder();

            str.append(unit.getClass().getSimpleName());
            str.append(",");
            str.append(unit.getName());
            str.append(",");
            str.append((int) unit.getHealth());

            printWriter.println(str.toString());
        }
        printWriter.flush();
        printWriter.close();
    }

    /**
     * Static method for getting an instance of an army from a csv file.
     * 
     * @param path
     *            to read from, must end in .csv
     * @return the loaded army
     */
    public static Army readCSV(String path, TileMap tileMap) throws IOException {
        if (!path.endsWith(".csv")) {
            throw new IllegalArgumentException("Path must end with .csv");
        }

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(path))) {
            Army army = new Army(bufferedReader.readLine(), tileMap);

            String unitData;
            while ((unitData = bufferedReader.readLine()) != null) {

                String[] data = unitData.split(",");

                if (data.length != 3) {
                    throw new IllegalArgumentException("Format of .csv is wrong.");
                }

                switch (data[0]) {
                case "InfantryUnit" -> army.add(new InfantryUnit(data[1], Integer.parseInt(data[2])));
                case "RangedUnit" -> army.add(new RangedUnit(data[1], Integer.parseInt(data[2])));
                case "CavalryUnit" -> army.add(new CavalryUnit(data[1], Integer.parseInt(data[2])));
                case "CommanderUnit" -> army.add(new CommanderUnit(data[1], Integer.parseInt(data[2])));
                }
            }
            return army;

        } catch (IOException e) {
            throw new IllegalArgumentException("Invalid String");
        } catch (NumberFormatException e) {
            throw new IOException("Format of csv is wrong.");
        }
    }

    public void setTileMap(TileMap tileMap) {
        this.tileMap = tileMap;
    }

    public void clearUnits() {
        units.clear();
    }
}
