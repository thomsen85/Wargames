package edu.ntnu.thosve.models.units;

import edu.ntnu.thosve.map.Terrain;

/**
 * Cavalry Units are slightly stronger units with a charge attack on their first attack, it will deal 4+2 bonus damage,
 * and after that they will deal 2 bonus damage.
 *
 * This unit has a tiny advantage with +1 resist bonus on when getting attacked
 */
public class CavalryUnit extends Unit {
    public static final int DEFAULT_ATTACK = 20;
    public static final int DEFAULT_ARMOR = 20;
    public static final int DEFAULT_HEALTH = 150;

    public static final int BASE_ATTACK_BONUS = 5;
    public static final int RESIST_BONUS = 5;
    public static final int CHARGE_ATTACK_BONUS = 10;

    public static final int MAX_SPEED = 20;
    public static final double PLAINS_SPEED_MULTIPLIER = 2;
    public static final int ATTACK_RADIUS = 10;
    public static final double CHARGE_SPEED_MULTIPLIER = 1.5;
    public static final double PLAINS_ATTACK_BONUS_MULTIPLIER = 1.5;

    private boolean charge = true;

    /**
     * Constructor for creating an instance of the CavalryUnit.
     * 
     * @param name of unit
     * @param health of unit
     * @param attack of unit
     * @param armor of unit
     */
    public CavalryUnit(String name, double health, int attack, int armor) {
        super(name, health, attack, armor);
    }

    /**
     * A simpler constructor for creating an instance of the CavalryUnit class Note: The attack and armor with be set to
     * its default values. Use the full constructor to change this.
     * 
     * @param name
     * @param health
     */
    public CavalryUnit(String name, double health) {
        super(name, health, DEFAULT_ATTACK, DEFAULT_ARMOR);
    }

    /**
     * The simplest constructor for creating an instance of the CavalryUnit class Note: The health, attack and armor
     * with be set to its default values. Use the full constructor to change this.
     * 
     * @param name
     */
    public CavalryUnit(String name) {
        super(name, DEFAULT_HEALTH, DEFAULT_ATTACK, DEFAULT_ARMOR);
    }

    /**
     * Gets the attackBonus of the Unit. The Unit will have a first strike which will deal an extra 4 damage. After it
     * has attacked it will go back to dealing 2 bonus damage.
     *
     * If the cavalry unit is in a "plains" terrain it will get an attack bonus multiplier defined with the
     * PLAINS_ATTACK_BONUS_MULTIPLIER constant.
     * 
     * @return attack bonus
     */
    @Override
    public int getAttackBonus() {
        double terrainBonus = getCurrentTerrain() == Terrain.PLAINS ? PLAINS_ATTACK_BONUS_MULTIPLIER : 1;

        if (charge) {
            return (int) (CHARGE_ATTACK_BONUS * terrainBonus);
        } else {
            return (int) (BASE_ATTACK_BONUS * terrainBonus);
        }
    }

    /**
     * Method for returning the resist bonus of the unit. If cavalry unit is in the <b>Forest</b> terrain, it wil have 0
     * in resist bonus.
     * @return the resist bonus
     */
    @Override
    public int getResistBonus() {
        if (getCurrentTerrain() == Terrain.FOREST) {
            return 0;
        }
        return RESIST_BONUS;
    }

    /**
     * Method for getting the max speed for the unit, if the unit is in charge its speed will be multiplied with CHARGE_SPEED_MULTIPLIER
     * @return max speed.
     */
    @Override
    public int getMaxSpeed() {
        double terrainBonus = getCurrentTerrain() == Terrain.PLAINS ? PLAINS_SPEED_MULTIPLIER : 1;
        if (charge) {
            return (int) (MAX_SPEED * CHARGE_SPEED_MULTIPLIER * terrainBonus);
        }
        return (int) (MAX_SPEED * terrainBonus);
    }

    @Override
    public int getAttackRadius() {
        return ATTACK_RADIUS;
    }

    @Override
    public void attack(Unit opponent) {
        super.attack(opponent);
        charge = false;
    }
}
