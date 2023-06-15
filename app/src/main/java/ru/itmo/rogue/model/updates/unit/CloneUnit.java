package ru.itmo.rogue.model.updates.unit;

import ru.itmo.rogue.model.state.State;
import ru.itmo.rogue.model.unit.Position;
import ru.itmo.rogue.model.unit.UnitView;
import ru.itmo.rogue.model.updates.UnitUpdate;

/**
 * Clones unit provided in the constructor and places it on provided position
 * If to-be-cloned unit is dead before application, unit won't be cloned (see UnitUpdate comment)
 */
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
