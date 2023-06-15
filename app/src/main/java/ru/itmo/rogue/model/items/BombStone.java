package ru.itmo.rogue.model.items;

import ru.itmo.rogue.model.state.StateView;
import ru.itmo.rogue.model.unit.UnitView;
import ru.itmo.rogue.model.updates.unit.AttackUpdate;
import ru.itmo.rogue.model.updates.CompositeUpdate;
import ru.itmo.rogue.model.updates.StateUpdate;

public class BombStone implements Item {
    private final String name = "Bomb";
    private final int change;

    public BombStone() {
        this(1);
    };

    public BombStone(int change) {
        this.change = change;
    }

    @Override
    public StateUpdate apply(UnitView unit, StateView state) {
        return state.getUnits().stream()
                .filter(other -> other != unit) // ID Comparison
                .map(other -> new AttackUpdate(unit, other, change))
                .collect(CompositeUpdate.collector());
    }

    @Override
    public String getName() {
        return name + " " + change + "dmg";
    }
}
