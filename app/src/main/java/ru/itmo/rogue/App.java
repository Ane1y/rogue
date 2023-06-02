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

    private static final Controller<Signal> controller;
    private static final Model<Signal, Delta> model;
    private static final View<Delta> view;

    static  {
        controller = new KeyboardController();
        model = new GameModel();
        view = new DummyView(); // TODO: Replace
    }

    public static void main(String[] args) {
        System.out.println("Entry point");

        // init MVC
        controller.subscribe(model);
        model.subscribe(view);

        // run
        controller.loop();
    }

}
