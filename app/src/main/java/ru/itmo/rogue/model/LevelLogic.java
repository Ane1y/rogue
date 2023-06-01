package ru.itmo.rogue.model;

import ru.itmo.rogue.control.Control;
import ru.itmo.rogue.utils.Updatable;

public class LevelLogic extends AbstractModel<Control.Signal, State.Delta> {

    private State state;

    public LevelLogic(State state) {
        this.state = state;
    }

    @Override
    public boolean update(Control.Signal data) {
        return false;
    }
}
