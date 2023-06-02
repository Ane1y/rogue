package ru.itmo.rogue.model;

import ru.itmo.rogue.control.Signal;
import ru.itmo.rogue.model.state.Delta;
import ru.itmo.rogue.model.state.State;

public class LevelLogic {

    private State state;

    public LevelLogic(State state) {
        this.state = state;
    }

    public Delta update(Signal data) {
        return null;
    }
}
