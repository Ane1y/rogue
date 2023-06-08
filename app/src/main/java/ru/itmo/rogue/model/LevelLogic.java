package ru.itmo.rogue.model;

import ru.itmo.rogue.control.Signal;
import ru.itmo.rogue.model.game.UnitFactory;
import ru.itmo.rogue.model.state.Delta;
import ru.itmo.rogue.model.state.State;
import ru.itmo.rogue.model.state.UnitPositionUpdate;

public class LevelLogic {
    private final State state;
    private final GameLogic gameLogic;

    private final InventoryLogic inventoryLogic;

    public LevelLogic(GameLogic gameLogic, InventoryLogic inventoryLogic, State state) {
        this.gameLogic = gameLogic;
        this.inventoryLogic = inventoryLogic;
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
            var actionResult = state.getJudge().actionResult(unit, action, state);

             switch (actionResult) {
                 case MOVE -> {
                     var oldPos = unit.getPosition();
                     unit.moveTo(action.dest());
                     delta.add(new UnitPositionUpdate(unit, oldPos));
                 }
                 case FIGHT -> {
                     var defender = state.getUnitOnPosition(action.dest());
                     var healthDelta = state.getJudge().resolveFight(unit, defender);
                     if (healthDelta > 0) {
                         delta.add(defender.changeHealth(-healthDelta));
                     } else if (healthDelta < 0) {
                         delta.add(unit.changeHealth(healthDelta));
                     }
                 }
                 case MOVE_AND_COLLECT -> {
                     var source = state.getUnitOnPosition(action.dest());
                     delta.append(inventoryLogic.transferItems(source, unit));
                 }
             }

        }
        return delta;
    }
}
