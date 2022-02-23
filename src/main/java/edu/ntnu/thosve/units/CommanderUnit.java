package edu.ntnu.thosve.units;

/**
 * A CommanderUnit is one of the strongest units. It shares a lot of attributes with the Cavalry unit.
 */
public class CommanderUnit extends CavalryUnit{
    public static final int DEFAULT_ATTACK = 30;
    public static final int DEFAULT_ARMOR = 40;
    /**
     * Constructor for creating an instance of the CommanderUnit class.
     * @param name
     * @param health
     * @param attack
     * @param armor
     */
    public CommanderUnit(String name, int health, int attack, int armor) {
        super(name, health, attack, armor);
    }

    /**
     * A simpler constructor for creating an instance of the CommanderUnit class
     * Note: The attack with be defaulted to 25 and the armor wil be default to 15. Use the full constructor to change this.
     * @param name
     * @param health
     */
    public CommanderUnit(String name, int health) {
        super(name, health, DEFAULT_ATTACK, DEFAULT_ARMOR);
    }
}
