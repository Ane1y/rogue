package ru.itmo.rogue.model;

import ru.itmo.rogue.model.game.unit.Unit;

public class State {
    public Focus focus = Focus.GAME;

    public Unit player;
    public Unit[] enemies;

    // TODO: Level map

    public enum Focus {
        GAME, LEVEL, INVENTORY
    }

    public static class Delta {
        // TODO: Define
    }

    public Delta getDelta() {
        // TODO: Define
        return null;
    }

    public void clearDelta() {
        // TODO: clear delta
    }

}
