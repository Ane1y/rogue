package ru.itmo.rogue.model.unit.strategy;

import org.jetbrains.annotations.NotNull;
import ru.itmo.rogue.model.state.StateView;
import ru.itmo.rogue.model.unit.Movement;
import ru.itmo.rogue.model.unit.UnitView;
import ru.itmo.rogue.model.updates.NoUpdate;
import ru.itmo.rogue.model.updates.StateUpdate;
import ru.itmo.rogue.model.updates.unit.PositionUpdate;

/**
 * Strategy that requires update from outside
 * Repeats queued movement until new update
 */
public class PlayerInputStrategy implements Strategy {

    /**
     * Queues movement to later supply as player's action
     * @param movement to be queued
     */
    public void setMovement(Movement movement) {
        this.movement = movement;
    }

    @Override
    public @NotNull StateUpdate getAction(UnitView unit, StateView state) {
        if (movement == null) {
            return new NoUpdate();
        }

        return new PositionUpdate(unit, unit.getPosition().move(movement));
    }

    private Movement movement = null;

}
