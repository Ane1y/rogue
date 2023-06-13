package ru.itmo.rogue.model.updates.unit;

import ru.itmo.rogue.model.state.State;
import ru.itmo.rogue.model.unit.UnitView;
import ru.itmo.rogue.model.updates.UnitUpdate;

public class StashClearUpdate extends UnitUpdate {
    public StashClearUpdate(UnitView unit) {
        super(unit);
    }

    @Override
    public void apply(State state) {
        state.getUnitWithView(view).getStash().clear();
    }
}
