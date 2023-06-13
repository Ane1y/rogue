package ru.itmo.rogue.model.unit;

import ru.itmo.rogue.model.items.Item;
import ru.itmo.rogue.model.unit.strategy.Strategy;

import java.util.List;

public interface UnitView {

    int getHealth();

    int getMaxHealth();

    int getStrength();

    int getExperience();

    int getLevel();

    char getAliveChar();

    char getDeadChar();

    int getHorizontalPos();

    int getVerticalPos();

    Position getPosition();

    Strategy getStrategy();

    boolean isDead();

    List<Item> getStash();

}
