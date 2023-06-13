package ru.itmo.rogue.model.updates.unit;

import ru.itmo.rogue.model.state.State;
import ru.itmo.rogue.model.unit.UnitView;
import ru.itmo.rogue.model.updates.UnitUpdate;

public class WipeExperience extends UnitUpdate {
    protected WipeExperience(UnitView view) {
        super(view);
    }

    @Override
    public void apply(State state) {
        state.getUnitWithView(view).wipeExperience();
    }
}
