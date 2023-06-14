package ru.itmo.rogue.model.items;

import ru.itmo.rogue.model.state.StateView;
import ru.itmo.rogue.model.unit.Unit;
import ru.itmo.rogue.model.state.State;
import ru.itmo.rogue.model.unit.UnitView;
import ru.itmo.rogue.model.updates.StateUpdate;
import ru.itmo.rogue.model.updates.unit.StrengthUpdate;

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
    public StateUpdate apply(UnitView unit, StateView state) {
        return new StrengthUpdate(unit, change);
    }


    @Override
    public String getName() {
        return name + " " + change;
    }
}
