package ru.itmo.rogue.model.unit.strategy;

import ru.itmo.rogue.model.state.StateView;
import ru.itmo.rogue.model.unit.UnitView;
import ru.itmo.rogue.model.updates.StateUpdate;

public interface Strategy {

    /**
     * Generates an Action for provided unit
     * @param unit unit that will act
     * @param state state of the game
     * @return StateUpdate
     */
    StateUpdate getAction(UnitView unit, StateView state);

    /**
     * Useful when creating mobs with changing behaviour
     *  and creating temporary effects (confusion or speeding up)
     * @return new strategy
     */
    default Strategy nextStrategy(UnitView unit) {
        return this;
    }
}
