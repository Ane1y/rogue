package ru.itmo.rogue.view;

import com.googlecode.lanterna.screen.Screen;
import ru.itmo.rogue.model.state.Delta;

public class LanternaView implements View {
    Screen screen;
    public LanternaView(Screen screen) {
        this.screen = screen;
    }

    @Override
    public boolean update(Delta data) {
        System.out.println("Display updated!");
        return true;
    }
}
