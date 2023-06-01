package ru.itmo.rogue.model;

public class Unit {
    int health;
    int strength;

    Item[] stash;

    // coordinates
    int x;
    int y;

    UnitStrategy strategy;

    public Action getAction(State state) {
        return strategy.getAction(this, state);
    }
}
