package edu.ntnu.thosve.units;

/**
 * Close ranged unit with no special abilities.
 */
public class InfantryUnit extends Unit {
    public static final int DEFAULT_ATTACK = 15;
    public static final int DEFAULT_ARMOR = 10;
    public static final int DEFAULT_HEALTH = 100;

    public static final int ATTACK_BONUS = 2;
    public static final int RESIST_BONUS = 5;

    public static final int MAX_SPEED = 25;
    public static final int ATTACK_RADIUS = 10;



    /**
     * Constructor for creating an instance of the InfantryUnit class.
     * @param name
     * @param health
     * @param attack
     * @param armor
     */
    public InfantryUnit(String name, int health, int attack, int armor) {
        super(name, health, attack, armor);
    }

    /**
     * A simpler constructor for creating an instance of the InfantryUnit class
     * Note: The attack and armor with be set to its default values. Use the full constructor to change this.
     * @param name
     * @param health
     */
    public InfantryUnit(String name, int health) {
        super(name, health, DEFAULT_ATTACK, DEFAULT_ARMOR);
    }

    /**
     * The simplest constructor for creating an instance of the InfantryUnit class
     * Note: The health, attack and armor with be set to its default values. Use the full constructor to change this.
     * @param name
     */
    public InfantryUnit(String name) {
        super(name, DEFAULT_HEALTH, DEFAULT_ATTACK, DEFAULT_ARMOR);
    }

    @Override
    public int getAttackBonus() {
        return ATTACK_BONUS;
    }
    @Override
    public int getResistBonus() {
        return RESIST_BONUS;
    }
    @Override
    public int getMaxSpeed() {
        return MAX_SPEED;
    }

    @Override
    public int getAttackRadius() {
        return ATTACK_RADIUS;
    }
}
