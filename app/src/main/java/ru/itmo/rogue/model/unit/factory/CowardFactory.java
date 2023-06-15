package ru.itmo.rogue.model.unit.factory;

import ru.itmo.rogue.model.unit.Position;
import ru.itmo.rogue.model.unit.Unit;
import ru.itmo.rogue.model.unit.strategy.CowardStrategy;
import ru.itmo.rogue.model.unit.strategy.Strategy;

import java.util.List;

public class CowardFactory extends AbstractFactory {
    private final int difficulty;

    public CowardFactory(int difficulty){
        this.difficulty = difficulty;
    }
    public CowardFactory(List<Position> possiblePositions, int difficulty){
        super(possiblePositions);
        this.difficulty = difficulty;

    }

    @Override
    public Unit getUnit() {
        var unit = createUnit();
        generateItems(1, 2).forEach(unit::addItem);
        return unit;
    }

    public char getAliveChar() {
        return '%';
    }

    protected int getHealth() {
        return difficulty * 2;
    }
    protected int getStrength(){
        return 1;
    }

    @Override
    protected Strategy getStrategy() {
        return new CowardStrategy();
    }
}
