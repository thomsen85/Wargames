package edu.ntnu.thosve.units;

/**
 * Abstract class for a given war Unit.
 */
public abstract class Unit {
    private final String name;
    private int health;
    private int attack;
    private int armor;

    public Unit(String name, int health, int attack, int armor) throws IllegalArgumentException{
        if (name.isBlank()) {
           throw new IllegalArgumentException("Name can not be blank");
        }
        if (health <= 0 ) {
            throw new IllegalArgumentException("Health can not be less then or equal to zero");
        }
        if (attack < 0) {
            throw new IllegalArgumentException("Attack can not be negative");
        }
        if (armor < 0) {
            throw new IllegalArgumentException("Armor can not be negative");
        }

        this.name = name;
        this.health = health;
        this.attack = attack;
        this.armor = armor;
    }

    /**
     * Method for attacking another given Unit. It is calculated in the following way:
     * health_opponent - (attack + attackBonus)_this + (armor + resistBonus)_opponent
     * @param opponent which is being attacked.
     */
    public void attack(Unit opponent) {
        int attackDamage = this.getAttack() + this.getAttackBonus();
        int resistance = opponent.getArmor() + opponent.getResistBonus();

        if (resistance < attackDamage) {
            int opponentsNewHealth = opponent.getHealth() - attackDamage + resistance;
            opponent.setHealth(opponentsNewHealth);
        }
    }

    /**
     * Gets the name of the Unit.
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Gives the <b>current</b> health of the Unit.
     * @return health
     */
    public int getHealth() {
        return health;
    }

    /**
     * Gets the attack attribute of the Unit.
     * @return attack
     */
    public int getAttack() {
        return attack;
    }

    /**
     * Get the armor attribute of the Unit.
     * @return armor
     */
    public int getArmor() {
        return armor;
    }

    /**
     * Sets the health of the Unit.
     * @param health
     */
    public void setHealth(int health) {
        this.health = health;
    }

    /**
     * Gets a String representation of the Unit.
     * @return a string
     */
    @Override
    public String toString() {
        return "Unit: " + name +
                "\n\t- HP" + health +
                "\n\t- Attack: " + attack +
                "\n\t- Armor: " + armor;
    }

    /**
     * Gets the <b>current</b> attack bonus of the Unit
     * @return AttackBouns
     */
    public abstract int getAttackBonus();

    /**
     * Gets the <b>current</b> resist bonus of the Unit
     * @return ResistBonus
     */
    public abstract int getResistBonus();
}

