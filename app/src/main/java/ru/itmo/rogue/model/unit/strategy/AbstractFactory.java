package ru.itmo.rogue.model.unit.strategy;

import ru.itmo.rogue.model.unit.Position;
import ru.itmo.rogue.model.unit.Unit;

import java.util.List;
import java.util.Random;

public abstract class AbstractFactory {
    protected final Random random = new Random();

    protected List<Position> currentPositions = null;
    protected boolean[] busy = null;

    public static int UNIT_EXPERIENCE = 2;
    public static int UNIT_LEVEL = 2;
    public abstract Unit getUnit();

    public AbstractFactory(){}

    public AbstractFactory(List<Position> possiblePositions){
        this.currentPositions = possiblePositions;
        busy = new boolean[possiblePositions.size()];
    }

    abstract public char getAliveChar();

    public char getDeadChar(){
        return 'x';
    }

    protected Position generatePosition(){
        if(currentPositions == null){
            return new Position();
        }
        int ind = random.nextInt(currentPositions.size());
        while (busy[ind]) {
            ind = random.nextInt(currentPositions.size());
        }
        Position position = currentPositions.get(ind);
        busy[ind] = true;
        return position;
    }

    public void setPossiblePositions(List<Position> possiblePositions) {
        currentPositions = possiblePositions;
    }

}
