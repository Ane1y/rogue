package ru.itmo.rogue.control;


import ru.itmo.rogue.model.Model;

public class KeyboardController implements Controller {

    private final Model model;

    public KeyboardController(Model model) {
        this.model = model;
    }

    // TODO: Implement

    @Override
    public void loop() {
        boolean run = true;
        while (run) {
            // TODO: PROCESS
            run = model.update(Signal.SELECT);
            run = false;
        }
        // TODO: Implement
    }
}
