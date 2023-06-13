package ru.itmo.rogue.model.game.unit.items;

import ru.itmo.rogue.model.game.unit.Unit;
import ru.itmo.rogue.model.state.Delta;
import ru.itmo.rogue.model.state.State;
import ru.itmo.rogue.model.state.UnitUpdate;

public class StrengthStone implements Item {
    private final String name = "Strength Potion";

    private final int change;
    public StrengthStone(){
        this.change = -1;
    };

    public StrengthStone(int addition) {
        this.change = addition;
    }

    @Override
    public Delta apply(Unit unit, State state) {
        return new Delta(unit.changeStrength(change));
    }


    @Override
    public String getName() {
        return name;
    }
}
