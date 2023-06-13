package ru.itmo.rogue.model.unit;

import ru.itmo.rogue.model.state.StateUpdate;

public abstract class UnitUpdate implements StateUpdate {
    protected Unit unit;

    UnitUpdate(Unit unit){
        this.unit = unit;
    }

}
