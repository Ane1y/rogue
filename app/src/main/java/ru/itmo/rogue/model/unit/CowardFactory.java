package ru.itmo.rogue.model.unit;

import ru.itmo.rogue.model.unit.AbstractFactory;
import ru.itmo.rogue.model.unit.Position;
import ru.itmo.rogue.model.unit.Unit;
import ru.itmo.rogue.model.unit.strategy.CowardStrategy;

import java.util.List;

public class CowardFactory extends AbstractFactory {
    private int difficulty;

    public CowardFactory(int difficulty){
        this.difficulty = difficulty;
    }
    public CowardFactory(List<Position> possiblePositions, int difficulty){
        super(possiblePositions);
        this.difficulty = difficulty;

    }

    @Override
    public Unit getUnit() {
        return new Unit(getHealth(), getStrength(), UNIT_EXPERIENCE, UNIT_LEVEL, generatePosition(), new CowardStrategy(), getAliveChar(), getDeadChar());

    }
    public char getAliveChar()
    {
        return '%';
    }
    private int getHealth(){
        return  difficulty*2;
    }
    private int getStrength(){
        return  2*difficulty;
    }
}
