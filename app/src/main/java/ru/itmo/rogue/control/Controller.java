package ru.itmo.rogue.control;

import ru.itmo.rogue.utils.Subscribable;
import ru.itmo.rogue.utils.Updatable;

public interface Controller<T> extends Subscribable<T> {
    void loop();

}
