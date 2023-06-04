package ru.itmo.rogue.model.game.unit.items;

import ru.itmo.rogue.model.game.unit.Unit;
import ru.itmo.rogue.model.state.State;

public class StrengthStone implements Item {
    private int change = 1;

    public StrengthStone(int addition) {
        this.change = addition;
    }

    @Override
    public void apply(Unit unit, State state) {
        unit.changeStrength(change);
    }
}
