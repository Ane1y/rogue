package ru.itmo.rogue.model;

import ru.itmo.rogue.model.state.StateView;
import ru.itmo.rogue.model.unit.Movement;

public interface Model {

    void movePlayer(Movement movement);
    void usePlayerItem(int itemIndex);

    StateView getState();

}
