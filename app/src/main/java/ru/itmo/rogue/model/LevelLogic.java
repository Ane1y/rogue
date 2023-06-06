package ru.itmo.rogue.model;

import ru.itmo.rogue.control.Signal;
import ru.itmo.rogue.model.game.UnitFactory;
import ru.itmo.rogue.model.state.Delta;
import ru.itmo.rogue.model.state.State;

public class LevelLogic {
    private State state;
    private final GameLogic gameLogic;
    public LevelLogic(GameLogic gameLogic, State state) {
        this.gameLogic = gameLogic;
        this.state = state;
    }

    public Delta update(Signal cmd) {
        var curPos = state.player.getPosition();
        if (state.levelMap.isExit(curPos)) {
            return gameLogic.update(cmd);
        }
        // action of player
        switch (cmd) {
            case UP, DOWN, LEFT, RIGHT -> UnitFactory
                    .getPlayerProxyStrategy()
                    .queueAction(cmd);
        }
        var delta = new Delta();
        // action of enemies
        for (var enemy : state.units) {
            var action = enemy.getAction(state);
            var unitUpdate = state.rdj.actionResult(enemy, action,state);
            delta.add(unitUpdate);
        }
        return delta;
    }
}
