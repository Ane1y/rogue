package ru.itmo.rogue.view;

import ru.itmo.rogue.model.State;

public class DummyView implements View<State.Delta> {
    @Override
    public boolean update(State.Delta data) {
        System.out.println("Display updated!");
        return true;
    }
}
