package ru.itmo.rogue.model.unit.strategy;

import org.jetbrains.annotations.NotNull;
import ru.itmo.rogue.model.unit.UnitView;

public class ChangeableCowardStrategy extends CowardStrategy {

    private static final int MAX_HEALTH = 5;

    @Override
    public @NotNull Strategy nextStrategy(UnitView unit) {
        if (unit.getHealth() > MAX_HEALTH) {
            return new ChangeableAggressiveStrategy();
        }

        return this;
    }
}
