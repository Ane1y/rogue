package ru.itmo.rogue.utils;

import java.io.IOException;

public interface Updatable<T> {

    boolean update(T data);

}
