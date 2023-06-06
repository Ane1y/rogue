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

    public GameModel(View view) {
        this.view = view;
        state = new State();
        gameLogic = new GameLogic(state);
        levelLogic = new LevelLogic(gameLogic, state);
        inventoryLogic = new InventoryLogic(state);
    }

    @Override
    public boolean update(Signal key) {
        if (key == Signal.BACK) {
            state.changeFocus();
        }

        var delta = switch (state.focus) {
            case GAME -> gameLogic.update(key);
            case LEVEL -> levelLogic.update(key);
            case INVENTORY -> inventoryLogic.update(key);
        };

        if (delta == null) {
            delta = new Delta();
            delta.setFocus(state.focus);
        }

        return state.running && view.update(delta);
    }
}
