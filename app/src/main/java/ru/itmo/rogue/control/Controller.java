package ru.itmo.rogue.control;

import ru.itmo.rogue.utils.Updatable;

public interface Controller<T> {

    boolean subscribe(Updatable<T> updatable);

    void loop();

}
