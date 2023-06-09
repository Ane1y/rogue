package ru.itmo.rogue.model.state;

import ru.itmo.rogue.model.game.unit.Unit;

import java.util.Objects;


/**
 * Update of some unit's statistics that should be reflected in the View
 */
public class UnitUpdate {

    /**
     * @param unit unit whose stats were changed, contains information about current position
     */
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
