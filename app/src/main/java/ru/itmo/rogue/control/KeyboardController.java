package ru.itmo.rogue.control;


import ru.itmo.rogue.utils.Updatable;

import java.util.ArrayList;
import java.util.List;

public class KeyboardController implements Controller<Control.Signal> {

    // TODO: Implement

    private final List<Updatable<Control.Signal>> updatableList = new ArrayList<>();

    @Override
    public boolean subscribe(Updatable<Control.Signal> updatable) {
        return updatableList.add(updatable);
    }

    @Override
    public void loop() {
        // TODO: Implement
    }
}
