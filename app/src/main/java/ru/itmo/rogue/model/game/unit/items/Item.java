package ru.itmo.rogue.model.game.unit.items;

import ru.itmo.rogue.model.game.unit.Unit;
import ru.itmo.rogue.model.state.State;

public interface Item {

    // TODO: Define
    public void apply(Unit unit, State state);

    public String getName();

}
