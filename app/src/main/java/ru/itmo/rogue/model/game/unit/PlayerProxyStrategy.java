package ru.itmo.rogue.model.game.unit;

import ru.itmo.rogue.control.Signal;
import ru.itmo.rogue.model.state.State;

import java.util.ArrayDeque;
import java.util.Queue;

public class PlayerProxyStrategy implements Strategy {

    /**
     * Queues Signal to later perform action
     * @param signal
     */
    public void queueAction(Signal signal) {
        queue.add(signal);
    }

    @Override
    public Action getAction(Unit unit, State state) {
        var signal = queue.poll();
        if (signal == null) {
            return new Action(unit.position);
        }

        Movement movement = switch (signal) {
            case UP ->  Movement.UP;
            case DOWN -> Movement.DOWN;
            case LEFT -> Movement.LEFT;
            case RIGHT -> Movement.RIGHT;
            default -> Movement.NONE; // Neutral action
        };

        return new Action(unit.position.move(movement));
    }

    private final Queue<Signal> queue = new ArrayDeque<>();

}
