package ru.itmo.rogue.model.state;

import ru.itmo.rogue.model.game.unit.Unit;

import java.util.Objects;

public class UnitHealthChange extends UnitChange {
    public UnitHealthChange(Unit.Position position, int healthDelta) {
        super(position);
        this.healthDelta = healthDelta;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UnitHealthChange that)) return false;
        if (!super.equals(o)) return false;
        if (!that.canEquals(this)) return false;
        return Objects.equals(healthDelta, that.healthDelta);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), healthDelta);
    }

    @Override
    public boolean canEquals(UnitChange that) {
        return super.canEquals(that);
    }

    public int getHealthDelta() {
        return healthDelta;
    }

    private final int healthDelta;

}
