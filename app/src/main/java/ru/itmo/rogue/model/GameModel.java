package ru.itmo.rogue.model;

import ru.itmo.rogue.control.Signal;
import ru.itmo.rogue.model.state.Delta;
import ru.itmo.rogue.model.state.State;
import ru.itmo.rogue.view.View;

public class GameModel implements Model {

    private final GameLogic gameLogic;
    private final LevelLogic levelLogic;
    private final InventoryLogic inventoryLogic;
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
        inventoryLogic = new InventoryLogic(state);

        var delta = gameLogic.defaultMap();
        delta.append(state.setFocus(State.Focus.LEVEL));

        this.view.update(delta);
    }

    @Override
    public boolean update(Signal key) {
        Delta delta;
        if (key.equals(Signal.BACK)) {
            delta = state.toggleFocus();
        } else {
            delta = switch (state.getFocus()) {
                case LEVEL -> levelLogic.update(key);
                case INVENTORY -> inventoryLogic.update(key);
            };
        }

        if (delta == null) { // TODO: Remove when NotNull guarantee is in place
            delta = new Delta();
        }

        return state.running() && view.update(delta);
    }
}
