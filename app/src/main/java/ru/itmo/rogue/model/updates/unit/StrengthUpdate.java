package ru.itmo.rogue.model.updates.unit;

import ru.itmo.rogue.model.state.State;
import ru.itmo.rogue.model.unit.Unit;
import ru.itmo.rogue.model.updates.UnitUpdate;

public class StrengthUpdate  extends UnitUpdate {

    private final int delta;

    public StrengthUpdate(Unit unit, int delta) {
        super(unit);
        this.delta = delta;
    }

    @Override
    public void apply(State state) {
        state.getUnitWithView(view).changeStrength(delta);
    }
}

