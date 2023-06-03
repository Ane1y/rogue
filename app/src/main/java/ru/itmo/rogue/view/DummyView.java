package ru.itmo.rogue.view;

import ru.itmo.rogue.model.state.Delta;

public class DummyView implements View {
    @Override
    public boolean update(Delta data) {
        System.out.println("Display updated!");
        return true;
    }
}
