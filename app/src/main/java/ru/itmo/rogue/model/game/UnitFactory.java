package ru.itmo.rogue.model.game;

import ru.itmo.rogue.model.game.unit.PlayerProxyStrategy;
import ru.itmo.rogue.model.game.unit.Position;
import ru.itmo.rogue.model.game.unit.Unit;

public class UnitFactory {
    public static int DEFAULT_PLAYER_HEALTH = 3;
    public static int DEFAULT_PLAYER_STRENGTH = 1;
    public int complexity;
    public UnitFactory(int complexity) {
        this.complexity = complexity;
    }

    public Unit getUnit() {
        // TODO: Replace with real generation
        return new Unit(0, 0, 0, 1, new Position(), playerProxyStrategy);
    }

    public static Unit getPlayerUnit() {
        return new Unit(DEFAULT_PLAYER_HEALTH,
                DEFAULT_PLAYER_STRENGTH,
                0,
                1,
                new Position(),
                playerProxyStrategy);
    }

    public static PlayerProxyStrategy getPlayerProxyStrategy() {
        return playerProxyStrategy;
    }

    private static final PlayerProxyStrategy playerProxyStrategy = new PlayerProxyStrategy();

}
