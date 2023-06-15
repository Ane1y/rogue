package ru.itmo.rogue.model.updates.unit;

import ru.itmo.rogue.model.state.State;
import ru.itmo.rogue.model.unit.Unit;
import ru.itmo.rogue.model.unit.UnitView;
import ru.itmo.rogue.model.updates.UnitUpdate;


/**
 * Changes strength value of the unit by some delta
 */
public class StrengthUpdate  extends UnitUpdate {

    private final int delta;

    public StrengthUpdate(UnitView unit, int delta) {
        super(unit);
        this.delta = delta;
    }

    @Override
    public void userApply(State state) {
        state.getUnitWithView(view).changeStrength(delta);
    }
}

