package ru.itmo.rogue.model.updates.unit;

import ru.itmo.rogue.model.state.State;
import ru.itmo.rogue.model.unit.Position;
import ru.itmo.rogue.model.unit.UnitView;
import ru.itmo.rogue.model.updates.UnitUpdate;

public class CloneUnit extends UnitUpdate {
    private Position position;

    public CloneUnit(UnitView toSpawn, Position position) {
        super(toSpawn);
        this.position = position;
    }

    @Override
    protected void userApply(State state) {
        if (!state.getMap().isFloor(position)) {
            return;
        }

        if (state.getUnitWithPosition(position) != null) {
            return;
        }

        var unit = state.getUnitWithView(view).copy();
        unit.moveTo(position);
        state.addUnit(unit);
    }
}
