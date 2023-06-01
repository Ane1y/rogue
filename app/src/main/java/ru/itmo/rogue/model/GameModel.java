package ru.itmo.rogue.model;

import ru.itmo.rogue.control.Control;
import ru.itmo.rogue.utils.Updatable;

import java.util.ArrayList;
import java.util.List;

public class GameModel extends AbstractModel<Control.Signal, State.Delta> {

    private final GameLogic gameLogic;
    private final LevelLogic levelLogic;
    private final InventoryLogic inventoryLogic;
    private final State state = new State();

    private final List<Updatable<State.Delta>> updatables = new ArrayList<>();


    public GameModel() {
        gameLogic = new GameLogic(state);
        levelLogic = new LevelLogic(state);
        inventoryLogic = new InventoryLogic(state);
    }

    @Override
    public boolean update(Control.Signal key) {
        var ret = switch (state.focus) {
            case GAME -> gameLogic.update(key);
            case LEVEL -> levelLogic.update(key);
            case INVENTORY -> inventoryLogic.update(key);
        };

        updatables.forEach(u -> u.update(state.getDelta()));
        state.clearDelta();

        return ret;
    }
}
