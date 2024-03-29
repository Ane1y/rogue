package ru.itmo.rogue.model.unit.strategy;
import org.jetbrains.annotations.NotNull;
import ru.itmo.rogue.model.state.StateView;
import ru.itmo.rogue.model.unit.UnitView;
import ru.itmo.rogue.model.updates.NoUpdate;
import ru.itmo.rogue.model.updates.StateUpdate;

/**
 * Generates NoUpdates, strategy for not moving objects (chests) and dead units
 */
public class IdleStrategy implements Strategy {
    @Override
    public @NotNull StateUpdate getAction(UnitView unit, StateView state) {
        return new NoUpdate();
    }
}
