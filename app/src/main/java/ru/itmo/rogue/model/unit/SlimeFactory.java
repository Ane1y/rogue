package ru.itmo.rogue.model.unit;

import ru.itmo.rogue.model.unit.strategy.AbstractFactory;
import ru.itmo.rogue.model.unit.strategy.CloneStrategy;

public class SlimeFactory extends AbstractFactory {

    public static final int HEALTH = 2;
    public static final int STRENGTH = 1;

    @Override
    public Unit getUnit() {
        return new Unit(
                HEALTH,
                STRENGTH,
                2,
                1,
                generatePosition(),
                new CloneStrategy(),
                getAliveChar(),
                getDeadChar()
        );
    }

    @Override
    public char getAliveChar() {
        return 0;
    }
}
