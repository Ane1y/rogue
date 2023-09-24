package ru.itmo.rogue.model.updates;

import ru.itmo.rogue.model.state.State;
import ru.itmo.rogue.model.unit.UnitView;


/**
 * Base class for Unit updates
 * Classes that inherit this class must implement userApply(State) method [it should've been called unitApply ,_,]
 *      userApply will be executed only if targeted unit (passed to the constructor) is alive
 * Class exposes `view` variable to its children classes
 *
 * If class that inherits UnitUpdate describes interaction between units,
 *  then unit that initiates this interaction SHOULD be passed to the constructor of UnitUpdate
 *
 */
public abstract class UnitUpdate implements StateUpdate {
    protected UnitView view;

    protected UnitUpdate(UnitView view){
        this.view = view;
    }

    @Override
    public void apply(State state) {
        if (!view.isDead()) {
            this.userApply(state);
        }
    }

    abstract protected void userApply(State state);
}
