package ru.itmo.rogue.model.items;

import ru.itmo.rogue.model.state.StateView;
import ru.itmo.rogue.model.unit.UnitView;
import ru.itmo.rogue.model.updates.StateUpdate;
import ru.itmo.rogue.model.updates.unit.HealthUpdate;

import java.util.List;

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
    public StateUpdate apply(UnitView unit, StateView state) {
        return new HealthUpdate(unit, change);
    }

    @Override
    public String getName() {
        return name + " " + change;
    }

}
