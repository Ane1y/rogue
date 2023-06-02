package ru.itmo.rogue.model.game.unit;

import ru.itmo.rogue.model.state.State;

import java.util.ArrayDeque;
import java.util.Queue;

public class PlayerProxyStrategy implements UnitStrategy {

    public void queueAction(Action action) {
        queue.add(action);
    }

    @Override
    public Action getAction(Unit unit, State state) {
        if (queue.isEmpty()) {
            return null; // TODO: Return neutral action
        }

        var action = queue.poll();
        // TODO: Check if action is possible
        //  Maybe we should make this check externally, hmm
        return action;
    }

    private Queue<Action> queue = new ArrayDeque<>();

}
