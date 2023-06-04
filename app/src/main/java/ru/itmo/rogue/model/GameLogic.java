package ru.itmo.rogue.model;

import ru.itmo.rogue.control.Signal;
import ru.itmo.rogue.model.game.UnitFactory;
import ru.itmo.rogue.model.state.Delta;
import ru.itmo.rogue.model.state.State;

public class GameLogic {

    private State state;
    public GameLogic(State state) {
        this.state = state;
    }

    public Delta update(Signal cmd) {
        // action of player
        switch (cmd) {
            case UP, DOWN, LEFT, RIGHT -> UnitFactory
                    .getPlayerProxyStrategy()
                    .queueAction(cmd);
        }

        // action of enemies
        for (var enemy : state.enemies) {
            var action = enemy.getAction(state);

        }
        return null;
    }
}
