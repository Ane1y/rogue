package ru.itmo.rogue.control;

public abstract class Control {

    public enum Signal {
        UP, DOWN, LEFT, RIGHT,
        BACK /* Esc on Keyboard */, SELECT /* Enter on Keyboard */
    }

}
