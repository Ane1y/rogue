package ru.itmo.rogue.model.unit.strategy;

import ru.itmo.rogue.model.state.State;
import ru.itmo.rogue.model.unit.Action;
import ru.itmo.rogue.model.unit.Unit;

public interface Strategy {

    /**
     * Generates an Action for provided unit
     * @param unit unit that will act
     * @param state state of the game
     * @return Action
     */
    Action getAction(Unit unit, State state);

    /**
     * Useful when creating mobs with changing behaviour
     *  and creating temporary effects (confusion or speeding up)
     * @return new strategy
     */
    default Strategy nextStrategy() {
        return this;
    }
}
