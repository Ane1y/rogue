package ru.itmo.rogue.model.unit.factory;

import ru.itmo.rogue.model.items.Item;
import ru.itmo.rogue.model.items.ItemFactory;
import ru.itmo.rogue.model.unit.Position;
import ru.itmo.rogue.model.unit.Unit;
import ru.itmo.rogue.model.unit.strategy.PlayerInputStrategy;
import ru.itmo.rogue.model.unit.strategy.Strategy;

import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

public abstract class AbstractFactory implements UnitFactory {

    // Helpers
    protected final Random random = new Random();
    protected List<Position> positions = null;
    protected boolean[] busy = null;

    protected final ItemFactory itemFactory = new ItemFactory();

    // Construction
    public AbstractFactory(){}

    public AbstractFactory(List<Position> possiblePositions){
        this.positions = possiblePositions;
        busy = new boolean[possiblePositions.size()];
    }

    protected Unit createUnit() {
        return new Unit(
                getHealth(),
                getStrength(),
                getExperience(),
                getLevel(),
                generatePosition(),
                getStrategy(),
                getAliveChar(),
                getDeadChar()
        );
    }

    protected List<Item> generateItems(int min, int max) {
        int num = random.nextInt(min, max + 1);
        return IntStream
                .range(0, num)
                .mapToObj(a -> itemFactory.getItem())
                .toList();
    }

    protected int getHealth() {
        return 2;
    }

    protected int getStrength() {
        return 2;
    }

    protected int getExperience() {
        return 10;
    }

    protected int getLevel() {
        return 1;
    }

    protected Position generatePosition(){
        if (positions == null){
            return new Position();
        }
        int ind = random.nextInt(positions.size());
        while (busy[ind]) {
            ind = random.nextInt(positions.size());
        }
        Position position = positions.get(ind);
        busy[ind] = true;
        return position;
    }

    protected abstract Strategy getStrategy();

    protected abstract char getAliveChar();

    protected char getDeadChar(){
        return 'x';
    }

    @Override
    public void setPositions(List<Position> possiblePositions) {
        positions = possiblePositions;
        busy = new boolean[positions.size()];
    }

    // Player generation
    private static final PlayerInputStrategy playerStrategy = new PlayerInputStrategy();
    private static final Unit player;

    static {
        player = new Unit(3, 1, 0, 1, new Position(), playerStrategy, '@', '?');
        player.addItem(ItemFactory.getPoison());
    }

    public static Unit getPlayer() {
        return player;
    }

    public static PlayerInputStrategy getPlayerStrategy() {
        return playerStrategy;
    }

}
