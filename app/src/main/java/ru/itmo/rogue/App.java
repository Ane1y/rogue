package ru.itmo.rogue;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.VirtualScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import ru.itmo.rogue.control.Controller;
import ru.itmo.rogue.control.KeyboardController;
import ru.itmo.rogue.model.GameModel;
import ru.itmo.rogue.model.Model;
import ru.itmo.rogue.view.DefaultView;
import ru.itmo.rogue.view.LanternaView;
import ru.itmo.rogue.view.View;

import java.io.IOException;

public class App {

    private final KeyboardController controller;
    private final GameModel model;
    private final DefaultView view;

    App() throws IOException {
        view = new DefaultView();
        model = new GameModel(view);
        controller = new KeyboardController(model, view);
    }

    void loop() {
        controller.loop();
        try {
            view.getScreen().stopScreen();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        System.out.println("Entry point");

        try {
            var app = new App();
            app.loop();
        } catch (IOException e) {
            System.err.println("Cant init screen");
        }
        // run
    }
}
