package ru.itmo.rogue.utils;

public interface Subscribable<T> {

    boolean subscribe(Updatable<T> updatable);

}
