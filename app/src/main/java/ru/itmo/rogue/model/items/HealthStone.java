package ru.itmo.rogue.model.items;

import ru.itmo.rogue.model.unit.Unit;
import ru.itmo.rogue.model.state.State;

public class HealthStone implements Item {
    private final String name = "Healing Potion";
    private final int change;

    public HealthStone() {
        this(1);
    };
    public HealthStone(int addition) {
        this.change = addition;
    }

    @Override
    public Delta apply(Unit unit, State state) {
        return new Delta(unit.changeHealth(change));
    }

    @Override
    public String getName() {
        return name;
    }

}
