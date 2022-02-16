package edu.ntnu.thosve.units;

/**
 * Abstract class for a given war Unit.
 */
public abstract class Unit {
    private final String name;
    private int health;
    private int attack;
    private int armor;

    public Unit(String name, int health, int attack, int armor) {
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
        int opponentsNewHealth = opponent.getHealth() - attackDamage + resistance;
        opponent.setHealth(opponentsNewHealth);
    }



    public String getName() {
        return name;
    }

    public int getHealth() {
        return health;
    }

    public int getAttack() {
        return attack;
    }

    public int getArmor() {
        return armor;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    @Override
    public String toString() {
        return "Unit: " + name +
                "\n\t- HP" + health +
                "\n\t- Attack: " + attack +
                "\n\t- Armor: " + armor;
    }

    public abstract int getAttackBonus();
    public abstract int getResistBonus();
}

