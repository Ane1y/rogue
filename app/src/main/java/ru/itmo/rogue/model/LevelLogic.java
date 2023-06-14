package ru.itmo.rogue.model;

import ru.itmo.rogue.model.unit.AbstractFactory;
import ru.itmo.rogue.model.unit.Movement;
import ru.itmo.rogue.model.unit.CompositeFactory;
import ru.itmo.rogue.model.state.State;
import ru.itmo.rogue.model.updates.StateUpdate;

import java.util.List;

/**
 * Class that contains Level Logic that is active while level is in focus
 * Class have control over the State of the game (as all Logic classes)
 */
public class LevelLogic {
    private final State state;
    private final GameLogic gameLogic;

    /**
     * @param gameLogic GameLogic to call when new Level must be generated
     * @param state state to control
     */
    public LevelLogic(GameLogic gameLogic, State state) {
        this.gameLogic = gameLogic;
        this.state = state;
    }

    /**
     * Updates game state, queues player's action depending on the signal
     * Processes actions of all units
     */
    public void movePlayer(Movement movement) {
        // Queue players action
        AbstractFactory.getPlayerStrategy().queueAction(movement);

        // Make decisions
        List<StateUpdate> updates = state.getUnits().stream()
                .map(unitView -> unitView.getAction(state))
                .toList();

        // Act
        updates.forEach(update -> update.apply(state));
        state.removeDeadEmptyUnits();

        if (state.getMap().isExit(state.getPlayer().getPosition())) {
            gameLogic.generateNewMap();
        }
    }

    public void usePlayerItem(int itemIndex) {
        var player = state.getPlayer();
        var stash = player.getStash();

        if (itemIndex >= stash.size()) {
            System.err.printf("Incorrect item index (%d/%d)%n", itemIndex, stash.size());
            return;
        }

        var item = stash.get(itemIndex);
        var effect = item.apply(state.getPlayer(), state);
        effect.apply(state);
        player.removeItem(itemIndex);
    }
}
