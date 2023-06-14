package ru.itmo.rogue.model.unit;

import ru.itmo.rogue.model.items.ItemFactory;
import ru.itmo.rogue.model.unit.Position;
import ru.itmo.rogue.model.unit.Unit;
import ru.itmo.rogue.model.unit.strategy.PlayerInputStrategy;

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

    // Player generation

    private static final PlayerInputStrategy playerStrategy = new PlayerInputStrategy();
    private static final Unit player;

    static {
        player = new Unit(
                3, 1, 0, 1, new Position(), playerStrategy, '@', '?');
        player.addItem(ItemFactory.getPoison());
    }

    public static Unit getPlayer() {
        return player;
    }

    public static PlayerInputStrategy getPlayerStrategy() {
        return playerStrategy;
    }

}
