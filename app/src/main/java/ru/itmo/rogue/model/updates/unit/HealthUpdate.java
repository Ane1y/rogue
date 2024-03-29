package ru.itmo.rogue.model.updates.unit;

import ru.itmo.rogue.model.state.State;
import ru.itmo.rogue.model.unit.UnitView;
import ru.itmo.rogue.model.updates.UnitUpdate;

/**
 * Changes unit's health by some value
 */
public class HealthUpdate extends UnitUpdate {

    private final int delta;

    public HealthUpdate(UnitView unit, int delta) {
        super(unit);
        this.delta = delta;
    }

    @Override
    public void userApply(State state) {
        state.getUnitWithView(view).changeHealth(delta);
    }
}
