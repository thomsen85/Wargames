package edu.ntnu.thosve.units;

import edu.ntnu.thosve.models.units.*;
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
        assertFalse(unit.toString().isBlank());
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
            assertEquals(health, unit.getHealth());
            assertEquals(attack, unit.getAttack());
            assertEquals(armor, unit.getArmor());
        }

        @Test
        public void testSimplifiedCreationOfInfantryUnit() {
            String name = "Infantry Unit";
            int health = 100;
            InfantryUnit unit = new InfantryUnit(name, health);
            assertEquals(name, unit.getName());
            assertEquals(health, unit.getHealth());
            assertEquals(InfantryUnit.DEFAULT_ATTACK, unit.getAttack());
            assertEquals(InfantryUnit.DEFAULT_ARMOR, unit.getArmor());
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
            assertEquals(name, unit.getName());
            assertEquals(health, unit.getHealth());
            assertEquals(attack, unit.getAttack());
            assertEquals(armor, unit.getArmor());
        }

        @Test
        public void testSimplifiedCreationOfRangedUnit() {
            String name = "Ranged Unit";
            int health = 100;
            RangedUnit unit = new RangedUnit(name, health);
            assertEquals(name, unit.getName());
            assertEquals(health, unit.getHealth());
            assertEquals(RangedUnit.DEFAULT_ATTACK, unit.getAttack());
            assertEquals(RangedUnit.DEFAULT_ARMOR, unit.getArmor());
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
            assertEquals(name, unit.getName());
            assertEquals(health, unit.getHealth());
            assertEquals(attack, unit.getAttack());
            assertEquals(armor, unit.getArmor());
        }

        @Test
        public void testSimplifiedCreationOfCavalryUnit() {
            String name = "Cavalry Unit";
            int health = 100;
            CavalryUnit unit = new CavalryUnit(name, health);
            assertEquals(name, unit.getName());
            assertEquals(health, unit.getHealth());
            assertEquals(CavalryUnit.DEFAULT_ATTACK, unit.getAttack());
            assertEquals(CavalryUnit.DEFAULT_ARMOR, unit.getArmor());
        }

        @Test
        public void testAttackBonus() {
            CavalryUnit unit = new CavalryUnit("Cavalry unit", 100);
            CavalryUnit opponent = new CavalryUnit("Opponent unit", 100);

            assertEquals(CavalryUnit.CHARGE_ATTACK_BONUS, unit.getAttackBonus());
            unit.attack(opponent);
            assertEquals(CavalryUnit.BASE_ATTACK_BONUS, unit.getAttackBonus());
            unit.attack(opponent);
            assertEquals(CavalryUnit.BASE_ATTACK_BONUS, unit.getAttackBonus());
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
            assertEquals(name, unit.getName());
            assertEquals(health, unit.getHealth());
            assertEquals(attack, unit.getAttack());
            assertEquals(armor, unit.getArmor());
        }

        @Test
        public void testSimplifiedCreationOfCommanderUnit() {
            String name = "Commander Unit";
            int health = 100;
            CommanderUnit unit = new CommanderUnit(name, health);
            assertEquals(name, unit.getName());
            assertEquals(health, unit.getHealth());
            assertEquals(CommanderUnit.DEFAULT_ATTACK, unit.getAttack());
            assertEquals(CommanderUnit.DEFAULT_ARMOR, unit.getArmor());
        }

        @Test
        public void testAttackBonus() {
            CommanderUnit unit = new CommanderUnit("Commander unit", 100);
            CommanderUnit opponent = new CommanderUnit("Opponent unit", 100);

            assertEquals(CommanderUnit.CHARGE_ATTACK_BONUS, unit.getAttackBonus());
            unit.attack(opponent);
            assertEquals(CommanderUnit.BASE_ATTACK_BONUS, unit.getAttackBonus());
            unit.attack(opponent);
            assertEquals(CommanderUnit.BASE_ATTACK_BONUS, unit.getAttackBonus());
        }

    }

}
