package edu.ntnu.thosve.units;

import java.util.Comparator;
import java.util.List;

/**
 * Abstract class for a Unit.
 */
public abstract class Unit {
    private final String name;
    private double health;
    private final int attack;
    private final int armor;

    private double x;
    private double y;
    private double xSpeed;
    private double ySpeed;

    private Unit currentOpponent;

    /**
     * Constructor
     * @param name of the unit
     * @param health of the unit
     * @param attack damage for the unit
     * @param armor amount for the unit.
     * @throws IllegalArgumentException
     */
    public Unit(String name, double health, int attack, int armor) throws IllegalArgumentException{
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

        this.x = 0;
        this.y = 0;
        this.xSpeed = 0;
        this.ySpeed = 0;
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
            double opponentsNewHealth = opponent.getHealth() - attackDamage + resistance;
            opponent.setHealth(opponentsNewHealth);
        }
    }

    /**
     * Method for attacking another given Unit. It is calculated in the following way:
     * health_opponent - (attack + attackBonus)_this + (armor + resistBonus)_opponent
     * @param opponent which is being attacked.
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
     * Method for getting the distance to a spesified Unit
     * @param unit
     * @return distance to Unit
     */
    private double getDistanceTo(Unit unit) {
        return Math.sqrt(Math.pow(unit.x - this.x, 2) + Math.pow(unit.y - this.y, 2));
    }

    /**
     * Finds the closes Unit by distance given an list of opponents
     * @param opponents
     * @return closest Unit
     */
    private Unit findClosestOpponent(List<Unit> opponents) {
        return opponents.stream().min(Comparator.comparingDouble(this::getDistanceTo)).get();
    }

    /**
     * Method for setting x and y speed to point to given unit.
     * @param unit
     */
    private void moveToUnit(Unit unit) {
        double xVec = unit.getX() - this.getX();
        double yVec = unit.getY() - this.getY();

        double length = Math.sqrt(Math.pow(xVec, 2) + Math.pow(yVec, 2));

        double xVecNormalized = xVec / length;
        double yVecNormalized = yVec / length;

        this.setXSpeed(xVecNormalized * this.getMaxSpeed());
        this.setYSpeed(yVecNormalized * this.getMaxSpeed());
    }

    /**
     * Method that will make the unit target the closest opponent. This will change it movement direction to move to
     * the closest opponent
     * @param opponents list of all the opponents.
     */
    public void targetClosestOpponent(List<Unit> opponents) {
        Unit closestOpponent = findClosestOpponent(opponents);
        this.currentOpponent = closestOpponent;
        moveToUnit(closestOpponent);
    }

    /**
     * Method for updating the unit. If a opponent is within attacking range it will attack, if not it will
     * update the position of the unit based on the current speed.
     */
    public void update() {
        if (getDistanceTo(currentOpponent) <= getAttackRadius()) {
            attack(currentOpponent);
        } else {
            x += xSpeed;
            y += ySpeed;
        }
    }

    /**
     * Method for updating the unit. If a opponent is within attacking range it will attack, if not it will
     * update the position of the unit based on the current speed.
     */
    public void update(double deltaTime) {
        if (currentOpponent != null) {
            if (getDistanceTo(currentOpponent) <= getAttackRadius()) {
                attack(currentOpponent, deltaTime);
            } else {
                x += xSpeed * deltaTime;
                y += ySpeed * deltaTime;
            }
        }
    }

    /**
     * Short form for setting the position of the Unit.
     * @param x coordinate
     * @param y coordinate
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
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Gives the <b>current</b> health of the Unit.
     * @return health
     */
    public double getHealth() {
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
     * Returns the closest opponent that the unit has, given it has been updated with targetClosestOpponent()
     * @return currentOpponent
     */
    public Unit getCurrentOpponent() {
        return currentOpponent;
    }

    /**
     * Sets the health of the Unit.
     * @param health
     */
    public void setHealth(double health) {
        this.health = health;
    }

    /**
     * Gets a String representation of the Unit.
     * @return a string
     */
    @Override
    public String toString() {
        return "Unit: " + name +
                "\n\t- HP: " + health +
                "\n\t- Attack: " + attack +
                "\n\t- Armor: " + armor +
                "\n\t- Pos: (" + x + ", " + y + ")";
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

    /**
     * Gets the max speed of the unit
     * @return
     */
    public abstract int getMaxSpeed();

    /**
     * Gets the radius of which a unit can conflict damage to another unit.
     * @return
     */
    public abstract int getAttackRadius();


}