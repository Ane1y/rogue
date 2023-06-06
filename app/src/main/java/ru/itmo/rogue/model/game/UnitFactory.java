package ru.itmo.rogue.model.game;

import ru.itmo.rogue.model.game.unit.PlayerProxyStrategy;
import ru.itmo.rogue.model.game.unit.Position;
import ru.itmo.rogue.model.game.unit.Unit;
import ru.itmo.rogue.model.game.unit.strategies.AgressiveStrategy;
import ru.itmo.rogue.model.game.unit.strategies.CowardStrategy;
import ru.itmo.rogue.model.game.unit.strategies.IdleStrategy;

import java.util.HashMap;
import java.util.Random;

public class UnitFactory {
    public static final char ALIVE_ENEMY = '*';
    public static final char DEAD_ENEMY = 'x';
    public static final char ALIVE_PLAYER = '@';
    public static final char DEAD_PLAYER = '?';
    private static final PlayerProxyStrategy playerProxyStrategy = new PlayerProxyStrategy();
    public static int DEFAULT_PLAYER_HEALTH = 3;
    public static int DEFAULT_PLAYER_STRENGTH = 1;
    public static int UNIT_STRENGTH = 3;
    public static int UNIT_HEALTH = 2;
    public static int UNIT_EXPERIENCE = 2;
    public static int UNIT_LEVEL = 2;
    public static String IDLE_PROBABILITY = "idleProb";
    public static String COWARD_PROBABILITY = "cowardProb";
    public static String AGGRESSIVE_PROBABILITY = "aggressiveProb";
    private final Random random = new Random();
    public int complexity;


    public UnitFactory(int complexity) {
        this.complexity = complexity;
    }

    public static Unit getPlayerUnit() {
        return new Unit(DEFAULT_PLAYER_HEALTH, // should use UnitFactory
                DEFAULT_PLAYER_STRENGTH,
                0,
                1,
                new Position(),
                playerProxyStrategy, ALIVE_PLAYER, DEAD_PLAYER);
    }

    public static PlayerProxyStrategy getPlayerProxyStrategy() {
        return playerProxyStrategy;
    }

    public Unit getUnit() {
        if (complexity == 0) {
            return getIdleUnit();
        }
        HashMap<String, Integer> probabilities = getProbabilities();
        int sum = probabilities.values().stream().mapToInt(Integer::intValue).sum();

        int num = random.nextInt(sum);
        if (num < probabilities.get(IDLE_PROBABILITY)) {
            return getIdleUnit();
        }
        if (num < probabilities.get(IDLE_PROBABILITY) + probabilities.get(COWARD_PROBABILITY)) {
            return getCowardUnit();
        }
        return getAgressiveUnit();
    }

    private HashMap<String, Integer> getProbabilities() {
        HashMap<String, Integer> prob = new HashMap<>();
        int idleProb = random.nextInt(20 / complexity);
        int cowardProb = random.nextInt(20 / complexity) + idleProb;
        int aggressiveProb = random.nextInt(20 * complexity) + cowardProb;
        prob.put(IDLE_PROBABILITY, idleProb);
        prob.put(COWARD_PROBABILITY, cowardProb);
        prob.put(AGGRESSIVE_PROBABILITY, aggressiveProb);

        return prob;
    }

    private Unit getAgressiveUnit() {
        return new Unit(UNIT_HEALTH,
                UNIT_STRENGTH,
                UNIT_EXPERIENCE,
                UNIT_LEVEL,
                new Position(),
                new AgressiveStrategy(), ALIVE_ENEMY, DEAD_ENEMY);
    }

    private Unit getCowardUnit() {
        return new Unit(UNIT_HEALTH,
                UNIT_STRENGTH,
                UNIT_EXPERIENCE,
                UNIT_LEVEL,
                new Position(),
                new CowardStrategy(), ALIVE_ENEMY, DEAD_ENEMY);
    }

    private Unit getIdleUnit() {
        return new Unit(UNIT_HEALTH,
                UNIT_STRENGTH,
                UNIT_EXPERIENCE,
                UNIT_LEVEL,
                new Position(),
                new IdleStrategy(), ALIVE_ENEMY, DEAD_ENEMY);
    }

}