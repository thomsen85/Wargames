package edu.ntnu.thosve.units;

public abstract class Unit {
    private final String name;
    private int health;
    private int attack;
    private int armor;

    public Unit(String name, int health, int attack, int armor) {
        this.name = name;
        this.health = health;
        this.attack = attack;
        this.armor = armor;
    }

    public void attack(Unit opponent) {
        int attack_damage = this.getAttack() + this.getAttackBonus();
        int resistance = opponent.getArmor() + opponent.getResistBonus();
        opponent.health -= attack_damage - resistance;
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

