package ru.itmo.rogue.model.unit.factory;

import ru.itmo.rogue.model.unit.Position;
import ru.itmo.rogue.model.unit.Unit;
import ru.itmo.rogue.model.unit.strategy.IdleStrategy;
import ru.itmo.rogue.model.unit.strategy.Strategy;

import java.util.List;

public class ChestFactory extends AbstractFactory {

    public ChestFactory(){
        super();
    }

    public ChestFactory(List<Position> possiblePositions){
        super(possiblePositions);
    }

    @Override
    public Unit getUnit() {
        var unit = createUnit();
        generateItems(2, 4).forEach(unit::addItem);
        return unit;
    }

    @Override
    protected char getAliveChar() {
        return '#';
    }

    @Override
    protected char getDeadChar() {
        return '#';
    }

    @Override
    protected int getHealth(){
        return 0;
    }

    @Override
    protected int getStrength(){
        return 0;
    }

    @Override
    protected int getExperience() {
        return 2;
    }

    @Override
    protected Strategy getStrategy() {
        return new IdleStrategy();
    }
}
