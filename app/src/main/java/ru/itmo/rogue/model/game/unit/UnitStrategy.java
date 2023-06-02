package ru.itmo.rogue.model.game.unit;

import ru.itmo.rogue.model.state.State;

public interface UnitStrategy {

    /**
     * Generates an Action for provided unit
     * @param unit unit that will act
     * @param state state of the game
     * @return Action
     */
    Action getAction(Unit unit, State state);

}
