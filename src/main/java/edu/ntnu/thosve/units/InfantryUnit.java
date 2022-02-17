package edu.ntnu.thosve.units;

/**
 * Basic unit with no special abilities, good at meele
 */
public class InfantryUnit extends Unit {

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
     * Note: The attack with be defaulted to 25 and the armor wil be default to 15. Use the full constructor to change this.
     * @param name
     * @param health
     */
    public InfantryUnit(String name, int health) {
        // Spørsmål: Er det bedre og lage egne int attack og int armor også skrive det inn i super?
        super(name, health, 15, 10);
    }

    @Override
    public int getAttackBonus() {
        return 2;
    }
    @Override
    public int getResistBonus() {
        return 1;
    }
}
