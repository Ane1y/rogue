package ru.itmo.rogue.model.items;

import ru.itmo.rogue.model.unit.Unit;
import ru.itmo.rogue.model.state.State;

public class PoisonStone implements Item {
    private final String name = "Poison";


    @Override
    public Delta apply(Unit unit, State state) {
        return new Delta(unit.changeHealth(-unit.getHealth()));
    }

    @Override
    public String getName() {
        return name;
    }
}
