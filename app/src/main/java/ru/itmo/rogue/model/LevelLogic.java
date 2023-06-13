package ru.itmo.rogue.model;

import ru.itmo.rogue.control.Signal;
import ru.itmo.rogue.model.unit.UnitFactory;
import ru.itmo.rogue.model.unit.Unit;
import ru.itmo.rogue.model.unit.strategy.IdleStrategy;
import ru.itmo.rogue.model.state.State;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that contains Level Logic that is active while level is in focus
 * Class have control over the State of the game (as all Logic classes)
 */
public class LevelLogic {
    private final State state;
    private final GameLogic gameLogic;

    private final InventoryLogic inventoryLogic;

    /**
     * @param gameLogic GameLogic to call when new Level must be generated
     * @param inventoryLogic InventoryLogic to call when item transfer must happen
     * @param state state to control
     */
    public LevelLogic(GameLogic gameLogic, InventoryLogic inventoryLogic, State state) {
        this.gameLogic = gameLogic;
        this.inventoryLogic = inventoryLogic;
        this.state = state;
    }

    /**
     * Updates game state, queues player's action depending on the signal
     * Processes actions of all units
     * @param signal Signal from controller
     * @return delta that represents all changes made by the method
     */
    public Delta update(Signal signal) {
        // action of player
        switch (signal) {
            case UP, DOWN, LEFT, RIGHT -> UnitFactory
                    .getPlayerProxyStrategy()
                    .queueAction(signal);
        }
        var delta = new Delta();
        // action of enemies

        List<Unit> toDelete = new ArrayList<>();
        for (var unit : state.getUnits()) {
            var action = unit.getAction(state);
            var actionResult = state.getJudge().actionResult(unit, action, state);

            switch (actionResult) {
                case MOVE -> delta.add(unit.moveTo(action.dest()));
                case FIGHT -> {
                    var defender = state.getUnitByPosition(action.dest());
                    var healthDelta = state.getJudge().resolveFight(unit, defender);
                    if (healthDelta > 0) {
                        delta.add(defender.changeHealth(-healthDelta));
                    } else if (healthDelta < 0) {
                        delta.add(unit.changeHealth(healthDelta));
                    }
                    if (defender.isDead()) { // If defender is killed transfer all experience to attacker
                        unit.increaseExperience(defender.getExperience());
                        defender.wipeExperience();
                        defender.setStrategy(new IdleStrategy());
                    }
                }
                case MOVE_AND_COLLECT -> {
                    var source = state.getUnitByPosition(action.dest());
                    delta.append(inventoryLogic.transferItems(source, unit));
//                    state.getUnits().remove(source);
                    toDelete.add(source);
                    delta.add(unit.moveTo(action.dest()));
                }
            }
        }

        for (var d : toDelete) {
            state.getUnits().remove(d);
        }

        var playerPosition = state.getPlayer().getPosition();
        if (state.getLevelMap().isExit(playerPosition)) {
            return gameLogic.update(signal); // Return Map update instead of unit updates
        }

        return delta;
    }
}
