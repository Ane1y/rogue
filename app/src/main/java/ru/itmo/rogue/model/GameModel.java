package ru.itmo.rogue.model;

import ru.itmo.rogue.control.Signal;
import ru.itmo.rogue.model.state.State;
import ru.itmo.rogue.model.state.StateView;
import ru.itmo.rogue.model.unit.Movement;
import ru.itmo.rogue.view.View;

/**
 * Class that contains general Logic of the game that is active between the levels (Level, Unit and Item generation)
 * Class have control over the State of the game (as all Logic classes)
 */
public class GameModel implements Model {

    private final GameLogic gameLogic;
    private final LevelLogic levelLogic;
    private final State state;
    private final View view;

    /**
     * Creates Model instance
     * @param view associated view that will be updated whenever the model is updated
     *             (including constructor)
     * @throws RuntimeException originating from View::update(Delta)
     */
    public GameModel(View view) {
        this.view = view;
        state = new State();
        gameLogic = new GameLogic(state);
        levelLogic = new LevelLogic(gameLogic, state);
        this.view.update(state.copy());
    }

    @Override
    public void movePlayer(Movement movement) {
        levelLogic.movePlayer(movement);
        view.update(state.copy());
    }

    @Override
    public void usePlayerItem(int itemIndex) {
        levelLogic.usePlayerItem(itemIndex);
        view.update(state.copy());
    }

    @Override
    public StateView getState() {
        return state;
    }
}
