package ru.itmo.rogue.model.game;

import ru.itmo.rogue.model.game.unit.PlayerProxyStrategy;
import ru.itmo.rogue.model.game.unit.Position;
import ru.itmo.rogue.model.game.unit.Unit;
import ru.itmo.rogue.model.game.unit.strategies.AgressiveStrategy;
import ru.itmo.rogue.model.game.unit.strategies.CowardStrategy;
import ru.itmo.rogue.model.game.unit.strategies.IdleStrategy;

import java.util.Random;

public class UnitFactory {
    public static final char ALIVE_AGGRESSIVE_ENEMY = '*';
    public static final char ALIVE_IDLE_ENEMY = '#';
    public static final char ALIVE_COWARD_ENEMY = '%';

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
    private final Random random = new Random();

    public int complexity;


    public UnitFactory(int complexity) {
        this.complexity = complexity;
    }

    public static Unit getPlayerUnit() {
        return new Unit(DEFAULT_PLAYER_HEALTH,
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
        Probabilities probabilities = new Probabilities();
        int sum = probabilities.sum();

        int num = random.nextInt(sum);
        if (num < probabilities.idleProb) {
            return getIdleUnit();
        }
        if (num < probabilities.idleProb + probabilities.cowardProb) {
            return getCowardUnit();
        }
        return getAgressiveUnit();
    }


    private Unit getAgressiveUnit() {
        return new Unit(UNIT_HEALTH,
                UNIT_STRENGTH,
                UNIT_EXPERIENCE,
                UNIT_LEVEL,
                new Position(),
                new AgressiveStrategy(), ALIVE_AGGRESSIVE_ENEMY, DEAD_ENEMY);
    }

    private Unit getCowardUnit() {
        return new Unit(UNIT_HEALTH,
                UNIT_STRENGTH,
                UNIT_EXPERIENCE,
                UNIT_LEVEL,
                new Position(),
                new CowardStrategy(), ALIVE_COWARD_ENEMY, DEAD_ENEMY);
    }

    private Unit getIdleUnit() {
        return new Unit(UNIT_HEALTH,
                UNIT_STRENGTH,
                UNIT_EXPERIENCE,
                UNIT_LEVEL,
                new Position(),
                new IdleStrategy(), ALIVE_IDLE_ENEMY, DEAD_ENEMY);
    }

    class Probabilities {
        private final int idleProb;
        private final int cowardProb;
        private final int aggressiveProb;

        private Probabilities() {
            this.idleProb = random.nextInt(20 / complexity);
            this.cowardProb = random.nextInt(20 / complexity) + idleProb;
            this.aggressiveProb = random.nextInt(20 * complexity) + cowardProb;
        }

        private int sum() {
            return idleProb + cowardProb + aggressiveProb;
        }

    }

}