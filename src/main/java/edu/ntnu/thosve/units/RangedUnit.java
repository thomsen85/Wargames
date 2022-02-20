package edu.ntnu.thosve.units;

/**
 * Unit that has the ability to attack at range.
 */
public class RangedUnit extends Unit{
    public static final int ATTACK_BONUS = 1;
    public static final int RESIST_BONUS = 2;

    public static final int MAX_SPEED = 10;
    public static final int ATTACK_RADIUS = 40;

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
     * A simpler constructor for creating an instance of the CommanderUnit class
     * Note: The attack with be defaulted to 25 and the armor wil be default to 15. Use the full constructor to change this.
     * @param name
     * @param health
     */
    public RangedUnit(String name, int health) {
        super(name, health, 15, 8 );
    }

    @Override
    public int getAttackBonus() {
        return 3;
    }

    /**
     * Gets the resist bonus. If the Ranged Unit has not been attacked it will have +6 in resist bonus but loses two
     * points for every damage it takes. It wil stop loosing resist bonus when it hits +2.
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
