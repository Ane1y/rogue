package ru.itmo.rogue.model.unit.strategy;

import org.jetbrains.annotations.NotNull;
import ru.itmo.rogue.model.unit.UnitView;

/**
 * Behaviour copies AggressiveStrategy, but when health is low will return
 *  *CowardStrategy as next strategy
 */
public class ChangeableAggressiveStrategy extends AgressiveStrategy {
    private final static int MIN_HEALTH = 2;

    @Override
    public @NotNull Strategy nextStrategy(UnitView unit) {
        int unitHealth = unit.getHealth();
        if (unitHealth < MIN_HEALTH) {
            return new ChangeableCowardStrategy();
        }
        return this;
    }
}
