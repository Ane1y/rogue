package ru.itmo.rogue.model.game.unit;

import ru.itmo.rogue.control.Signal;
import ru.itmo.rogue.model.state.Map;
import ru.itmo.rogue.model.state.State;

import java.util.ArrayDeque;
import java.util.Queue;

public class PlayerProxyStrategy implements Strategy {

    public void queueAction(Signal signal) {
        queue.add(signal);
    }

    @Override
    public Action getAction(Unit unit, State state) {
        if (queue.isEmpty()) {
            return null; // TODO: Return neutral action
        }

        var signal = queue.poll();

        Position change = new Position();
        switch (signal) {
            case UP -> change.set(0, 1);
            case DOWN -> change.set(0, -1);
            case LEFT -> change.set(-1, 0);
            case RIGHT -> change.set(1, 0);
        }

        return new Action(state.player.move(change));
    }

    private final Queue<Signal> queue = new ArrayDeque<>();

}
