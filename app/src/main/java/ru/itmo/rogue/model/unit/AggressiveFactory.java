package ru.itmo.rogue.model.unit;

import ru.itmo.rogue.model.unit.AbstractFactory;
import ru.itmo.rogue.model.unit.Position;
import ru.itmo.rogue.model.unit.Unit;
import ru.itmo.rogue.model.unit.strategy.AgressiveStrategy;

import java.util.List;

public class AggressiveFactory extends AbstractFactory {

    private int difficulty;

    public AggressiveFactory(int difficulty){
        this.difficulty = difficulty;
    }
    public AggressiveFactory(List<Position> possiblePositions, int difficulty){
        super(possiblePositions);
        this.difficulty = difficulty;

    }
    @Override

    public Unit getUnit() {
        return new Unit(getHealth(), getStrength(), UNIT_EXPERIENCE, UNIT_LEVEL, generatePosition(), new AgressiveStrategy(), getAliveChar(), getDeadChar());

    }
    public char getAliveChar()
    {
        return '*';
    }
    private int getHealth(){
        return  2*difficulty;
    }

    private int getStrength(){
        return  2*difficulty;
    }
}