package edu.ntnu.thosve.units;

/**
 * Cavalry Units are slightly stronger units with a charge attack on their first attack, it will deal 4+2 bonus damage,
 * and after that they will deal 2 bonus damage.
 *
 * This unit has a tiny advantage with +1 resist bonus on when getting attacked
 */
public class CavalryUnit extends Unit{
    boolean charge = true;

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
            return 6;
        } else {
            return 2;
        }
    }

    @Override
    public int getResistBonus() {
        return 1;
    }

    @Override
    public void attack(Unit opponent) {
        super.attack(opponent);
        charge = false;
    }
}
