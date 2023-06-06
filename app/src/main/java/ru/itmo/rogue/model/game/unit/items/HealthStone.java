package ru.itmo.rogue.model.game.unit.items;

import ru.itmo.rogue.model.game.unit.Unit;
import ru.itmo.rogue.model.state.State;

public class HealthStone implements Item {
    private final String name = "Healing Potion";
    private int change = 1;

    public HealthStone(int addition) {
        this.change = addition;
    }

    @Override
    public void apply(Unit unit, State state) {
        unit.changeHealth(change);
    }

    @Override
    public String getName() {
        return name;
    }

}
