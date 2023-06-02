package ru.itmo.rogue.model;

import ru.itmo.rogue.control.Signal;
import ru.itmo.rogue.model.state.Delta;
import ru.itmo.rogue.model.state.State;
import ru.itmo.rogue.utils.AbstractSubscribable;

public class GameModel extends AbstractSubscribable<Delta> implements Model<Signal, Delta> {

    private final GameLogic gameLogic;
    private final LevelLogic levelLogic;
    private final InventoryLogic inventoryLogic;
    private final State state;


    public GameModel() {
        state = new State();
        gameLogic = new GameLogic(state);
        levelLogic = new LevelLogic(state);
        inventoryLogic = new InventoryLogic(state);
    }

    @Override
    public boolean update(Signal key) {
        var delta = switch (state.focus) {
            case GAME -> gameLogic.update(key);
            case LEVEL -> levelLogic.update(key);
            case INVENTORY -> inventoryLogic.update(key);
        };

        updatableList.forEach(u -> u.update(delta));
        return state.running;
    }
}
