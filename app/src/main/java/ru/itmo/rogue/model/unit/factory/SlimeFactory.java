package ru.itmo.rogue.model.unit.factory;

import ru.itmo.rogue.model.unit.Unit;
import ru.itmo.rogue.model.unit.strategy.CloneStrategy;
import ru.itmo.rogue.model.unit.strategy.Strategy;

public class SlimeFactory extends AbstractFactory {
    @Override
    public Unit getUnit() {
        var unit = createUnit();
        generateItems(1, 1).forEach(unit::addItem);
        return unit;
    }

    @Override
    protected Strategy getStrategy() {
        return new CloneStrategy();
    }

    @Override
    protected int getExperience() {
        return 0;
    }

    @Override
    protected char getAliveChar() {
        return '^';
    }

    @Override
    protected char getDeadChar() {
        return 'v';
    }
}
