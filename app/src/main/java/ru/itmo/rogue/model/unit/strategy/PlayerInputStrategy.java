package ru.itmo.rogue.model.unit.strategy;

import ru.itmo.rogue.model.state.StateView;
import ru.itmo.rogue.model.unit.Movement;
import ru.itmo.rogue.model.unit.UnitView;
import ru.itmo.rogue.model.updates.NoUpdate;
import ru.itmo.rogue.model.updates.StateUpdate;
import ru.itmo.rogue.model.updates.unit.PositionUpdate;

import java.util.ArrayDeque;
import java.util.Queue;

public class PlayerInputStrategy implements Strategy {

    /**
     * Queues Signal to later perform action
     * @param movement to be queued
     */
    public void queueAction(Movement movement) {
        queue.add(movement);
    }

    @Override
    public StateUpdate getAction(UnitView unit, StateView state) {
        var movement = queue.poll();
        if (movement == null) {
            return new NoUpdate();
        }

        return new PositionUpdate(unit, unit.getPosition().move(movement));
    }

    private final Queue<Movement> queue = new ArrayDeque<>();

}
