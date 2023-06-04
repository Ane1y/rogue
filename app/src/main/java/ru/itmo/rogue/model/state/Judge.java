package ru.itmo.rogue.model.state;

import ru.itmo.rogue.model.game.unit.Action;
import ru.itmo.rogue.model.game.unit.Unit;
import ru.itmo.rogue.model.state.State;

public interface Judge {
    /**
     * @param unit
     * @param state
     * @return null if nothing
     */
    UnitUpdate actionResult(Unit unit, Action action, State state);
}
