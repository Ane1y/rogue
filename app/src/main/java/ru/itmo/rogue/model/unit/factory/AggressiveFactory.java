package ru.itmo.rogue.model.unit.factory;

import ru.itmo.rogue.model.unit.Position;
import ru.itmo.rogue.model.unit.Unit;
import ru.itmo.rogue.model.unit.strategy.ChangeableAggressiveStrategy;
import ru.itmo.rogue.model.unit.strategy.Strategy;

import java.util.List;

public class AggressiveFactory extends AbstractFactory {

    private final int difficulty;

    public AggressiveFactory(int difficulty){
        this.difficulty = difficulty;
    }
    public AggressiveFactory(List<Position> possiblePositions, int difficulty){
        super(possiblePositions);
        this.difficulty = difficulty;

    }
    @Override

    public Unit getUnit() {
        var unit = createUnit();
        generateItems(1, 3).forEach(unit::addItem);
        return unit;
    }

    @Override
    protected Strategy getStrategy() {
        return new ChangeableAggressiveStrategy();
    }

    protected char getAliveChar() {
        return '*';
    }

    protected int getHealth() {
        return (int) (difficulty * 1.5);
    }

    protected int getStrength() {
        return difficulty;
    }
}
