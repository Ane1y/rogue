package ru.itmo.rogue.model.game.unit.items;

import ru.itmo.rogue.model.game.unit.Unit;
import ru.itmo.rogue.model.state.Delta;
import ru.itmo.rogue.model.state.State;
import ru.itmo.rogue.model.state.UnitUpdate;

public class StrengthStone implements Item {
    private final String name = "Strength Potion";

    private int change = 1;
    public StrengthStone(){};

    public StrengthStone(int addition) {
        this.change = addition;
    }

    @Override
    public Delta apply(Unit unit, State state) {
        Delta delta = new Delta();
        unit.changeStrength(change);
        delta.add(new UnitUpdate(unit));
        return delta;
    }


    @Override
    public String getName() {
        return name;
    }
}
