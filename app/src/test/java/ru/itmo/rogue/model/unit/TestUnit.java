package ru.itmo.rogue.model.unit;

import org.junit.jupiter.api.Test;
import ru.itmo.rogue.model.unit.factory.CompositeFactory;
import ru.itmo.rogue.model.unit.strategy.CowardStrategy;
import ru.itmo.rogue.model.unit.strategy.IdleStrategy;
import ru.itmo.rogue.model.unit.strategy.Strategy;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

public class TestUnit {

    private final CompositeFactory factory = new CompositeFactory(1);

    private Unit copyUnit(Unit unit) {
        return new Unit(
                unit.getMaxHealth(),
                unit.getStrength(),
                unit.getExperience(),
                unit.getLevel(),
                unit.getPosition(),
                unit.getStrategy(),
                unit.getAliveChar(),
                unit.getDeadChar());
    }


    @Test
    void equalUnits() {
        var unit1 = factory.getUnit();
        var unit2 = copyUnit(unit1);

        assertNotSame(unit1, unit2);
        assertEquals(unit1, unit2);
    }

    @Test
    void differentHealth() throws NoSuchFieldException, IllegalAccessException {
        var unit = factory.getUnit();
        var unitMaxHealth = copyUnit(unit);

        // Increase max health
        // At the time of the writing, maxHealth field has protected access modifier
        // and can be accessed in this package;
        // But this can change in the future, so change the value via Reflection
        Class<?> clazz = Unit.class;
        var fieldMaxHealth = clazz.getDeclaredField("maxHealth");
        fieldMaxHealth.setAccessible(true);
        fieldMaxHealth.set(unitMaxHealth, unitMaxHealth.getMaxHealth() + 2);

        assertEquals(unit.getMaxHealth() + 2, unitMaxHealth.getMaxHealth());
        assertNotEquals(unit, unitMaxHealth);

        var unitHealth = copyUnit(unit);
        unitHealth.changeHealth(2);

        assertEquals(unit.getHealth() + 2, unitHealth.getHealth());
        assertNotEquals(unit, unitHealth);
    }

    @Test
    void differentStrength() {
        var unit = factory.getUnit();
        var unitStrength = copyUnit(unit);

        unitStrength.changeStrength(2);
        assertEquals(unit.getStrength() + 2, unitStrength.getStrength());

        assertNotEquals(unit, unitStrength);
    }

    @Test
    void differentExperience() {
        var unit = factory.getUnit();
        var unitExp = copyUnit(unit);

        unitExp.increaseExperience(2);
        assertEquals(unit.getExperience() + 2, unitExp.getExperience());

        assertNotEquals(unit, unitExp);
    }

    @Test
    void differentLevel() {
        var unit = factory.getUnit();
        var unitLvl = copyUnit(unit);

        unitLvl.levelUp();
        assertEquals(unit.getLevel() + 1, unitLvl.getLevel());

        assertNotEquals(unit, unitLvl);
    }

    @Test
    void differentStrategy() throws NoSuchFieldException, IllegalAccessException {
        var unit1 = factory.getUnit();
        var unit2 = copyUnit(unit1);

        Strategy differentStrategy;
        if (unit1.getStrategy().getClass().equals(IdleStrategy.class)) {
            differentStrategy = new CowardStrategy();
        } else {
            differentStrategy = new IdleStrategy();
        }

        // Change strategy
        // At the time of the writing, strategy field has protected access modifier
        // and can be accessed in this package;
        // But this can change in the future, so change the value via Reflection
        Class<?> clazz = Unit.class;
        Field fieldStrategy = clazz.getDeclaredField("strategy");
        fieldStrategy.setAccessible(true);
        fieldStrategy.set(unit2, differentStrategy);

        assertNotEquals(unit1.getStrategy(), unit2.getStrategy());
        assertNotEquals(unit1, unit2);
    }

    @Test
    void healthCanOverflow() {
        var unit = factory.getUnit();

        int initialHealth = unit.getHealth();
        unit.changeHealth(10);

        assertTrue(unit.getHealth() > initialHealth);
        assertTrue(unit.getHealth() > unit.getMaxHealth());
    }

    @Test
    void healthCantUnderflow() {
        var unit = factory.getUnit();

        unit.changeHealth(-unit.getHealth() - 100);

        assertEquals(0, unit.getHealth());
    }

    @Test
    void strengthCantUnderflow() {
        var unit = factory.getUnit();

        unit.changeStrength(unit.getStrength() - 100);

        assertEquals(0, unit.getStrength());
    }

}
