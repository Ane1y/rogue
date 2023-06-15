package ru.itmo.rogue.model.updates.unit;

import ru.itmo.rogue.model.state.State;
import ru.itmo.rogue.model.unit.UnitView;
import ru.itmo.rogue.model.updates.UnitUpdate;

/**
 * Changes unit's experience by some value
 */
public class ExperienceUpdate extends UnitUpdate {

    private final int delta;

    public ExperienceUpdate(UnitView unit, int delta) {
        super(unit);
        this.delta = delta;
    }

    @Override
    public void userApply(State state) {
        state.getUnitWithView(view).increaseExperience(delta);
    }
}
