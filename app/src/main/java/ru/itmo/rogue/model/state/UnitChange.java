package ru.itmo.rogue.model.state;

import ru.itmo.rogue.model.game.unit.Unit;

import java.util.Objects;

public class UnitChange {

    public UnitChange(Unit.Position previousPosition) {
        this.previousPosition = previousPosition;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UnitChange that)) return false;
        if (!that.canEquals(this)) return false;
        return Objects.equals(previousPosition, that.previousPosition);
    }

    @Override
    public int hashCode() {
        return Objects.hash(previousPosition);
    }
    public boolean canEquals(UnitChange that) {
        return true;
    }

    public Unit.Position getPreviousPosition() {
        return previousPosition;
    }

    private final Unit.Position previousPosition;

}
