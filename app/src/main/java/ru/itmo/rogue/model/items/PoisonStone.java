package ru.itmo.rogue.model.items;

import ru.itmo.rogue.model.state.StateView;
import ru.itmo.rogue.model.unit.UnitView;
import ru.itmo.rogue.model.updates.StateUpdate;
import ru.itmo.rogue.model.updates.unit.HealthUpdate;


/**
 * Item that kills unit that uses it
 */
public class PoisonStone implements Item {
    @Override
    public StateUpdate apply(UnitView unit, StateView state) {
        return new HealthUpdate(unit, Integer.MIN_VALUE);
    }

    @Override
    public String getName() {
        return "Poison";
    }
}
