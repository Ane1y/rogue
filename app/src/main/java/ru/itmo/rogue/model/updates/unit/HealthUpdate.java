package ru.itmo.rogue.model.updates.unit;

import ru.itmo.rogue.model.state.State;
import ru.itmo.rogue.model.unit.UnitView;
import ru.itmo.rogue.model.updates.UnitUpdate;

public class HealthUpdate extends UnitUpdate {

    private final int delta;

    public HealthUpdate(UnitView unit, int delta) {
        super(unit);
        this.delta = delta;
    }

    @Override
    public void apply(State state) {
        state.getUnitWithView(view).changeHealth(delta);
    }
}
