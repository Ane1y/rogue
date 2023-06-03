package ru.itmo.rogue;

import ru.itmo.rogue.control.Signal;
import ru.itmo.rogue.control.Controller;
import ru.itmo.rogue.control.KeyboardController;
import ru.itmo.rogue.model.GameModel;
import ru.itmo.rogue.model.Model;
import ru.itmo.rogue.model.state.Delta;
import ru.itmo.rogue.view.DummyView;
import ru.itmo.rogue.view.View;

public class App {

    private static final Controller controller;
    private static final Model model;
    private static final View view;

    static  {
        view = new DummyView(); // TODO: Replace
        model = new GameModel(view);
        controller = new KeyboardController(model);
    }

    public static void main(String[] args) {
        System.out.println("Entry point");

        // run
        controller.loop();
    }

}
