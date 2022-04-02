package edu.ntnu.thosve.units;

/**
 * Unit that has the ability to attack at range.
 */
public class RangedUnit extends Unit{
    public static final int DEFAULT_ATTACK = 15;
    public static final int DEFAULT_ARMOR = 5;
    public static final int DEFAULT_HEALTH = 50;

    private static final int ATTACK_BONUS = 2;
    private static final int RESIST_BONUS = 1;

    private static final int MAX_SPEED = 10;
    private static final int ATTACK_RADIUS = 100;

    /**
     * Constructor for creating an instance of the RangedUnit class.
     * @param name
     * @param health
     * @param attack
     * @param armor
     */
    public RangedUnit(String name, int health, int attack, int armor) {
        super(name, health, attack, armor);
    }

    /**
     * A simpler constructor for creating an instance of the RangedUnit class
     * Note: The attack and armor with be set to its default values. Use the full constructor to change this.
     * @param name
     * @param health
     */
    public RangedUnit(String name, int health) {
        super(name, health, DEFAULT_ATTACK, DEFAULT_ARMOR);
    }

    /**
     * The simplest constructor for creating an instance of the RangedUnit class
     * Note: The health, attack and armor with be set to its default values. Use the full constructor to change this.
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

    @Override
    public int getAttackRadius() {
        return ATTACK_RADIUS;
    }
}
