package ru.itmo.rogue.model.state;

import ru.itmo.rogue.model.game.unit.Unit;

import java.util.Objects;

public class UnitMoved extends UnitChange {
    public UnitMoved(Unit.Position previousPosition, Unit.Position newPosition) {
        super(previousPosition);
        this.newPosition = newPosition;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UnitMoved unitMoved)) return false;
        if (!super.equals(o)) return false;
        if (!unitMoved.canEquals(this)) return false;
        return Objects.equals(newPosition, unitMoved.newPosition);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), newPosition);
    }

    @Override
    public boolean canEquals(UnitChange that) {
        return that instanceof UnitMoved;
    }

    public Unit.Position getNewPosition() {
        return newPosition;
    }

    private final Unit.Position newPosition;


}
