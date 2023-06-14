package ru.itmo.rogue.model;

import ru.itmo.rogue.control.Signal;
import ru.itmo.rogue.model.state.StateView;
import ru.itmo.rogue.model.unit.Movement;
import ru.itmo.rogue.utils.Updatable;

public interface Model {

    void movePlayer(Movement movement);
    void usePlayerItem(int itemIndex);

    StateView getState();

}
