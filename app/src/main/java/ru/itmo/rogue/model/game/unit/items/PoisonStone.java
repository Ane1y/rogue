package ru.itmo.rogue.model.game.unit.items;

import ru.itmo.rogue.model.game.unit.Unit;
import ru.itmo.rogue.model.state.State;

public class PoisonStone implements Item{

    @Override
    public void apply(Unit unit, State state) {
        unit.changeHealth(-unit.getHealth());
    }
}
