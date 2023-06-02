package ru.itmo.rogue.model;

import ru.itmo.rogue.utils.Subscribable;
import ru.itmo.rogue.utils.Updatable;

public interface Model<Input, Output> extends Updatable<Input>, Subscribable<Output> {

}
