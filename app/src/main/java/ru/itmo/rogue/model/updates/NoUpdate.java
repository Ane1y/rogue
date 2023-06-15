package ru.itmo.rogue.model.updates;

import ru.itmo.rogue.model.state.State;


/**
 * Dummy update for situations where not null update must be returned, but no action is needed
 */
public class NoUpdate implements StateUpdate {
    @Override
    public void apply(State state) {}
}
