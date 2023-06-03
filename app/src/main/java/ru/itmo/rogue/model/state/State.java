package ru.itmo.rogue.model.state;

import ru.itmo.rogue.model.game.unit.*;

import java.util.ArrayList;
import java.util.List;

public class State {
    public static int DEFAULT_PLAYER_HEALTH = 3;
    public static int DEFAULT_PLAYER_STRENGTH = 1;
    public Focus focus = Focus.GAME;
    public Unit player;
    public List<Unit> enemies = new ArrayList<>();
    public boolean running = true;

    public MapTile[][] map;

    enum MapTile {
        FLOOR, WALL, DOOR_IN, DOOR_OUT

        }
    public enum Focus {
        GAME, LEVEL, INVENTORY

        }

    public State(/* Maybe add parameters... */) {
        player = new Unit(DEFAULT_PLAYER_HEALTH, // should use UnitFactory
                DEFAULT_PLAYER_STRENGTH,
                new Position(),
                new PlayerProxyStrategy());
        // TODO: We need to decide on default player parameters
    }

}
