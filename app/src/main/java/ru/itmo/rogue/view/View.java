package ru.itmo.rogue.view;

import ru.itmo.rogue.model.state.StateView;
import ru.itmo.rogue.utils.Updatable;

public interface View extends Updatable<StateView> {

    enum Focus {
        LEVEL, INVENTORY
    }

    Focus toggleFocus();
    void setFocus(Focus focus);
    Focus getFocus();

}
