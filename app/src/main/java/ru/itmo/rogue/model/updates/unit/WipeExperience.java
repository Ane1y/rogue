package ru.itmo.rogue.model.updates.unit;

import ru.itmo.rogue.model.state.State;
import ru.itmo.rogue.model.unit.UnitView;
import ru.itmo.rogue.model.updates.UnitUpdate;

/**
 * Completely erases unit's experience
 */
public class WipeExperience extends UnitUpdate {
    protected WipeExperience(UnitView view) {
        super(view);
    }

    @Override
    public void userApply(State state) {
        state.getUnitWithView(view).wipeExperience();
    }
}
