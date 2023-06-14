package ru.itmo.rogue.model.updates;

import ru.itmo.rogue.model.state.State;

public class NoUpdate implements StateUpdate {
    @Override
    public void apply(State state) {}
}
