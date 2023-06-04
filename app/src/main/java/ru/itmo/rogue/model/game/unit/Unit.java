package ru.itmo.rogue.model.game.unit;


import ru.itmo.rogue.model.game.unit.items.Item;
import ru.itmo.rogue.model.state.Delta;
import ru.itmo.rogue.model.state.State;
import ru.itmo.rogue.model.state.UnitUpdate;

import java.util.List;

public class Unit {
    protected int maxHealth;
    protected int health;
    protected int strength;
    protected int experience;
    protected int level;
    protected List<Item> stash;
    protected Position position;
    protected Strategy strategy;

    public Unit(int maxHealth, int strength, int experience, int level, Position position, Strategy strategy) {
        this.maxHealth = maxHealth;
        this.health = maxHealth;
        this.experience = experience;
        this.level = level;
        this.strength = strength;
        this.position = position;
        this.strategy = strategy;
    }

    public Action getAction(State state) {
        return strategy.getAction(this, state);
    }

    /**
     * @param deltaX horizontal move
     * @param deltaY vertical move
     * @return delta of the state generated by the change
     */
    public Position move(int deltaX, int deltaY) {
        position.set(position.getX() + deltaX,
                position.getY() + deltaY);
        return position;
    }

    public Position moveTo(int x, int y) {
        position.set(x, y);
        return position;
    }

    public Position moveTo(Position pos) {
        return moveTo(pos.getX(), pos.getY());
    }

    public Position move(Position pos) {
        return moveTo(pos.getX(), pos.getY());
    }

    /**
     * @param change change in health, positive number increases health, negative - decreases
     * @return delta of the state generated by the change
     */
    public UnitUpdate changeHealth(int change) {
        health = (-change >= health) ? 0 : health + change;
        return new UnitUpdate(this);
    }

    public UnitUpdate changeStrength(int change) {
        strength += change;
        return new UnitUpdate(this);
    }

    public boolean isDead() {
        return health == 0 && !stash.isEmpty();
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

    public int getExperience() {
        return experience;
    }

    public int getLevel() {
        return level;
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
