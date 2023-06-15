package ru.itmo.rogue.model.updates.unit;

import ru.itmo.rogue.model.state.State;
import ru.itmo.rogue.model.unit.UnitView;
import ru.itmo.rogue.model.updates.UnitUpdate;

/**
 * Clears unit's stash (inventory)
 * IMPORTANT: Won't be applied on dead unit (see UnitUpdate comment), so it's unfit to clear bodies from the map
 */
public class StashClearUpdate extends UnitUpdate {
    public StashClearUpdate(UnitView unit) {
        super(unit);
    }

    @Override
    public void userApply(State state) {
        state.getUnitWithView(view).getStash().clear();
    }
}
