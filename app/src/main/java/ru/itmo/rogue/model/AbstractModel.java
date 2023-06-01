package ru.itmo.rogue.model;

import ru.itmo.rogue.utils.Updatable;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractModel<T, U> implements Model<T, U>{
    @Override
    public boolean subscribe(Updatable<U> updatable) {
        return updatableList.add(updatable);
    }

    protected List<Updatable<U>> updatableList = new ArrayList<>();
}
