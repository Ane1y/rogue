package ru.itmo.rogue.control;

/**
 * Signal from Controller to the Model
 */
public enum Signal {
    UP, DOWN, LEFT, RIGHT,
    BACK /* Esc on Keyboard */, SELECT /* Enter on Keyboard */
}