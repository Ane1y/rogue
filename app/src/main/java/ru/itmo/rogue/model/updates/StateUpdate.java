package ru.itmo.rogue.model.updates;

import ru.itmo.rogue.model.state.State;

public interface StateUpdate {

    void apply(State state);

}
