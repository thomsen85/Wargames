package edu.ntnu.thosve.units;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class UnitTest {

    @Test
    public void testAttackWontGiveHealthIfResistantIsGreaterThanAttackDamage() {
        Unit unit = new InfantryUnit("Base Unit", 100, 10, 10);
        Unit opponent = new InfantryUnit("Opponent Unit", 100, 5, 20);
        assertTrue(opponent.getHealth() <= 100);
    }

    @Test
    public void testToStringMethodOfUnit() {
        Unit unit = new InfantryUnit("Unit", 100);
        assertTrue(!unit.toString().isBlank());
    }

    @Test
    public void testUnitThrowsIllegalArgumentExceptionOnIllegalArguments() {
        assertThrows(IllegalArgumentException.class, () -> {
            new InfantryUnit("", 100);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            new InfantryUnit("Bob", -100);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            new InfantryUnit("Bob", 100, -20, 0);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            new InfantryUnit("Bob", 100, 20, -50);
        });

    }

    @Test
    public void testUnitTargetsClosestOpponent() {
        InfantryUnit unit = new InfantryUnit("Unit", 100);
        InfantryUnit opponent1 = new InfantryUnit("Opponent 1 (Closest)", 100);
        InfantryUnit opponent2 = new InfantryUnit("Opponent 1 (Farthest)", 100);
        List<Unit> opponents = new ArrayList<Unit>();

        opponent1.setPos(10,10);
        opponent2.setPos(20, 32);

        opponents.add(opponent1);
        opponents.add(opponent2);

        unit.targetClosestOpponent(opponents);


        // Checking if he is moving diagonally
        assertEquals(unit.getXSpeed(), unit.getYSpeed());

        //Checking if he is moving the correct diagonal
        assertTrue(unit.getXSpeed() > 0);
        assertTrue(unit.getYSpeed() > 0);
    }


    @Test
    public void testUnitMovesAtMaxSpeed() {
        InfantryUnit unit = new InfantryUnit("Unit", 100);
        InfantryUnit opponent = new InfantryUnit("Opponent", 100);
        opponent.setPos(20, 10);
        List<Unit> opponents = new ArrayList<>();
        opponents.add(opponent);

        unit.targetClosestOpponent(opponents);

        double speed = Math.sqrt(Math.pow(unit.getXSpeed(), 2) + Math.pow(unit.getYSpeed(), 2));

        assertEquals(unit.getMaxSpeed(), speed);
    }

    @Nested
    class InfantryUnitTest {

        @Test
        public void testFullCreationOfInfantryUnit() {
            String name = "Infantry Unit";
            int health = 100;
            int attack = 5;
            int armor = 8;
            InfantryUnit unit = new InfantryUnit(name, health, attack, armor);
            assertEquals(name, unit.getName());
            assertEquals(health,  unit.getHealth());
            assertEquals(attack,  unit.getAttack());
            assertEquals(armor,  unit.getArmor());
        }

        @Test
        public void testSimplifiedCreationOfInfantryUnit() {
            String name = "Infantry Unit";
            int health = 100;
            InfantryUnit unit = new InfantryUnit(name, health);
            assertEquals(name,  unit.getName());
            assertEquals(health,  unit.getHealth());
            assertEquals(15,  unit.getAttack());
            assertEquals(10,  unit.getArmor());

        }

        @Test
        public void testAttackBonus() {
            String name = "Infantry Unit";
            int health = 100;
            InfantryUnit unit = new InfantryUnit(name, health);

            assertEquals(2,  unit.getAttackBonus());
        }

        @Test
        public void testResistBonus() {
            String name = "Infantry Unit";
            int health = 100;
            InfantryUnit unit = new InfantryUnit(name, health);

            assertEquals(1,  unit.getResistBonus());
        }
    }

    @Nested
    class RangedUnitTest {

        @Test
        public void testFullCreationOfRangedUnit() {
            String name = "Ranged Unit";
            int health = 100;
            int attack = 5;
            int armor = 8;
            RangedUnit unit = new RangedUnit(name, health, attack, armor);
            assertEquals(name,  unit.getName());
            assertEquals(health,  unit.getHealth());
            assertEquals(attack,  unit.getAttack());
            assertEquals(armor,  unit.getArmor());
        }
        
        @Test
        public void testSimplifiedCreationOfRangedUnit() {
            String name = "Ranged Unit";
            int health = 100;
            RangedUnit unit = new RangedUnit(name, health);
            assertEquals(name, unit.getName());
            assertEquals(health,  unit.getHealth());
            assertEquals(15,  unit.getAttack());
            assertEquals(8,  unit.getArmor());
        }
        
        @Test
        public void testAttackBonus() {
            String name = "Ranged Unit";
            int health = 100;
            RangedUnit unit = new RangedUnit(name, health);
            assertEquals(3, unit.getAttackBonus());
        }

        @Test
        public void testResistBonus() {
            RangedUnit unit = new RangedUnit("Ranged Unit", 100);

            assertEquals(2, unit.getResistBonus());

        }
    }

    @Nested
    class CavalryUnitTest {

        @Test
        public void testFullCreationOfCavalryUnit() {
            String name = "Cavalry Unit";
            int health = 100;
            int attack = 5;
            int armor = 8;
            CavalryUnit unit = new CavalryUnit(name, health, attack, armor);
            assertEquals(name,  unit.getName());
            assertEquals(health,  unit.getHealth());
            assertEquals(attack,  unit.getAttack());
            assertEquals(armor,  unit.getArmor());
        }

        @Test
        public void testSimplifiedCreationOfCavalryUnit() {
            String name = "Cavalry Unit";
            int health = 100;
            CavalryUnit unit = new CavalryUnit(name, health);
            assertEquals(name,  unit.getName());
            assertEquals(health,  unit.getHealth());
            assertEquals(20,  unit.getAttack());
            assertEquals(12,  unit.getArmor());
        }

        @Test
        public void testAttackBonus() {
            CavalryUnit unit = new CavalryUnit("Cavalry unit", 100);
            CavalryUnit opponent = new CavalryUnit("Opponent unit", 100);

            assertEquals(6, unit.getAttackBonus());
            unit.attack(opponent);
            assertEquals(2, unit.getAttackBonus());
            unit.attack(opponent);
            assertEquals(2, unit.getAttackBonus());
        }

        @Test
        public void testResistBonus() {
            CavalryUnit unit = new CavalryUnit("Cavalry unit", 100);
            assertEquals(1, unit.getResistBonus());
        }
    }

    @Nested
    class CommanderUnitTest {
        @Test
        public void testFullCreationOfCommanderUnit() {
            String name = "Commander Unit";
            int health = 100;
            int attack = 5;
            int armor = 8;
            CommanderUnit unit = new CommanderUnit(name, health, attack, armor);
            assertEquals(name,  unit.getName());
            assertEquals(health,  unit.getHealth());
            assertEquals(attack,  unit.getAttack());
            assertEquals(armor,  unit.getArmor());
        }

        @Test
        public void testSimplifiedCreationOfCommanderUnit() {
            String name = "Commander Unit";
            int health = 100;
            CommanderUnit unit = new CommanderUnit(name, health);
            assertEquals(name,  unit.getName());
            assertEquals(health,  unit.getHealth());
            assertEquals(25,  unit.getAttack());
            assertEquals(15,  unit.getArmor());
        }

        @Test
        public void testAttackBonus() {
            CommanderUnit unit = new CommanderUnit("Commander unit", 100);
            CommanderUnit opponent = new CommanderUnit("Opponent unit", 100);

            assertEquals(6, unit.getAttackBonus());
            unit.attack(opponent);
            assertEquals(2, unit.getAttackBonus());
            unit.attack(opponent);
            assertEquals(2, unit.getAttackBonus());
        }

        @Test
        public void testResistBonus() {
            CommanderUnit unit = new CommanderUnit("Commander unit", 100);
            assertEquals(1, unit.getResistBonus());
        }
    }


}

