package ru.itmo.rogue.model;

import ru.itmo.rogue.control.Signal;
import ru.itmo.rogue.model.game.UnitFactory;
import ru.itmo.rogue.model.state.Delta;
import ru.itmo.rogue.model.state.State;

public class LevelLogic {
    private final State state;
    private final GameLogic gameLogic;
    public LevelLogic(GameLogic gameLogic, State state) {
        this.gameLogic = gameLogic;
        this.state = state;
    }

    public Delta update(Signal cmd) {
        var curPos = state.getPlayer().getPosition();
        if (state.getLevelMap().isExit(curPos)) {
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
        for (var unit : state.getUnits()) {
            var action = unit.getAction(state);
            var unitUpdate = state.getJudge().actionResult(unit, action,state);
            delta.add(unitUpdate);
        }
        return delta;
    }
}
