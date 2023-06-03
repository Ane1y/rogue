package ru.itmo.rogue.model.game.unit;

import ru.itmo.rogue.model.state.Delta;
import ru.itmo.rogue.model.state.State;

import java.util.List;

public class Unit {
    protected int maxHealth;
    protected int health;
    protected int strength;
    protected List<Item> stash;
    protected Position position;
    protected Strategy strategy;

    public Unit(int maxHealth, int strength, Position position, Strategy strategy) {
        this.maxHealth = maxHealth;
        this.health = maxHealth;
        this.strength = strength;
        this.position = position;
        this.strategy = strategy;
    }

    public Action getAction(State state) {
        return strategy.getAction(this, state);
    }

    // TODO:
    //  Important: State.Delta is from outer package,
    //             I don't think it's good,
    //             Maybe change package structure?
    /**
     * @param deltaX horizontal move
     * @param deltaY vertical move
     * @return delta of the state generated by the change
     */
    public Delta move(int deltaX, int deltaY) {
        // TODO: Implement
        return null;
    }

    public Delta moveTo(int x, int y) {
        // TODO: Implement
        return null;
    }

    /**
     * @param change change in health, positive number increases health, negative - decreases
     * @return delta of the state generated by the change
     */
    public Delta health(int change) {
        // TODO: Implement
        return null;
    }


    public List<Item> getStash() {
        return stash;
    }

    public int getMaxHealth() {
        return maxHealth;
    }
    public int getHealth() {
        return health;
    }

    public int getStrength() {
        return strength;
    }

    public int getHorizontalPos() {
        return position.getX();
    }

    public int getVerticalPos() {
        return position.getY();
    }

    public Position getPosition() {
        return position;
    }

    public Strategy getStrategy() {
        return strategy;
    }
}
