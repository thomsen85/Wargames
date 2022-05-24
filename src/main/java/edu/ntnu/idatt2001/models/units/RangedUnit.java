package edu.ntnu.idatt2001.models.units;

import edu.ntnu.idatt2001.models.map.Terrain;

/**
 * Unit that has the ability to attack at range.
 */
public class RangedUnit extends Unit {
    public static final int DEFAULT_ATTACK = 15;
    public static final int DEFAULT_ARMOR = 5;
    public static final int DEFAULT_HEALTH = 60;

    public static final int ATTACK_BONUS = 2;
    public static final int RESIST_BONUS = 1;

    public static final int MAX_SPEED = 10;
    public static final int ATTACK_RADIUS = 100;

    public static final double HILL_ATTACK_RADIUS_MULTIPLIER = 1.5;
    public static final double FOREST_ATTACK_RADIUS_MULTIPLIER = 0.5;

    /**
     * Constructor for creating an instance of the RangedUnit class.
     * 
     * @param name
     *            of unit.
     *
     * @param health
     *            of unit
     * @param attack
     *            of unit
     * @param armor
     *            of unit
     */
    public RangedUnit(String name, double health, int attack, int armor) {
        super(name, health, attack, armor);
    }

    /**
     * A simpler constructor for creating an instance of the RangedUnit class Note: The attack and armor with be set to
     * its default values. Use the full constructor to change this.
     * 
     * @param name
     * @param health
     */
    public RangedUnit(String name, double health) {
        super(name, health, DEFAULT_ATTACK, DEFAULT_ARMOR);
    }

    /**
     * The simplest constructor for creating an instance of the RangedUnit class Note: The health, attack and armor with
     * be set to its default values. Use the full constructor to change this.
     * 
     * @param name
     */
    public RangedUnit(String name) {
        super(name, DEFAULT_HEALTH, DEFAULT_ATTACK, DEFAULT_ARMOR);
    }

    @Override
    public int getAttackBonus() {
        return ATTACK_BONUS;
    }

    /**
     * Gets the resist bonus.
     * 
     * @return
     */
    @Override
    public int getResistBonus() {
        return RESIST_BONUS;
    }

    @Override
    public int getMaxSpeed() {
        return MAX_SPEED;
    }

    /**
     * Returns the attack radius of the unit, if the current terrain of the unit is forest it will get worse range, and
     * if the terrain is hill it will get better, dictated by HILL_ATTACK_RADIUS_MULTIPLIER,
     * FOREST_ATTACK_RADIUS_MULTIPLIER.
     * 
     * @return the attack radius
     */
    @Override
    public int getAttackRadius() {
        double multiplier = 1;
        if (getCurrentTerrain() == Terrain.FOREST) {
            multiplier = FOREST_ATTACK_RADIUS_MULTIPLIER;
        } else if (getCurrentTerrain() == Terrain.HILL) {
            multiplier = HILL_ATTACK_RADIUS_MULTIPLIER;
        }
        return (int) (ATTACK_RADIUS * multiplier);
    }
}
