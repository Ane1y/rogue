package ru.itmo.rogue.model.game.unit.items;

import ru.itmo.rogue.model.game.unit.Unit;
import ru.itmo.rogue.model.state.Delta;
import ru.itmo.rogue.model.state.State;

public interface Item {

    // TODO: Define
    public Delta apply(Unit unit, State state);

    public String getName();

}
