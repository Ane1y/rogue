package ru.itmo.rogue.model.unit;


import org.jetbrains.annotations.NotNull;
import ru.itmo.rogue.model.items.Item;
import ru.itmo.rogue.model.state.StateView;
import ru.itmo.rogue.model.unit.strategy.Strategy;
import ru.itmo.rogue.model.updates.NoUpdate;
import ru.itmo.rogue.model.updates.StateUpdate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;


/**
 * Unit stores all information that defines any Unit's identity and behaviour
 */
public class Unit implements UnitView {
    public static int MAX_STASH_SIZE = 31;

    protected int maxHealth;
    protected int health;
    protected int strength;
    protected int experience;
    protected int level;
    protected List<Item> stash = new ArrayList<>();
    protected Position position;
    protected Strategy strategy;

    public int levelUpCondition() {
        return (int) Math.ceil(Math.log(level + 1) * 10);
    }

    private int levelUpStrengthBonus() {
        return 2;
    }

    private int levelUpHealthBonus() {
        return 2;
    }

    private char aliveChar;

    private char deadChar;


    public Unit(int maxHealth,
                int strength,
                int experience,
                int level,
                @NotNull Position position,
                @NotNull Strategy strategy,
                char aliveChar, char deadChar) {
        this.maxHealth = maxHealth;
        this.health = maxHealth;
        this.experience = experience;
        this.level = level;
        this.strength = strength;
        this.position = position;
        this.strategy = strategy;
        this.aliveChar = aliveChar;
        this.deadChar = deadChar;
    }

    public void setStrategy(@NotNull Strategy strategy) {
        this.strategy = strategy;
    }

    public void wipeExperience() {
        this.experience = 0;
        this.level = 0;
    }

    @Override
    public StateUpdate getAction(@NotNull StateView state) {
        if (isDead() || this.strategy == null) {
            return new NoUpdate();
        }
        this.strategy = strategy.nextStrategy(this);
        return strategy.getAction(this, state);
    }

    public void moveTo(@NotNull Position pos) {
        position = pos;
    }

    public void move(@NotNull Movement movement) {
        position = position.move(movement);
    }

    /**
     * @param change change in health, positive number increases health,
     *               negative - decreases
     */
    public void changeHealth(int change) {
        health += change;
        health = Math.max(Math.min(maxHealth, health), 0);
    }

    public void changeStrength(int change) {
        strength = (-change >= strength) ? 0 : strength + change;
    }

    public void increaseExperience(int change) {
        experience += change;
        if (experience >= levelUpCondition()) {
            levelUp();
        }
    }

    public void levelUp() {
        experience = 0;
        level += 1;

        maxHealth += levelUpHealthBonus();
        health += levelUpHealthBonus();
        strength += levelUpStrengthBonus();
    }

    public void addItem(@NotNull Item item) {
        if (stash.size() >= MAX_STASH_SIZE) {
            return;
        }

        if (stash.isEmpty()) {
            stash.add(item);
        } else {
            stash.add(stash.size() - 1, item);
        }
    }

    public void removeItem(Item item) {
        stash.remove(item);
    }

    public void removeItem(int itemIndex) {
        stash.remove(itemIndex);
    }

    public void removeAllItems() {
        stash.clear();
    }

    public void setAliveChar(char aliveChar) {
        this.aliveChar = aliveChar;
    }

    public void setDeadChar(char deadChar) {
        this.deadChar = deadChar;
    }

    public boolean isDead() {
        return health < 1;
    }

    public List<Item> getStash() {
        return Collections.unmodifiableList(stash);
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

    public char getAliveChar() {
        return aliveChar;
    }

    public char getDeadChar() {
        return deadChar;
    }

    /**
     * Creates a copy of the Unit
     * IMPORTANT:
     *      Created unit SHARES Position and Strategy with the original
     * @return copied unit
     */
    public Unit copy() {
        var copy = new Unit(maxHealth,
                strength,
                experience,
                level,
                position,
                strategy,
                aliveChar,
                deadChar);
        copy.health = health;
        copy.stash = new ArrayList<>(stash);
        return copy;
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
                deadChar == unit.deadChar &&
                aliveChar == unit.aliveChar &&
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
                deadChar,
                aliveChar,
                stash,
                position,
                strategy);
    }
}
