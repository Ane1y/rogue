package ru.itmo.rogue.model.game.unit.items;

import ru.itmo.rogue.model.game.unit.Unit;
import ru.itmo.rogue.model.state.Delta;
import ru.itmo.rogue.model.state.State;

public interface Item {

    /**
     * Applies effects of Item's use to the state
     * @param unit unit that used the item
     * @param state state
     */
    Delta apply(Unit unit, State state);

    /**
     * @return name of the item
     */
    String getName();

}
