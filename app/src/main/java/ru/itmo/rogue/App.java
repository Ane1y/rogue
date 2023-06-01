package ru.itmo.rogue;

import ru.itmo.rogue.control.Control;
import ru.itmo.rogue.control.Controller;
import ru.itmo.rogue.control.KeyboardController;
import ru.itmo.rogue.model.GameModel;
import ru.itmo.rogue.model.Model;
import ru.itmo.rogue.model.State;
import ru.itmo.rogue.view.DummyView;
import ru.itmo.rogue.view.View;

public class App {

    private static Controller<Control.Signal> controller;
    private static Model<Control.Signal, State.Delta> model;
    private static View<State.Delta> view;

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
