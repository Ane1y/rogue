package ru.itmo.rogue.model.game.unit.items;

import ru.itmo.rogue.model.game.unit.Unit;
import ru.itmo.rogue.model.state.Delta;
import ru.itmo.rogue.model.state.State;
import ru.itmo.rogue.model.state.UnitUpdate;

public class HealthStone implements Item {
    private final String name = "Healing Potion";
    private int change = 1;

    public HealthStone(){};
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
