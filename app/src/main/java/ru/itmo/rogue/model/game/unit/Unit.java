package ru.itmo.rogue.model.game.unit;


import ru.itmo.rogue.model.game.unit.items.Item;
import ru.itmo.rogue.model.state.State;
import ru.itmo.rogue.model.state.UnitPositionUpdate;
import ru.itmo.rogue.model.state.UnitUpdate;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Unit {
    protected int maxHealth;
    protected int health;
    protected int strength;
    protected int experience;
    protected int level;
    protected List<Item> stash = new ArrayList<>();
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

    public UnitPositionUpdate moveTo(Position pos) {
        Position oldPos = position;
        position = pos;
        return new UnitPositionUpdate(this, oldPos);
    }

    public UnitPositionUpdate move(Movement movement) {
        Position oldPos = position;
        position = position.move(movement);
        return new UnitPositionUpdate(this, oldPos);
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

    public UnitUpdate increaseExperience(int change) {
        experience += change;
        return new UnitUpdate(this);
    }

    public UnitUpdate levelUp() {
        experience = 0;
        level += 1;
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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Unit unit = (Unit) o;
        return maxHealth == unit.maxHealth &&
                health == unit.health &&
                strength == unit.strength &&
                experience == unit.experience &&
                level == unit.level &&
                Objects.equals(stash, unit.stash) &&
                Objects.equals(position, unit.position) &&
                Objects.equals(strategy, unit.strategy);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                maxHealth,
                health,
                strength,
                experience,
                level,
                stash,
                position,
                strategy);
    }
}
