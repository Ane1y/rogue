package ru.itmo.rogue.model.updates;

import ru.itmo.rogue.model.state.State;


/**
 * StateUpdate is a postponed update of the state
 * It can be created in context where only immutable view for the State is available
 *      Then passed to the context where State is modifiable, and applied there
 */
public interface StateUpdate {

    /**
     * Applies postponed update to the state
     * @param state state that will be modified
     */
    void apply(State state);

}
