package ru.itmo.rogue.control;

import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import ru.itmo.rogue.model.Model;
import ru.itmo.rogue.model.unit.Movement;
import ru.itmo.rogue.view.DefaultView;
import ru.itmo.rogue.view.Focus;

import java.io.IOException;

public class KeyboardController implements Controller {

    private final Model model;
    private final Screen screen;

    private final DefaultView view;

    public KeyboardController(Model model, DefaultView view) {
        this.model = model;
        this.screen = view.getScreen();
        this.view = view;
    }


    /**
     * Returns last pressed key (or keystroke)
     * Method supposed to wait first, but on some input it skips and returns null.
     * Blocks thread.
     * @return last pressed key
     * @throws IOException see documentation for lanterna.input.InputProvider (.readInput(), .pollInput())
     */
    public KeyStroke pressedKey() throws IOException {
        KeyStroke currentStroke = screen.readInput();
        KeyStroke lastStroke = currentStroke;

        // Exhaust all input
        while (currentStroke != null) {
            lastStroke = currentStroke;
            currentStroke = screen.pollInput();
        }

        return lastStroke;
    }

    /**
     * See Controller interface
     */
    @Override
    public void loop() {
        try {
            while (model.getState().running()) {
                KeyStroke stroke = pressedKey();
                if (stroke == null) {
                    continue;
                }

                if (stroke.getKeyType().equals(KeyType.Escape)) {
                    view.toggleFocus();
                    continue;
                }

                if (view.getFocus().equals(Focus.INVENTORY)) {
                    inventoryKeys(stroke);
                } else {
                    levelKeys(stroke);
                }
            }
        }
        catch (IOException e) {
            System.err.println("Error occurred when tried to read keystroke");
            System.err.println(e);
        }
    }

    private void inventoryKeys(KeyStroke keystroke) {
        switch (keystroke.getKeyType()) {
            case ArrowDown -> view.itemFocusDown();
            case ArrowUp -> view.itemFocusUp();
            case Enter -> model.usePlayerItem(view.getFocusedItem());
        }
    }

    private void levelKeys(KeyStroke keystroke) {
        var movement = switch (keystroke.getKeyType()) {
            case ArrowLeft -> Movement.LEFT;
            case ArrowRight -> Movement.RIGHT;
            case ArrowUp -> Movement.UP;
            case ArrowDown -> Movement.DOWN;
            default -> null;
        };

        if (movement != null) {
            model.movePlayer(movement);
        }
    }


}
