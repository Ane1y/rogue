package ru.itmo.rogue.model;

import ru.itmo.rogue.control.Control;
import ru.itmo.rogue.utils.Updatable;

public class GameLogic extends AbstractModel<Control.Signal, State.Delta> {

    private State state;

    public GameLogic(State state) {
        this.state = state;
    }

    @Override
    public boolean update(Control.Signal data) {
        // TODO: Implement
        return false;
    }
}
