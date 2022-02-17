package edu.ntnu.thosve.units;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
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
        public void testDecrementalResistBonus() {
            RangedUnit unit = new RangedUnit("Ranged Unit", 100);
            RangedUnit opponent = new RangedUnit("Opponent Unit", 100);

            assertEquals(6, unit.getResistBonus());

            opponent.attack(unit);
            assertEquals(4, unit.getResistBonus());

            opponent.attack(unit);
            assertEquals(2, unit.getResistBonus());

            opponent.attack(unit);
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

