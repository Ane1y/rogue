package ru.itmo.rogue.model.unit;

import ru.itmo.rogue.model.unit.strategy.IdleStrategy;

import java.util.List;

public class ChestFactory extends AbstractFactory {
    private int difficulty;

    public ChestFactory(int difficulty){
        this.difficulty = difficulty;
    }
    public ChestFactory(List<Position> possiblePositions, int difficulty){
        super(possiblePositions);
        this.difficulty = difficulty;

    }
    @Override

    public Unit getUnit() {
        return new Unit(getHealth(), getStrength(), 0, 0, generatePosition(), new IdleStrategy(), getAliveChar(), getDeadChar());

    }
    public char getAliveChar() {
        return '#';
    }
    public char getDeadChar() {
        return '#';
    }
    private int getHealth(){
        return  0;
    }

    private int getStrength(){
        return  2*difficulty;
    }
}
