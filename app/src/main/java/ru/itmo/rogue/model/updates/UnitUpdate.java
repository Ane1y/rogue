package ru.itmo.rogue.model.updates;

import ru.itmo.rogue.model.state.State;
import ru.itmo.rogue.model.unit.UnitView;

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
