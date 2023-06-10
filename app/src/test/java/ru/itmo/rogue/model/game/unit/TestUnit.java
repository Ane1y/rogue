package ru.itmo.rogue.model.game.unit;

import org.junit.jupiter.api.Test;
import ru.itmo.rogue.model.game.UnitFactory;
import ru.itmo.rogue.model.game.unit.strategies.CowardStrategy;
import ru.itmo.rogue.model.game.unit.strategies.IdleStrategy;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

public class TestUnit {

    private final UnitFactory factory = new UnitFactory(1);

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
    void unitMoved() {
        var unit = factory.getUnit();

        var movement = new Movement(2, 2);
        var destination = unit.getPosition().move(movement);

        var unitMoved = copyUnit(unit);
        var movedUpdate1 = unitMoved.move(movement);

        var unitTeleported = copyUnit(unit);
        var teleportedUpdate = unitTeleported.moveTo(destination);

        assertEquals(unitMoved, unitTeleported);

        // Move back
        var reversedMovement = new Movement(-2, -2);
        var movedUpdate2 = unitMoved.move(reversedMovement);

        assertEquals(unit, unitMoved);
        assertNotEquals(unitMoved, unitTeleported);

        // Check position
        assertEquals(unit.getPosition().getX(), unit.getHorizontalPos());
        assertEquals(unit.getPosition().getY(), unit.getVerticalPos());
        assertEquals(unit.getPosition().move(movement), unitTeleported.getPosition());

        // Check returned updates
        assertSame(unitMoved, movedUpdate1.getUnit());
        assertSame(unitMoved, movedUpdate2.getUnit());
        assertSame(unitTeleported, teleportedUpdate.getUnit());

        assertEquals(unit.getPosition(), movedUpdate1.getOldPosition());
        assertEquals(unit.getPosition(), teleportedUpdate.getOldPosition());
        assertEquals(unitTeleported.getPosition(), movedUpdate2.getOldPosition());
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
