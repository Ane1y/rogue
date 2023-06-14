package ru.itmo.rogue.model.unit.strategy;

import org.jetbrains.annotations.NotNull;
import ru.itmo.rogue.model.state.StateView;
import ru.itmo.rogue.model.unit.UnitView;
import ru.itmo.rogue.model.updates.StateUpdate;

public interface Strategy {

    /**
     * Generates an Action for provided unit
     * @param unit unit that will act
     * @param state state of the game
     * @return StateUpdate
     */
    @NotNull
    StateUpdate getAction(UnitView unit, StateView state);

    /**
     * Useful when creating mobs with changing behaviour
     * and creating temporary effects (confusion or speeding up)
     *
     * @return new strategy
     */
    default @NotNull Strategy nextStrategy(UnitView unit) {
        return this;
    }
}
