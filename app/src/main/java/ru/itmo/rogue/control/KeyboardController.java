package ru.itmo.rogue.control;

import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import ru.itmo.rogue.model.Model;

import java.io.IOException;

public class KeyboardController implements Controller {

    private final Model model;
    private final Screen screen;

    public KeyboardController(Model model, Screen screen) {
        this.model = model;
        this.screen = screen;
    }


    public KeyStroke pressedKey() throws IOException {
        KeyStroke currentStroke = screen.readInput();
        KeyStroke lastStroke = currentStroke;
        while (currentStroke != null) {
            lastStroke = currentStroke;
            currentStroke = screen.pollInput();
        }


        return lastStroke;
    }

    Signal getSignal(KeyStroke stroke)
    {
        KeyType type = stroke.getKeyType();
        return switch (type) {
            case Escape -> Signal.BACK;
            case ArrowLeft -> Signal.LEFT;
            case ArrowRight -> Signal.RIGHT;
            case ArrowUp -> Signal.UP;
            case ArrowDown -> Signal.DOWN;

            case Enter -> Signal.SELECT;
            default -> null;
        };

    }
    // TODO: Implement

    @Override
    public void loop() {
        boolean run = true;

        try {
            while (run) {
                KeyStroke stroke = pressedKey();
                if (stroke == null) {
                    continue;
                }
                Signal signal = getSignal(stroke);
                if(signal == null) {
                    continue;
                }
                run = model.update(signal);
            }
        }
        catch (IOException e) {
            System.err.println(e);
        }

        // TODO: Ending message
    }
}
