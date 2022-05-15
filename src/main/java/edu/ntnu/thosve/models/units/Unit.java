package edu.ntnu.thosve.models.units;

import edu.ntnu.thosve.map.Terrain;

import java.io.Serializable;

/**
 * Abstract class for a Unit.
 */
public abstract class Unit implements Serializable {
    private final String name;
    private double health;
    private final int attack;
    private final int armor;

    private double x;
    private double y;
    private double xSpeed;
    private double ySpeed;

    private Unit currentOpponent;
    private Terrain currentTerrain;

    /**
     * Constructor
     * 
     * @param name
     *            of the unit
     * @param health
     *            of the unit
     * @param attack
     *            damage for the unit
     * @param armor
     *            amount for the unit.
     * @throws IllegalArgumentException
     *             if arguments are wrong.
     */
    public Unit(String name, double health, int attack, int armor) throws IllegalArgumentException {
        if (name.isBlank()) {
            throw new IllegalArgumentException("Name can not be blank");
        }
        if (health <= 0) {
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

        this.x = 0;
        this.y = 0;
        this.xSpeed = 0;
        this.ySpeed = 0;
    }

    /**
     * Method for attacking another given Unit. It is calculated in the following way: health_opponent - (attack +
     * attackBonus)_this + (armor + resistBonus)_opponent
     * 
     * @param opponent
     *            which is being attacked.
     */
    public void attack(Unit opponent) {
        int attackDamage = this.getAttack() + this.getAttackBonus();
        int resistance = opponent.getArmor() + opponent.getResistBonus();

        if (resistance < attackDamage) {
            double opponentsNewHealth = opponent.getHealth() - attackDamage + resistance;
            opponent.setHealth(opponentsNewHealth);
        }
    }

    /**
     * Method for attacking another given Unit. It is calculated in the following way: health_opponent - (attack +
     * attackBonus)_this + (armor + resistBonus)_opponent
     * 
     * @param opponent
     *            which is being attacked.
     */
    public void attack(Unit opponent, double deltaTime) {
        int attackDamage = this.getAttack() + this.getAttackBonus();
        int resistance = opponent.getArmor() + opponent.getResistBonus();

        if (resistance < attackDamage) {
            double opponentsNewHealth = opponent.getHealth() - (attackDamage + resistance) * deltaTime;
            opponent.setHealth(opponentsNewHealth);
        }
    }

    /**
     * Short form for setting the position of the Unit.
     * 
     * @param x
     *            coordinate
     * @param y
     *            coordinate
     */
    public void setPos(double x, double y) {
        setX(x);
        setY(y);
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getXSpeed() {
        return xSpeed;
    }

    public void setXSpeed(double xSpeed) {
        this.xSpeed = xSpeed;
    }

    public double getYSpeed() {
        return ySpeed;
    }

    public void setYSpeed(double ySpeed) {
        this.ySpeed = ySpeed;
    }

    /**
     * Gets the name of the Unit.
     * 
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Gives the <b>current</b> health of the Unit.
     * 
     * @return health
     */
    public double getHealth() {
        return health;
    }

    /**
     * Gets the attack attribute of the Unit.
     * 
     * @return attack
     */
    public int getAttack() {
        return attack;
    }

    /**
     * Get the armor attribute of the Unit.
     * 
     * @return armor
     */
    public int getArmor() {
        return armor;
    }

    /**
     * Sets the health of the Unit.
     * 
     * @param health
     */
    public void setHealth(double health) {
        this.health = health;
    }

    /**
     * Method for getting the current terrain of the player, given that the terrain is set.
     * 
     * @return the terrain
     */
    public Terrain getCurrentTerrain() {
        return currentTerrain;
    }

    /**
     * Method for setting the current terrain that the player is on, this is needed for bonus calculations.
     * 
     * @param currentTerrain
     *            to be set.
     */
    public void setCurrentTerrain(Terrain currentTerrain) {
        this.currentTerrain = currentTerrain;
    }

    public Unit getCurrentOpponent() {
        return currentOpponent;
    }

    public void setCurrentOpponent(Unit currentOpponent) {
        this.currentOpponent = currentOpponent;
    }

    /**
     * Gets a String representation of the Unit.
     * 
     * @return a string
     */
    @Override
    public String toString() {
        return "Unit: " + name + "\n\t- HP: " + health + "\n\t- Attack: " + attack + "\n\t- Armor: " + armor
                + "\n\t- Pos: (" + x + ", " + y + ")";
    }

    /**
     * Gets the <b>current</b> attack bonus of the Unit
     * 
     * @return AttackBonus
     */
    public abstract int getAttackBonus();

    /**
     * Gets the <b>current</b> resist bonus of the Unit
     * 
     * @return ResistBonus
     */
    public abstract int getResistBonus();

    /**
     * Gets the max speed of the unit
     * 
     * @return speed
     */
    public abstract int getMaxSpeed();

    /**
     * Gets the radius of which a unit can conflict damage to another unit.
     * 
     * @return radius
     */
    public abstract int getAttackRadius();

}