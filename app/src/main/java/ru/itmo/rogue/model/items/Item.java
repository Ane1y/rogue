package ru.itmo.rogue.model.items;

import ru.itmo.rogue.model.unit.Unit;
import ru.itmo.rogue.model.state.State;
import ru.itmo.rogue.model.updates.StateUpdate;

import java.util.List;

public interface Item {

    /**
     * Applies effects of Item's use to the state
     * @param unit unit that used the item
     * @param state state
     * @return delta that reflects changes to the state made by the item
     */
    List<StateUpdate> apply(Unit unit, State state);

    /**
     * @return name of the item
     */
    String getName();
}
