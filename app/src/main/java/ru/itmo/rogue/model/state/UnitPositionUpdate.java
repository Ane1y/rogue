package ru.itmo.rogue.model.state;

import ru.itmo.rogue.model.game.unit.Position;
import ru.itmo.rogue.model.game.unit.Unit;

import java.util.Objects;

public class UnitPositionUpdate extends UnitUpdate {

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

    public UnitPositionUpdate(Unit unit, Position position) {
        super(unit);
        this.oldPosition = position;
    }

    private final Position oldPosition;
}
