package ru.itmo.rogue.model.state;

import ru.itmo.rogue.model.game.unit.Position;
import ru.itmo.rogue.model.game.unit.Unit;

import java.util.Objects;

/**
 * Update of some unit's position that should be reflected in the View
 */
public class UnitPositionUpdate extends UnitUpdate {
    /**
     * @param unit unit that was moved, contains information about current position
     * @param oldPosition old position of the unit
     */
    public UnitPositionUpdate(Unit unit, Position oldPosition) {
        super(unit);
        this.oldPosition = oldPosition;
    }

    /**
     * @return old position of the unit
     */
    public Position getOldPosition() {
        return oldPosition;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UnitPositionUpdate that)) return false;
        if (!that.canEquals(this)) return false;
        if (!super.equals(o)) return false;
        return Objects.equals(oldPosition, that.oldPosition);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), oldPosition);
    }

    @Override
    public boolean canEquals(UnitUpdate that) {
        return that instanceof UnitPositionUpdate;
    }

    private final Position oldPosition;
}
