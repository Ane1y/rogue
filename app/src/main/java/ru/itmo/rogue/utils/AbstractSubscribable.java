package ru.itmo.rogue.utils;

import java.util.ArrayList;
import java.util.List;

public class AbstractSubscribable<T> implements Subscribable<T> {

    @Override
    public boolean subscribe(Updatable<T> updatable) {
        return updatableList.add(updatable);
    }

    protected List<Updatable<T>> updatableList = new ArrayList<>();

}
