package ru.itmo.rogue.model.game;

import ru.itmo.rogue.model.game.unit.Position;
import ru.itmo.rogue.model.game.unit.Strategy;
import ru.itmo.rogue.model.game.unit.Unit;
import ru.itmo.rogue.model.game.unit.strategies.*;

import java.util.*;

public class UnitFactory {
    public static final char ALIVE_AGGRESSIVE_ENEMY = '*';
    public static final char ALIVE_IDLE_ENEMY = '#';
    public static final char ALIVE_COWARD_ENEMY = '%';

    public static final char DEAD_ENEMY = 'x';
    public static final char ALIVE_PLAYER = '@';
    public static final char DEAD_PLAYER = '?';
    private static final PlayerProxyStrategy playerProxyStrategy = new PlayerProxyStrategy();
//    private static final Unit player = newPlayerUnit();

    public static int DEFAULT_PLAYER_HEALTH = 3;
    public static int DEFAULT_PLAYER_STRENGTH = 1;
    private static final Unit player = newPlayerUnit();
    public static int UNIT_STRENGTH = 2;
    public static int UNIT_HEALTH = 2;
    public static int UNIT_EXPERIENCE = 2;
    public static int UNIT_LEVEL = 2;
    public final int difficulty;
    private final Random random = new Random();
    private final int width;
    private final int height;
    private final List<Position> currentPositions;

    public UnitFactory(int difficulty) {
        this(difficulty, 10, 10, Collections.emptyList());
    }

    public UnitFactory(int difficulty, int width, int height, List<Position> doors) {
        this.difficulty = difficulty;
        this.width = width;
        this.height = height;
        this.currentPositions = genListOfPositions();
        currentPositions.removeAll(doors);
    }

    public static Unit getPlayerUnit() {
        return player;
    }

    private static Unit newPlayerUnit() {
        return new Unit(DEFAULT_PLAYER_HEALTH, DEFAULT_PLAYER_STRENGTH, 0, 1, new Position(), playerProxyStrategy, ALIVE_PLAYER, DEAD_PLAYER);
    }

    public static PlayerProxyStrategy getPlayerProxyStrategy() {
        return playerProxyStrategy;
    }

    private List<Position> genListOfPositions() {
        List<Position> currentPositions = new ArrayList<>();
        for (int x = 1; x < width - 1; x++) {
            for (int y = 1; y < height - 1; y++) {
                currentPositions.add(new Position(x, y));
            }
        }
        return currentPositions;
    }

    public Unit getUnit() {
        if (difficulty == 0) {
            return getIdleUnit();
        }
        Probabilities probabilities = new Probabilities();
        int sum = probabilities.sum();

        Unit unit;
        int num = random.nextInt(sum);
        if (num < probabilities.idleProb) {
            unit = getIdleUnit();
        }
        else {
            if (num < probabilities.idleProb + probabilities.cowardProb) {
                unit = getCowardUnit();
            } else {
                if(num< probabilities.idleProb + probabilities.cowardProb+probabilities.changeableAggressiveProb)
                    unit = getChangebleStrategyUnit();
                else {
                    unit= getAgressiveUnit();
                }
            }
        }
        ItemFactory factory = new ItemFactory();
        int items = random.nextInt(6);
        for (int i = 0; i < items; i++) {
            unit.getStash().add(factory.getItem());
        }

        return unit;
    }

    private Position generatePosition() {
        int ind = random.nextInt(currentPositions.size());
        Position position = currentPositions.get(ind);
        currentPositions.remove(ind);
        return position;
    }


    private Unit getAgressiveUnit() {
        return new Unit(UNIT_HEALTH, UNIT_STRENGTH, UNIT_EXPERIENCE, UNIT_LEVEL, generatePosition(), new AgressiveStrategy(), ALIVE_AGGRESSIVE_ENEMY, DEAD_ENEMY);
    }

    private Unit getCowardUnit() {
        return new Unit(UNIT_HEALTH, UNIT_STRENGTH, UNIT_EXPERIENCE, UNIT_LEVEL, generatePosition(), new CowardStrategy(), ALIVE_COWARD_ENEMY, DEAD_ENEMY);
    }

    private Unit getIdleUnit() {
        return new Unit(UNIT_HEALTH, UNIT_STRENGTH, UNIT_EXPERIENCE, UNIT_LEVEL, generatePosition(), new IdleStrategy(), ALIVE_IDLE_ENEMY, DEAD_ENEMY);
    }

    public Unit getChangebleStrategyUnit() {
        return new Unit(UNIT_HEALTH, UNIT_STRENGTH, UNIT_EXPERIENCE, UNIT_LEVEL, generatePosition(), new ChangeableAggressiveStrategy(), ALIVE_IDLE_ENEMY, DEAD_ENEMY);
    }

    class Probabilities {
        private final int idleProb;
        private final int cowardProb;
        private final int aggressiveProb;
        private final int changeableAggressiveProb;


        private Probabilities() {
            this.idleProb = random.nextInt(20 / difficulty);
            this.cowardProb = random.nextInt(20 / difficulty) + idleProb;
            this.aggressiveProb = random.nextInt(20 * difficulty) + cowardProb;
            this.changeableAggressiveProb = aggressiveProb;
        }

        private int sum() {
            return idleProb + cowardProb + aggressiveProb + changeableAggressiveProb;
        }

    }

}