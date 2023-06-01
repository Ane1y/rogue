package ru.itmo.rogue.model;

public class State {

    public enum Focus {
        GAME, LEVEL, INVENTORY
    }

    public Focus focus = Focus.GAME;

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
