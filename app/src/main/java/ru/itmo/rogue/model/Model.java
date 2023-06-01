package ru.itmo.rogue.model;

import ru.itmo.rogue.utils.Updatable;

public interface Model<T, U> extends Updatable<T> {

    boolean subscribe(Updatable<U> updatable);

}
