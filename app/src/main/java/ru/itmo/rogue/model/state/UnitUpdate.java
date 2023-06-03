package ru.itmo.rogue.model.state;

import ru.itmo.rogue.model.game.unit.Unit;

import java.util.Objects;

public class UnitUpdate {

    public UnitUpdate(Unit unit) {
        this.unit = unit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UnitUpdate that)) return false;
        if (!that.canEquals(this)) return false;
        return Objects.equals(unit, that.unit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(unit);
    }
    public boolean canEquals(UnitUpdate that) {
        return true;
    }

    public Unit getUnit() {
        return unit;
    }

    private final Unit unit;

}
