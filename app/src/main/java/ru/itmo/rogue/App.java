package ru.itmo.rogue;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.VirtualScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import ru.itmo.rogue.control.Controller;
import ru.itmo.rogue.control.KeyboardController;
import ru.itmo.rogue.model.GameModel;
import ru.itmo.rogue.model.Model;
import ru.itmo.rogue.view.LanternaView;
import ru.itmo.rogue.view.View;

import java.io.IOException;

public class App {

    private final Controller controller;
    private final Model model;
    private final View view;
    private final Screen screen;

    App() throws IOException {
        DefaultTerminalFactory terminalFactory = new DefaultTerminalFactory();
        terminalFactory.setInitialTerminalSize(new TerminalSize(130, 40));
        var virtualScreen = new VirtualScreen(terminalFactory.createScreen());
        screen = virtualScreen;
        view = new LanternaView(virtualScreen);
        model = new GameModel(view);
        controller = new KeyboardController(model, screen);
    }

    void loop() {
        controller.loop();
        try {
            screen.stopScreen();
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
