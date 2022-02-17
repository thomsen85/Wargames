package edu.ntnu.thosve.units;

/**
 * Cavalry Units are slightly stronger units with a charge attack on their first attack, it will deal 4+2 bonus damage,
 * and after that they will deal 2 bonus damage.
 *
 * This unit has a tiny advantage with +1 resist bonus on when getting attacked
 */
public class CavalryUnit extends Unit{
    public static final int BASE_ATTACK_BONUS = 2;
    public static final int CHARGE_ATTACK_BONUS = 6;
    public static final int RESIST_BONUS = 1;

    public static final int MAX_SPEED = 10;
    public static final int LOOK_RADIUS = 1;
    public static final int ATTACK_RADIUS = 1;

    private boolean charge = true;

    /**
     * Constructor for creating an instance of the CavalryUnit.
     * @param name
     * @param health
     * @param attack
     * @param armor
     */
    public CavalryUnit(String name, int health, int attack, int armor) {
        super(name, health, attack, armor);
    }

    /**
     * A simpler version of the constructor for creating an istance of the Cavalry unit.
     * Note: Attack will be default to 20 and armor will be default to 12. Use the full constructor to change this
     * @param name
     * @param health
     */
    public CavalryUnit(String name, int health) {
        super(name, health, 20, 12);
    }

    /**
     * Gets the attackBouns of the Unit. The Unit will have a first strike which will deal an extra 4 damage.
     * After it has attacked it will go back to dealing 2 bonus damage.
     * @return
     */
    @Override
    public int getAttackBonus() {
        if (charge) {
            return CHARGE_ATTACK_BONUS;
        } else {
            return BASE_ATTACK_BONUS;
        }
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
    public int getLookRadius() {
        return LOOK_RADIUS;
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
